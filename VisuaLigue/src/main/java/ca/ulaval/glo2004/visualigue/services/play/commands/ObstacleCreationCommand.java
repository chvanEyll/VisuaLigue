package ca.ulaval.glo2004.visualigue.services.play.commands;

import ca.ulaval.glo2004.visualigue.domain.obstacle.Obstacle;
import ca.ulaval.glo2004.visualigue.domain.obstacle.ObstacleNotFoundException;
import ca.ulaval.glo2004.visualigue.domain.obstacle.ObstacleRepository;
import ca.ulaval.glo2004.visualigue.domain.play.Play;
import ca.ulaval.glo2004.visualigue.domain.play.PlayRepository;
import ca.ulaval.glo2004.visualigue.domain.play.actorinstance.ObstacleInstance;
import ca.ulaval.glo2004.visualigue.domain.play.actorstate.ObstacleState;
import ca.ulaval.glo2004.visualigue.utils.geometry.Vector2;
import java.util.UUID;
import javax.inject.Inject;

public class ObstacleCreationCommand implements Command {

    private Play play;
    private Integer time;
    private UUID obstacleInstanceUUID;
    private Vector2 position;
    @Inject private PlayRepository playRepository;
    @Inject private ObstacleRepository obstacleRepository;

    private ObstacleInstance obstacleInstance;

    public ObstacleCreationCommand(Play play, Integer time, UUID obstacleInstanceUUID, Vector2 position) {
        this.time = time;
        this.obstacleInstanceUUID = obstacleInstanceUUID;
        this.position = position;
    }

    @Override
    public void execute() throws ObstacleNotFoundException {
        ObstacleState obstacleState = new ObstacleState(position);
        Obstacle obstacle = obstacleRepository.get(obstacleInstanceUUID);
        obstacleInstance = new ObstacleInstance(obstacle);
        play.mergeKeyframe(time, obstacleInstance, obstacleState);
    }

    @Override
    public void revert() {
        play.unmergeKeyframe(time, obstacleInstance, null);
    }

}
