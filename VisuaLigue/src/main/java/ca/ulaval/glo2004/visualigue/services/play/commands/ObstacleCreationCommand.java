package ca.ulaval.glo2004.visualigue.services.play.commands;

import ca.ulaval.glo2004.visualigue.domain.obstacle.Obstacle;
import ca.ulaval.glo2004.visualigue.domain.obstacle.ObstacleRepository;
import ca.ulaval.glo2004.visualigue.domain.play.Play;
import ca.ulaval.glo2004.visualigue.domain.play.PlayRepository;
import ca.ulaval.glo2004.visualigue.domain.play.actor.obstacleinstance.ObstacleInstance;
import ca.ulaval.glo2004.visualigue.domain.play.actorstate.ObstacleState;
import ca.ulaval.glo2004.visualigue.domain.play.position.Position;
import java.util.UUID;
import javax.inject.Inject;

public class ObstacleCreationCommand implements Command {

    UUID playUUID;
    Integer time;
    UUID obstacleUUID;
    Position position;
    @Inject private PlayRepository playRepository;
    @Inject private ObstacleRepository obstacleRepository;

    private Play play;
    private ObstacleState obstacleState;
    private Obstacle obstacle;
    private ObstacleInstance obstacleInstance;

    public ObstacleCreationCommand(UUID playUUID, Integer time, UUID obstacleUUID, Position position) {
        this.playUUID = playUUID;
        this.time = time;
        this.obstacleUUID = obstacleUUID;
        this.position = position;
    }

    @Override
    public void execute() throws Exception {
        play = playRepository.get(playUUID);
        obstacleState = new ObstacleState(position);
        obstacle = obstacleRepository.get(obstacleUUID);
        obstacleInstance = new ObstacleInstance(obstacle);
        play.mergeActorState(time, obstacleInstance, obstacleState);
    }

    @Override
    public void revert() {

    }

}
