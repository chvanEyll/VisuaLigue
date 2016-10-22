package ca.ulaval.glo2004.visualigue.services.play;

import ca.ulaval.glo2004.visualigue.domain.play.*;
import ca.ulaval.glo2004.visualigue.domain.play.actorinstance.ActorInstance;
import ca.ulaval.glo2004.visualigue.domain.play.actorinstance.TeamSide;
import ca.ulaval.glo2004.visualigue.domain.play.frame.Frame;
import ca.ulaval.glo2004.visualigue.domain.play.keyframe.Keyframe;
import ca.ulaval.glo2004.visualigue.domain.play.position.Position;
import ca.ulaval.glo2004.visualigue.domain.play.transition.Transition;
import ca.ulaval.glo2004.visualigue.domain.sport.Sport;
import ca.ulaval.glo2004.visualigue.domain.sport.SportNotFoundException;
import ca.ulaval.glo2004.visualigue.domain.sport.SportRepository;
import ca.ulaval.glo2004.visualigue.services.play.commands.*;
import ca.ulaval.glo2004.visualigue.utils.EventHandler;
import java.util.*;
import java.util.function.Function;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.swing.SortOrder;
import org.apache.commons.lang3.tuple.Pair;

@Singleton
public class PlayService {

    private final PlayRepository playRepository;
    private final PlayFactory playFactory;
    private final SportRepository sportRepository;

    private final Map<UUID, Deque<Command>> undoStackMap = new HashMap();
    private final Map<UUID, Deque<Command>> redoStackMap = new HashMap();

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

    public UUID createPlay(String name, UUID sportUUID) throws PlayAlreadyExistsException, SportNotFoundException {
        Sport sport = sportRepository.get(sportUUID);
        Play play = playFactory.create(name, sport);
        playRepository.persist(play);
        onPlayCreated.fire(this, play);
        return play.getUUID();
    }

    public void updatePlayTitle(UUID playUUID, String title) throws PlayNotFoundException {
        Play play = playRepository.get(playUUID);
        play.setTitle(title);
        setDirty(playUUID, true);
        onPlayUpdated.fire(this, play);
    }

    public void deletePlay(UUID playUUID) throws PlayNotFoundException {
        Play play = playRepository.get(playUUID);
        playRepository.delete(play);
        onPlayDeleted.fire(this, play);
    }

    public Play getPlay(UUID playUUID) throws PlayNotFoundException {
        return playRepository.get(playUUID);
    }

    public List<Play> getPlays(Function<Play, Comparable> sortFunction, SortOrder sortOrder) {
        return playRepository.getAll(sortFunction, sortOrder);
    }

    public void addPlayer(UUID playUUID, Integer time, UUID playerCategoryUUID, TeamSide teamSide, Double orientation, Position position) throws Exception {
        Play play = playRepository.get(playUUID);
        PlayerCreationCommand command = new PlayerCreationCommand(play, time, playerCategoryUUID, teamSide, orientation, position);
        executeNewCommand(playUUID, command);
    }

    public void updatePlayerPositionDirect(UUID playUUID, Integer time, UUID ownerPlayerInstanceUUID, Position position) throws Exception {
        Play play = playRepository.get(playUUID);
        PlayerPositionUpdateDirectCommand command = new PlayerPositionUpdateDirectCommand(play, time, ownerPlayerInstanceUUID, position);
        executeNewCommand(playUUID, command);
    }

    public void updatePlayerPositionFreeform(UUID playUUID, Integer time, UUID ownerPlayerInstanceUUID, Position position, Transition positionTransition) throws Exception {
        Play play = playRepository.get(playUUID);
        PlayerPositionUpdateFreeformCommand command = new PlayerPositionUpdateFreeformCommand(play, time, ownerPlayerInstanceUUID, position, positionTransition);
        executeNewCommand(playUUID, command);
    }

