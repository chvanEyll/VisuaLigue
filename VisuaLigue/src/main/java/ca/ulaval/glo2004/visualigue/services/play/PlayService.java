package ca.ulaval.glo2004.visualigue.services.play;

import ca.ulaval.glo2004.visualigue.domain.play.*;
import ca.ulaval.glo2004.visualigue.domain.play.actorinstance.TeamSide;
import ca.ulaval.glo2004.visualigue.domain.play.frame.Frame;
import ca.ulaval.glo2004.visualigue.domain.play.keyframe.KeyframeTransition;
import ca.ulaval.glo2004.visualigue.domain.sport.Sport;
import ca.ulaval.glo2004.visualigue.domain.sport.SportNotFoundException;
import ca.ulaval.glo2004.visualigue.domain.sport.SportRepository;
import ca.ulaval.glo2004.visualigue.services.play.commands.*;
import ca.ulaval.glo2004.visualigue.utils.EventHandler;
import ca.ulaval.glo2004.visualigue.utils.geometry.Vector2;
import java.util.*;
import java.util.function.Function;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.swing.SortOrder;

@Singleton
public class PlayService {

    private final PlayRepository playRepository;
    private final PlayFactory playFactory;
    private final SportRepository sportRepository;

    private final Map<String, Deque<Command>> undoStackMap = new HashMap();
    private final Map<String, Deque<Command>> redoStackMap = new HashMap();

    public EventHandler<Play> onPlayCreated = new EventHandler();
    public EventHandler<Play> onPlayUpdated = new EventHandler();
    public EventHandler<Play> onPlayDeleted = new EventHandler();
    public EventHandler<Boolean> onUndoAvailabilityChanged = new EventHandler();
    public EventHandler<Boolean> onRedoAvailabilityChanged = new EventHandler();
    public EventHandler<Play> onPlayDirtyFlagChanged = new EventHandler();

    @Inject
    public PlayService(final PlayRepository playRepository, final PlayFactory playFactory, final SportRepository sportRepository) {
        this.playRepository = playRepository;
        this.playFactory = playFactory;
        this.sportRepository = sportRepository;
    }

    public String createPlay(String name, String sportUUID) throws PlayAlreadyExistsException, SportNotFoundException {
        Sport sport = sportRepository.get(sportUUID);
        Play play = playFactory.create(name, sport);
        playRepository.persist(play);
        setDirty(play.getUUID(), false);
        onPlayCreated.fire(this, play);
        return play.getUUID();
    }

    public void updatePlayTitle(String playUUID, String title) throws PlayNotFoundException {
        Play play = playRepository.get(playUUID);
        play.setTitle(title);
        setDirty(playUUID, true);
        onPlayUpdated.fire(this, play);
    }

    public void deletePlay(String playUUID) throws PlayNotFoundException {
        Play play = playRepository.get(playUUID);
        playRepository.delete(play);
        onPlayDeleted.fire(this, play);
    }

    public Play getPlay(String playUUID) throws PlayNotFoundException {
        return playRepository.get(playUUID);
    }

    public List<Play> getPlays(Function<Play, Comparable> sortFunction, SortOrder sortOrder) {
        return playRepository.getAll(sortFunction, sortOrder);
    }

    public void addPlayer(String playUUID, Integer time, String playerCategoryUUID, TeamSide teamSide, Double orientation, Vector2 position) throws Exception {
        Play play = playRepository.get(playUUID);
        PlayerCreationCommand command = new PlayerCreationCommand(play, time, playerCategoryUUID, teamSide, orientation, position);
        executeNewCommand(playUUID, command);
    }

    public void updatePlayerPositionDirect(String playUUID, Integer time, String ownerPlayerInstanceUUID, Vector2 position) throws Exception {
        Play play = playRepository.get(playUUID);
        PlayerPositionUpdateDirectCommand command = new PlayerPositionUpdateDirectCommand(play, time, ownerPlayerInstanceUUID, position);
        executeNewCommand(playUUID, command);
    }

    public void updatePlayerPositionFreeform(String playUUID, Integer time, String ownerPlayerInstanceUUID, Vector2 position, KeyframeTransition positionTransition) throws Exception {
        Play play = playRepository.get(playUUID);
        PlayerPositionUpdateFreeformCommand command = new PlayerPositionUpdateFreeformCommand(play, time, ownerPlayerInstanceUUID, position, positionTransition);
        executeNewCommand(playUUID, command);
    }

