package ca.ulaval.glo2004.visualigue.services.play;

import ca.ulaval.glo2004.visualigue.domain.obstacle.ObstacleRepository;
import ca.ulaval.glo2004.visualigue.domain.play.*;
import ca.ulaval.glo2004.visualigue.domain.play.actor.TeamSide;
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

    private final Map<Play, Deque<CommandGroup>> undoStackMap = new HashMap();
    private final Map<Play, Deque<CommandGroup>> redoStackMap = new HashMap();

    public EventHandler<Play> onPlayCreated = new EventHandler();
    public EventHandler<Play> onPlayUpdated = new EventHandler();
    public EventHandler<Play> onPlayDeleted = new EventHandler();
    public EventHandler<Play> onDirtyFlagChanged = new EventHandler();
    public EventHandler<Boolean> onUndoAvailabilityChanged = new EventHandler();
    public EventHandler<Boolean> onRedoAvailabilityChanged = new EventHandler();
    public EventHandler<Long> onUndoRedo = new EventHandler();

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
        setDirty(play, false);
        onPlayCreated.fire(this, play);
        return play.getUUID();
    }

    public void updatePlayTitle(String playUUID, String title) throws PlayNotFoundException {
        Play play = playRepository.get(playUUID);
        play.setTitle(title);
        setDirty(play, true);
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

    public void addPlayerActor(String playUUID, Long time, String playerCategoryUUID, TeamSide teamSide, Double orientation, Vector2 position) {
        Play play = playRepository.get(playUUID);
        PlayerCreationCommand command = new PlayerCreationCommand(play, time, playerCategoryUUID, teamSide, orientation, position, playerCategoryRepository);
        executeNewCommand(play, command);
    }

    public void updatePlayerPositionDirect(String playUUID, Long time, String playerActorUUID, Vector2 position) {
        Play play = playRepository.get(playUUID);
        PlayerPositionUpdateDirectCommand command = new PlayerPositionUpdateDirectCommand(play, time, playerActorUUID, position);
        executeNewCommand(play, command);
    }

    public void updatePlayerPositionFreeform(String playUUID, Long time, String playerActorUUID, List<Vector2> positions) {
        Play play = playRepository.get(playUUID);
        PlayerPositionUpdateFreeformCommand command = new PlayerPositionUpdateFreeformCommand(play, time, playerActorUUID, positions);
        executeNewCommand(play, command);
    }

    public void updatePlayerOrientation(String playUUID, Long time, String ownerPlayerActorUUID, Double orientation) {
        Play play = playRepository.get(playUUID);
        PlayerOrientationUpdateCommand command = new PlayerOrientationUpdateCommand(play, time, ownerPlayerActorUUID, orientation);
        executeNewCommand(play, command);
    }

    public void addObstacleActor(String playUUID, Long time, String obstacleUUID, Vector2 position) {
        Play play = playRepository.get(playUUID);
        ObstacleCreationCommand command = new ObstacleCreationCommand(play, time, obstacleUUID, position, obstacleRepository);
        executeNewCommand(play, command);
    }

    public void updateObstaclPosition(String playUUID, Long time, String obstacleActorUUID, Vector2 position) {
        Play play = playRepository.get(playUUID);
        ObstaclePositionUpdateCommand command = new ObstaclePositionUpdateCommand(play, time, obstacleActorUUID, position);
        executeNewCommand(play, command);
    }

    public void addBallActor(String playUUID, Long time, String ownerPlayerActorUUID, Vector2 position) {
        Play play = playRepository.get(playUUID);
        BallCreationCommand command = new BallCreationCommand(play, time, ownerPlayerActorUUID, position);
        executeNewCommand(play, command);
    }

    public void updateBallPosition(String playUUID, Long time, String ballActorUUID, Vector2 position) {
        Play play = playRepository.get(playUUID);
        BallPositionUpdateCommand command = new BallPositionUpdateCommand(play, time, ballActorUUID, position);
        executeNewCommand(play, command);
    }

    public void snapBallToPlayer(String playUUID, Long time, String ballActorUUID, String playerActorUUID) {
        Play play = playRepository.get(playUUID);
        BallSnapCommand command = new BallSnapCommand(play, time, playerActorUUID, ballActorUUID);
        executeNewCommand(play, command);
    }

    public void unsnapBallFromPlayer(String playUUID, Long time, String ballActorUUID, String playerActorUUID) {
        Play play = playRepository.get(playUUID);
        BallUnsnapCommand command = new BallUnsnapCommand(play, time, playerActorUUID, ballActorUUID);
        executeNewCommand(play, command);
    }

    public void addKeypoint(String playUUID, Long time) {
        Play play = playRepository.get(playUUID);
        KeyPointCreationCommand command = new KeyPointCreationCommand(play, time);
        executeNewCommand(play, command);
    }

    public void savePlay(String playUUID) throws PlayNotFoundException {
        Play play = playRepository.get(playUUID);
        setDirty(play, false);
        playRepository.update(play);
    }

    public void discardChanges(String playUUID) throws PlayNotFoundException {
        Play play = playRepository.get(playUUID);
        Play revertedPlay = playRepository.revert(play);
        setDirty(play, false);
        clearUndoRedo(play);
        onPlayUpdated.fire(this, revertedPlay);
    }

    public Frame getFrame(String playUUID, Long time, Boolean showPlayerTrails) throws PlayNotFoundException {
        Play play = playRepository.get(playUUID);
        return play.getFrame(time, showPlayerTrails);
    }

    public Boolean isUndoAvailable(String playUUID) throws PlayNotFoundException {
        Play play = playRepository.get(playUUID);
        if (undoStackMap.containsKey(play)) {
            return undoStackMap.get(play).size() > 0;
        } else {
            return false;
        }
    }

    public Boolean isRedoAvailable(String playUUID) throws PlayNotFoundException {
        Play play = playRepository.get(playUUID);
        if (redoStackMap.containsKey(play)) {
            return redoStackMap.get(play).size() > 0;
        } else {
            return false;
        }
    }

    public void beginUpdate(String playUUID) {
        Play play = playRepository.get(playUUID);
        play.setPendingUpdateCount(0);
    }

    public void endUpdate(String playUUID) {
        Play play = playRepository.get(playUUID);
        onPlayUpdated.fire(this, play);
    }

    public void undo(String playUUID) throws PlayNotFoundException {
        Play play = playRepository.get(playUUID);
        if (!isUndoAvailable(playUUID)) {
            return;
        }
        CommandGroup lastCommandGroup = undoStackMap.get(play).pop();
        redoStackMap.get(play).push(lastCommandGroup);
        Long time = lastCommandGroup.revert();
        setDirty(play, true);
        onUndoAvailabilityChanged.fire(this, isUndoAvailable(playUUID));
        onRedoAvailabilityChanged.fire(this, isRedoAvailable(playUUID));
        onPlayUpdated.fire(this, play);
        onUndoRedo.fire(this, time);
    }

    public void redo(String playUUID) throws PlayNotFoundException {
        Play play = playRepository.get(playUUID);
        if (!isRedoAvailable(playUUID)) {
            return;
        }
        CommandGroup nextCommandGroup = redoStackMap.get(play).pop();
        undoStackMap.get(play).push(nextCommandGroup);
        Long time = nextCommandGroup.execute();
        setDirty(play, true);
        onUndoAvailabilityChanged.fire(this, isUndoAvailable(playUUID));
        onRedoAvailabilityChanged.fire(this, isRedoAvailable(playUUID));
        onPlayUpdated.fire(this, play);
        onUndoRedo.fire(this, time);
    }

    private void clearUndoRedo(Play play) {
        undoStackMap.get(play).clear();
        redoStackMap.get(play).clear();
        onUndoAvailabilityChanged.fire(this, isUndoAvailable(play.getUUID()));
        onRedoAvailabilityChanged.fire(this, isRedoAvailable(play.getUUID()));
    }

    private void executeNewCommand(Play play, Command command) {
        command.execute();
        setDirty(play, true);
        if (!undoStackMap.containsKey(play)) {
            undoStackMap.put(play, new ArrayDeque());
        }
        if (!redoStackMap.containsKey(play)) {
            redoStackMap.put(play, new ArrayDeque());
        }
        redoStackMap.get(play).clear();
        Deque<CommandGroup> undoStack = undoStackMap.get(play);
        if (play.getPendingUpdateCount() == 0 || undoStack.isEmpty()) {
            undoStack.push(new CommandGroup(command));
        } else {
            undoStack.getFirst().addCommand(command);
        }
        onRedoAvailabilityChanged.fire(this, isRedoAvailable(play.getUUID()));
        onUndoAvailabilityChanged.fire(this, isUndoAvailable(play.getUUID()));
    }

    private void setDirty(Play play, Boolean dirty) throws PlayNotFoundException {
        play.setDirty(dirty);
        onDirtyFlagChanged.fire(this, play);
    }

    public Boolean isPlayDirty(String playUUID) throws PlayNotFoundException {
        Play play = playRepository.get(playUUID);
        return play.isDirty();
    }

}
