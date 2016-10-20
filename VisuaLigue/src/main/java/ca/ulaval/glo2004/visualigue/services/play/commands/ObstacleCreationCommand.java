package ca.ulaval.glo2004.visualigue.services.play.commands;

import ca.ulaval.glo2004.visualigue.domain.obstacle.Obstacle;
import ca.ulaval.glo2004.visualigue.domain.obstacle.ObstacleRepository;
import ca.ulaval.glo2004.visualigue.domain.play.Play;
import ca.ulaval.glo2004.visualigue.domain.play.PlayRepository;
import ca.ulaval.glo2004.visualigue.domain.play.actor.ObstacleInstance;
import ca.ulaval.glo2004.visualigue.domain.play.actorstate.ObstacleState;
import ca.ulaval.glo2004.visualigue.domain.play.position.Position;
import java.util.Optional;
import java.util.UUID;
import javax.inject.Inject;

public class ObstacleCreationCommand implements Command {

    private UUID playUUID;
    private Integer time;
    private UUID obstacleInstanceUUID;
    private Position position;
    @Inject private PlayRepository playRepository;
    @Inject private ObstacleRepository obstacleRepository;

    private Play play;
    private ObstacleInstance obstacleInstance;

    public ObstacleCreationCommand(UUID playUUID, Integer time, UUID obstacleInstanceUUID, Position position) {
        this.playUUID = playUUID;
        this.time = time;
        this.obstacleInstanceUUID = obstacleInstanceUUID;
        this.position = position;
    }

    @Override
    public void execute() throws Exception {
        play = playRepository.get(playUUID);
        ObstacleState obstacleState = new ObstacleState(Optional.of(position));
        Obstacle obstacle = obstacleRepository.get(obstacleInstanceUUID);
        obstacleInstance = new ObstacleInstance(obstacle);
        play.mergeActorState(time, obstacleInstance, obstacleState);
    }

    @Override
    public void revert() {
        play.unmergeActorState(time, obstacleInstance, Optional.empty());
    }

}