    public void updatePlayerOrientation(String playUUID, Integer time, String ownerPlayerInstanceUUID, Double orientation) throws Exception {
        Play play = playRepository.get(playUUID);
        PlayerOrientationUpdateCommand command = new PlayerOrientationUpdateCommand(play, time, ownerPlayerInstanceUUID, orientation);
        executeNewCommand(playUUID, command);
    }

    public void addObstacle(String playUUID, Integer time, String obstacleInstanceUUID, Vector2 position) throws Exception {
        Play play = playRepository.get(playUUID);
        ObstacleCreationCommand command = new ObstacleCreationCommand(play, time, obstacleInstanceUUID, position);
        executeNewCommand(playUUID, command);
    }

    public void addBall(String playUUID, Integer time, String ownerPlayerInstanceUUID, Vector2 position) throws Exception {
        Play play = playRepository.get(playUUID);
        BallCreationCommand command = new BallCreationCommand(play, time, ownerPlayerInstanceUUID, position);
        executeNewCommand(playUUID, command);
    }

    public void updateBall(String playUUID, Integer time, String ballInstanceUUID, String ownerPlayerInstanceUUID, Vector2 position) throws Exception {
        Play play = playRepository.get(playUUID);
        BallUpdateCommand command = new BallUpdateCommand(play, time, ballInstanceUUID, ownerPlayerInstanceUUID, position);
        executeNewCommand(playUUID, command);
    }

    public void savePlay(String playUUID) throws PlayNotFoundException {
        Play play = playRepository.get(playUUID);
        playRepository.update(play);
        setDirty(playUUID, false);
    }

    public void discardChanges(String playUUID) throws PlayNotFoundException {
        Play play = playRepository.get(playUUID);
        playRepository.discard(play);
        setDirty(playUUID, false);
    }

    public Frame getFrame(String playUUID, Integer time) throws PlayNotFoundException {
        Play play = playRepository.get(playUUID);
        return play.getFrame(time);
    }

    public Integer getPlayLength(String playUUID) throws PlayNotFoundException {
        Play play = playRepository.get(playUUID);
        return play.getLength();
    }

    public Boolean isUndoAvailable(String playUUID) throws PlayNotFoundException {
        if (undoStackMap.containsKey(playUUID)) {
            return undoStackMap.get(playUUID).size() > 0;
        } else {
            return false;
        }
    }

    public Boolean isRedoAvailable(String playUUID) throws PlayNotFoundException {
        if (redoStackMap.containsKey(playUUID)) {
            return redoStackMap.get(playUUID).size() > 0;
        } else {
            return false;
        }
    }

    public void undo(String playUUID) throws PlayNotFoundException {
        if (!isUndoAvailable(playUUID)) {
            throw new IllegalStateException("Undo operation is not permitted at this time.");
        }
        Command lastCommand = undoStackMap.get(playUUID).pop();
        redoStackMap.get(playUUID).push(lastCommand);
        lastCommand.revert();
        setDirty(playUUID, true);
        onUndoAvailabilityChanged.fire(this, isUndoAvailable(playUUID));
    }

    public void redo(String playUUID) throws PlayNotFoundException {
        if (!isRedoAvailable(playUUID)) {
            throw new IllegalStateException("Redo operation is not permitted at this time.");
        }
        Command nextCommand = redoStackMap.get(playUUID).pop();
        undoStackMap.get(playUUID).push(nextCommand);
        nextCommand.execute();
        setDirty(playUUID, true);
        onRedoAvailabilityChanged.fire(this, isRedoAvailable(playUUID));
    }

    private void executeNewCommand(String playUUID, Command command) {
        command.execute();
        setDirty(playUUID, true);
        if (!undoStackMap.containsKey(playUUID)) {
            undoStackMap.put(playUUID, new ArrayDeque());
        }
        if (!redoStackMap.containsKey(playUUID)) {
            redoStackMap.put(playUUID, new ArrayDeque());
        }
        redoStackMap.get(playUUID).clear();
        undoStackMap.get(playUUID).push(command);
    }

    private void setDirty(String playUUID, Boolean dirty) throws PlayNotFoundException {
        Play play = playRepository.get(playUUID);
        play.setDirty(dirty);
        onPlayDirtyFlagChanged.fire(this, play);
    }

    public Boolean isPlayDirty(String playUUID) throws PlayNotFoundException {
        Play play = playRepository.get(playUUID);
        return play.isDirty();
    }

}
