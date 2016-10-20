package ca.ulaval.glo2004.visualigue.services.play;

import ca.ulaval.glo2004.visualigue.domain.play.*;
import ca.ulaval.glo2004.visualigue.domain.play.actor.ActorInstance;
import ca.ulaval.glo2004.visualigue.domain.play.actor.playerinstance.TeamSide;
import ca.ulaval.glo2004.visualigue.domain.play.frame.Frame;
import ca.ulaval.glo2004.visualigue.domain.play.keyframe.Keyframe;
import ca.ulaval.glo2004.visualigue.domain.play.position.Position;
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

    private final Deque<Command> undoStack = new ArrayDeque<>();
    private final Deque<Command> redoStack = new ArrayDeque<>();

    public EventHandler<Play> onPlayCreated = new EventHandler<>();
    public EventHandler<Play> onPlayUpdated = new EventHandler<>();
    public EventHandler<Play> onPlayDeleted = new EventHandler<>();

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
        playRepository.update(play);
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
        PlayerCreationCommand command = new PlayerCreationCommand(playUUID, time, playerCategoryUUID, teamSide, orientation, position);
        executeNewCommand(command);
    }

    public void updatePlayerPosition(UUID playUUID, Integer time, UUID ownerPlayerInstanceUUID, Position position) throws Exception {
        PlayerPositionUpdateCommand command = new PlayerPositionUpdateCommand(playUUID, time, ownerPlayerInstanceUUID, position);
        executeNewCommand(command);
    }

    public void updatePlayerOrientation(UUID playUUID, Integer time, UUID ownerPlayerInstanceUUID, Double orientation) throws Exception {
        PlayerOrientationUpdateCommand command = new PlayerOrientationUpdateCommand(playUUID, time, ownerPlayerInstanceUUID, orientation);
        executeNewCommand(command);
    }

    public void addObstacle(UUID playUUID, Integer time, UUID obstacleInstanceUUID, Position position) throws Exception {
        ObstacleCreationCommand command = new ObstacleCreationCommand(playUUID, time, obstacleInstanceUUID, position);
        executeNewCommand(command);
    }

    public void addBall(UUID playUUID, Integer time, UUID ownerPlayerInstanceUUID, Position position) throws Exception {
        BallCreationCommand command = new BallCreationCommand(playUUID, time, ownerPlayerInstanceUUID, position);
        executeNewCommand(command);
    }

    public void updateBall(UUID playUUID, Integer time, UUID ballInstanceUUID, UUID ownerPlayerInstanceUUID, Position position) throws Exception {
        BallUpdateCommand command = new BallUpdateCommand(playUUID, time, ballInstanceUUID, ownerPlayerInstanceUUID, position);
        executeNewCommand(command);
    }

    public void savePlay(UUID playUUID) throws PlayAlreadyExistsException, PlayNotFoundException {
        Play play = playRepository.get(playUUID);
        playRepository.persist(play);
    }

    public void discardChanges(UUID playUUID) throws PlayNotFoundException {
        Play play = playRepository.get(playUUID);
        playRepository.discard(play);
    }

    public Frame getFrame(UUID playUUID, Integer time) throws PlayNotFoundException {
        Play play = playRepository.get(playUUID);
        return play.getFrame(time);
    }

    public SortedMap<Pair<ActorInstance, Integer>, Keyframe> getKeyframes(UUID playUUID) throws PlayNotFoundException {
        Play play = playRepository.get(playUUID);
        return play.getKeyframes();
    }

    public Boolean isUndoAvailable() {
        return undoStack.size() > 0;
    }

    public Boolean isRedoAvailable() {
        return redoStack.size() > 0;
    }

    public void undo() {
        if (!isUndoAvailable()) {
            throw new IllegalStateException("Undo operation is not permitted at this time.");
        }
        Command lastCommand = undoStack.pop();
        redoStack.push(lastCommand);
        lastCommand.revert();
    }

    public void redo() throws Exception {
        if (!isRedoAvailable()) {
            throw new IllegalStateException("Redo operation is not permitted at this time.");
        }
        Command nextCommand = redoStack.pop();
        undoStack.push(nextCommand);
        nextCommand.execute();
    }

    private void executeNewCommand(Command command) throws Exception {
        command.execute();
        redoStack.clear();
        undoStack.push(command);
    }

}
