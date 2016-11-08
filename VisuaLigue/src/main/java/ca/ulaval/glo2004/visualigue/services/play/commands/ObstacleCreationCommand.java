package ca.ulaval.glo2004.visualigue.services.play.commands;

import ca.ulaval.glo2004.visualigue.domain.obstacle.Obstacle;
import ca.ulaval.glo2004.visualigue.domain.obstacle.ObstacleNotFoundException;
import ca.ulaval.glo2004.visualigue.domain.obstacle.ObstacleRepository;
import ca.ulaval.glo2004.visualigue.domain.play.Play;
import ca.ulaval.glo2004.visualigue.domain.play.actor.ObstacleActor;
import ca.ulaval.glo2004.visualigue.domain.play.actorstate.ObstacleState;
import ca.ulaval.glo2004.visualigue.utils.EventHandler;
import ca.ulaval.glo2004.visualigue.utils.geometry.Vector2;

public class ObstacleCreationCommand extends Command {

    private String obstacleActorUUID;
    private Vector2 position;
    private EventHandler<Play> onFrameChanged;
    private ObstacleRepository obstacleRepository;

    private ObstacleActor obstacleActor;

    public ObstacleCreationCommand(Play play, Long time, String obstacleActorUUID, Vector2 position, ObstacleRepository obstacleRepository, EventHandler<Play> onFrameChanged) {
        super(play, time);
        this.obstacleActorUUID = obstacleActorUUID;
        this.position = position;
        this.obstacleRepository = obstacleRepository;
        this.onFrameChanged = onFrameChanged;
    }

    @Override
    public void execute() throws ObstacleNotFoundException {
        ObstacleState obstacleState = new ObstacleState(position);
        Obstacle obstacle = obstacleRepository.get(obstacleActorUUID);
        obstacleActor = new ObstacleActor(obstacle);
        play.mergeKeyframe(time, obstacleActor, obstacleState);
        onFrameChanged.fire(this, play);
    }

    @Override
    public void revert() {
        play.unmergeKeyframe(time, obstacleActor, null);
        onFrameChanged.fire(this, play);
    }

}
