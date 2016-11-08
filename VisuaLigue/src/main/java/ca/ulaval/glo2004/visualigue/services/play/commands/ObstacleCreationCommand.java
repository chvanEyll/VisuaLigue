package ca.ulaval.glo2004.visualigue.services.play.commands;

import ca.ulaval.glo2004.visualigue.domain.obstacle.Obstacle;
import ca.ulaval.glo2004.visualigue.domain.obstacle.ObstacleNotFoundException;
import ca.ulaval.glo2004.visualigue.domain.obstacle.ObstacleRepository;
import ca.ulaval.glo2004.visualigue.domain.play.Play;
import ca.ulaval.glo2004.visualigue.domain.play.actor.ObstacleActor;
import ca.ulaval.glo2004.visualigue.domain.play.actorstate.ObstacleState;
import ca.ulaval.glo2004.visualigue.domain.play.keyframe.transition.LinearKeyframeTransition;
import ca.ulaval.glo2004.visualigue.utils.EventHandler;
import ca.ulaval.glo2004.visualigue.utils.geometry.Vector2;

public class ObstacleCreationCommand extends Command {

    private String obstacleUUID;
    private Vector2 position;
    private EventHandler<Play> onFrameChanged;
    private ObstacleRepository obstacleRepository;

    private ObstacleActor createdObstacleActor;
    private String createdObstacleActorUUID;

    public ObstacleCreationCommand(Play play, Long time, String obstacleUUID, Vector2 position, ObstacleRepository obstacleRepository, EventHandler<Play> onFrameChanged) {
        super(play, time);
        this.obstacleUUID = obstacleUUID;
        this.position = position;
        this.obstacleRepository = obstacleRepository;
        this.onFrameChanged = onFrameChanged;
    }

    @Override
    public void execute() throws ObstacleNotFoundException {
        Obstacle obstacle = obstacleRepository.get(obstacleUUID);
        createdObstacleActor = new ObstacleActor(obstacle);
        if (createdObstacleActorUUID != null) {
            createdObstacleActor.setUUID(createdObstacleActorUUID);
        } else {
            createdObstacleActorUUID = createdObstacleActor.getUUID();
        }
        play.merge(0L, createdObstacleActor, ObstacleState.getPositionProperty(), position, new LinearKeyframeTransition());
        onFrameChanged.fire(this, play);
    }

    @Override
    public void revert() {
        play.unmerge(0L, createdObstacleActor, ObstacleState.getPositionProperty(), null);
        onFrameChanged.fire(this, play);
    }

}
