package ca.ulaval.glo2004.visualigue.services.play;

import ca.ulaval.glo2004.visualigue.domain.obstacle.ObstacleRepository;
import ca.ulaval.glo2004.visualigue.domain.play.*;
import ca.ulaval.glo2004.visualigue.domain.play.actorinstance.TeamSide;
import ca.ulaval.glo2004.visualigue.domain.play.actorstate.transition.FreeformStateTransition;
import ca.ulaval.glo2004.visualigue.domain.play.frame.Frame;
import ca.ulaval.glo2004.visualigue.domain.sport.Sport;
import ca.ulaval.glo2004.visualigue.domain.sport.SportNotFoundException;
import ca.ulaval.glo2004.visualigue.domain.sport.SportRepository;
import ca.ulaval.glo2004.visualigue.domain.sport.playercategory.PlayerCategoryRepository;
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
    private final PlayerCategoryRepository playerCategoryRepository;
    private final ObstacleRepository obstacleRepository;

    private final Map<String, Deque<Command>> undoStackMap = new HashMap();
    private final Map<String, Deque<Command>> redoStackMap = new HashMap();

    public EventHandler<Play> onPlayCreated = new EventHandler();
    public EventHandler<Play> onPlayUpdated = new EventHandler();
    public EventHandler<Play> onPlayDeleted = new EventHandler();
    public EventHandler<Play> onDirtyFlagChanged = new EventHandler();
    public EventHandler<Play> onFrameChanged = new EventHandler();
    public EventHandler<Integer> onUndo = new EventHandler();
    public EventHandler<Integer> onRedo = new EventHandler();
    public EventHandler<Boolean> onUndoAvailabilityChanged = new EventHandler();
    public EventHandler<Boolean> onRedoAvailabilityChanged = new EventHandler();
    public EventHandler onNewCommandExecute = new EventHandler();

    @Inject
    public PlayService(final PlayRepository playRepository, final PlayFactory playFactory, final SportRepository sportRepository, final PlayerCategoryRepository playerCategoryRepository, final ObstacleRepository obstacleRepository) {
        this.playRepository = playRepository;
        this.playFactory = playFactory;
        this.sportRepository = sportRepository;
        this.playerCategoryRepository = playerCategoryRepository;
        this.obstacleRepository = obstacleRepository;
    }

    public String createPlay(String sportUUID) throws PlayAlreadyExistsException, SportNotFoundException {
        Sport sport = sportRepository.get(sportUUID);
        Play play = playFactory.create(sport);
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

    public void addPlayerInstance(String playUUID, String playerCategoryUUID, TeamSide teamSide, Double orientation, Vector2 position) {
        Play play = playRepository.get(playUUID);
        PlayerCreationCommand command = new PlayerCreationCommand(play, 0, playerCategoryUUID, teamSide, orientation, position, playerCategoryRepository, onFrameChanged);
        executeNewCommand(playUUID, command);
    }

    public void updatePlayerInstancePositionDirect(String playUUID, Integer time, String playerInstanceUUID, Vector2 position) {
        Play play = playRepository.get(playUUID);
        PlayerPositionUpdateDirectCommand command = new PlayerPositionUpdateDirectCommand(play, time, playerInstanceUUID, position, onFrameChanged);
        executeNewCommand(playUUID, command);
    }

    public void updatePlayerInstancePositionFreeform(String playUUID, Integer time, String playerInstanceUUID, Vector2 position, FreeformStateTransition positionTransition) {
        Play play = playRepository.get(playUUID);
        PlayerPositionUpdateFreeformCommand command = new PlayerPositionUpdateFreeformCommand(play, time, playerInstanceUUID, position, positionTransition, onFrameChanged);
        executeNewCommand(playUUID, command);
    }

    public void updatePlayerInstanceOrientation(String playUUID, Integer time, String ownerPlayerInstanceUUID, Double orientation) {
        Play play = playRepository.get(playUUID);
        PlayerOrientationUpdateCommand command = new PlayerOrientationUpdateCommand(play, time, ownerPlayerInstanceUUID, orientation, onFrameChanged);
        executeNewCommand(playUUID, command);
    }

    public void addObstacleInstance(String playUUID, Integer time, String obstacleInstanceUUID, Vector2 position) {
        Play play = playRepository.get(playUUID);
        ObstacleCreationCommand command = new ObstacleCreationCommand(play, time, obstacleInstanceUUID, position, obstacleRepository, onFrameChanged);
        executeNewCommand(playUUID, command);
    }

    public void addBallInstance(String playUUID, Integer time, String ownerPlayerInstanceUUID, Vector2 position) {
        Play play = playRepository.get(playUUID);
        BallCreationCommand command = new BallCreationCommand(play, time, ownerPlayerInstanceUUID, position, onFrameChanged);
        executeNewCommand(playUUID, command);
    }

    public void updateBallInstance(String playUUID, Integer time, String ballInstanceUUID, String ownerPlayerInstanceUUID, Vector2 position) {
        Play play = playRepository.get(playUUID);
        BallUpdateCommand command = new BallUpdateCommand(play, time, ballInstanceUUID, ownerPlayerInstanceUUID, position, onFrameChanged);
        executeNewCommand(playUUID, command);
    }

    public void setTimelineLength(String playUUID, Integer time, Integer timelineLength) {
        Play play = playRepository.get(playUUID);
        TimelineLengthUpdateCommand command = new TimelineLengthUpdateCommand(play, time, timelineLength, onPlayUpdated);
        executeNewCommand(playUUID, command);
    }

    public void savePlay(String playUUID) throws PlayNotFoundException {
        Play play = playRepository.get(playUUID);
        setDirty(playUUID, false);
        playRepository.update(play);
    }

    public void discardChanges(String playUUID) throws PlayNotFoundException {
        Play play = playRepository.get(playUUID);
        Play revertedPlay = playRepository.revert(play);
        setDirty(playUUID, false);
        onPlayUpdated.fire(this, revertedPlay);
    }

    public Frame getFrame(String playUUID, Integer time) throws PlayNotFoundException {
        Play play = playRepository.get(playUUID);
        return play.getFrame(time);
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
            return;
        }
        Command lastCommand = undoStackMap.get(playUUID).pop();
        redoStackMap.get(playUUID).push(lastCommand);
        lastCommand.revert();
        setDirty(playUUID, true);
        onUndoAvailabilityChanged.fire(this, isUndoAvailable(playUUID));
        onRedoAvailabilityChanged.fire(this, isRedoAvailable(playUUID));
        onUndo.fire(this, lastCommand.getTime());
    }

    public void redo(String playUUID) throws PlayNotFoundException {
        if (!isRedoAvailable(playUUID)) {
            return;
        }
        Command nextCommand = redoStackMap.get(playUUID).pop();
        undoStackMap.get(playUUID).push(nextCommand);
        nextCommand.execute();
        setDirty(playUUID, true);
        onUndoAvailabilityChanged.fire(this, isUndoAvailable(playUUID));
        onRedoAvailabilityChanged.fire(this, isRedoAvailable(playUUID));
        onRedo.fire(this, nextCommand.getTime());
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
        onRedoAvailabilityChanged.fire(this, isRedoAvailable(playUUID));
        onUndoAvailabilityChanged.fire(this, isUndoAvailable(playUUID));
        onNewCommandExecute.fire(this);
    }

    private void setDirty(String playUUID, Boolean dirty) throws PlayNotFoundException {
        Play play = playRepository.get(playUUID);
        play.setDirty(dirty);
        onDirtyFlagChanged.fire(this, play);
    }

    public Boolean isPlayDirty(String playUUID) throws PlayNotFoundException {
        Play play = playRepository.get(playUUID);
        return play.isDirty();
    }

}
