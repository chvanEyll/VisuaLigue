package ca.ulaval.glo2004.visualigue.services.play.commands;

import ca.ulaval.glo2004.visualigue.domain.obstacle.Obstacle;
import ca.ulaval.glo2004.visualigue.domain.obstacle.ObstacleNotFoundException;
import ca.ulaval.glo2004.visualigue.domain.obstacle.ObstacleRepository;
import ca.ulaval.glo2004.visualigue.domain.play.Play;
import ca.ulaval.glo2004.visualigue.domain.play.actorinstance.ObstacleInstance;
import ca.ulaval.glo2004.visualigue.domain.play.actorstate.ObstacleState;
import ca.ulaval.glo2004.visualigue.utils.EventHandler;
import ca.ulaval.glo2004.visualigue.utils.geometry.Vector2;

public class ObstacleCreationCommand extends Command {

    private String obstacleInstanceUUID;
    private Vector2 position;
    private EventHandler<Play> onFrameChanged;
    private ObstacleRepository obstacleRepository;

    private ObstacleInstance obstacleInstance;

    public ObstacleCreationCommand(Play play, Integer time, String obstacleInstanceUUID, Vector2 position, ObstacleRepository obstacleRepository, EventHandler<Play> onFrameChanged) {
        super(play, time);
        this.obstacleInstanceUUID = obstacleInstanceUUID;
        this.position = position;
        this.obstacleRepository = obstacleRepository;
        this.onFrameChanged = onFrameChanged;
    }

    @Override
    public void execute() throws ObstacleNotFoundException {
        ObstacleState obstacleState = new ObstacleState(position);
        Obstacle obstacle = obstacleRepository.get(obstacleInstanceUUID);
        obstacleInstance = new ObstacleInstance(obstacle);
        play.mergeKeyframe(time, obstacleInstance, obstacleState);
        onFrameChanged.fire(this, play);
    }

    @Override
    public void revert() {
        play.unmergeKeyframe(time, obstacleInstance, null);
        onFrameChanged.fire(this, play);
    }

}