    public void updatePlayerOrientation(UUID playUUID, Integer time, UUID ownerPlayerInstanceUUID, Double orientation) throws Exception {
        Play play = playRepository.get(playUUID);
        PlayerOrientationUpdateCommand command = new PlayerOrientationUpdateCommand(play, time, ownerPlayerInstanceUUID, orientation);
        executeNewCommand(playUUID, command);
    }

    public void addObstacle(UUID playUUID, Integer time, UUID obstacleInstanceUUID, Position position) throws Exception {
        Play play = playRepository.get(playUUID);
        ObstacleCreationCommand command = new ObstacleCreationCommand(play, time, obstacleInstanceUUID, position);
        executeNewCommand(playUUID, command);
    }

    public void addBall(UUID playUUID, Integer time, UUID ownerPlayerInstanceUUID, Position position) throws Exception {
        Play play = playRepository.get(playUUID);
        BallCreationCommand command = new BallCreationCommand(play, time, ownerPlayerInstanceUUID, position);
        executeNewCommand(playUUID, command);
    }

    public void updateBall(UUID playUUID, Integer time, UUID ballInstanceUUID, UUID ownerPlayerInstanceUUID, Position position) throws Exception {
        Play play = playRepository.get(playUUID);
        BallUpdateCommand command = new BallUpdateCommand(play, time, ballInstanceUUID, ownerPlayerInstanceUUID, position);
        executeNewCommand(playUUID, command);
    }

    public void savePlay(UUID playUUID) throws PlayNotFoundException {
        Play play = playRepository.get(playUUID);
        try {
            playRepository.persist(play);
        } catch (PlayAlreadyExistsException ex) {
            throw new RuntimeException(ex);
        }
        setDirty(playUUID, false);
    }

    public void discardChanges(UUID playUUID) throws PlayNotFoundException {
        Play play = playRepository.get(playUUID);
        playRepository.discard(play);
        setDirty(playUUID, false);
    }

    public Frame getFrame(UUID playUUID, Integer time) throws PlayNotFoundException {
        Play play = playRepository.get(playUUID);
        return play.getFrame(time);
    }

    public SortedMap<Pair<ActorInstance, Integer>, Keyframe> getKeyframes(UUID playUUID) throws PlayNotFoundException {
        Play play = playRepository.get(playUUID);
        return play.getKeyframes();
    }

    public Boolean isUndoAvailable(UUID playUUID) throws PlayNotFoundException {
        if (undoStackMap.containsKey(playUUID)) {
            return undoStackMap.get(playUUID).size() > 0;
        } else {
            return false;
        }
    }

    public Boolean isRedoAvailable(UUID playUUID) throws PlayNotFoundException {
        if (redoStackMap.containsKey(playUUID)) {
            return redoStackMap.get(playUUID).size() > 0;
        } else {
            return false;
        }
    }

    public void undo(UUID playUUID) throws PlayNotFoundException {
        if (!isUndoAvailable(playUUID)) {
            throw new IllegalStateException("Undo operation is not permitted at this time.");
        }
        Command lastCommand = undoStackMap.get(playUUID).pop();
        redoStackMap.get(playUUID).push(lastCommand);
        lastCommand.revert();
        setDirty(playUUID, true);
        onUndoAvailabilityChanged.fire(this, isUndoAvailable(playUUID));
    }

    public void redo(UUID playUUID) throws PlayNotFoundException {
        if (!isRedoAvailable(playUUID)) {
            throw new IllegalStateException("Redo operation is not permitted at this time.");
        }
        Command nextCommand = redoStackMap.get(playUUID).pop();
        undoStackMap.get(playUUID).push(nextCommand);
        try {
            nextCommand.execute();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        setDirty(playUUID, true);
        onRedoAvailabilityChanged.fire(this, isRedoAvailable(playUUID));
    }

    private void executeNewCommand(UUID playUUID, Command command) throws Exception {
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

    private void setDirty(UUID playUUID, Boolean dirty) throws PlayNotFoundException {
        Play play = playRepository.get(playUUID);
        play.setDirty(dirty);
        onPlayDirtyFlagChanged.fire(this, play);
    }

}
