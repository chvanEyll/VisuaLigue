package ca.ulaval.glo2004.visualigue.services.play.commands;

import ca.ulaval.glo2004.visualigue.domain.obstacle.ObstacleNotFoundException;
import ca.ulaval.glo2004.visualigue.domain.play.Play;
import ca.ulaval.glo2004.visualigue.domain.play.actor.ObstacleActor;
import ca.ulaval.glo2004.visualigue.domain.play.actorstate.ObstacleState;
import ca.ulaval.glo2004.visualigue.domain.play.keyframe.Keyframe;
import ca.ulaval.glo2004.visualigue.domain.play.keyframe.transition.LinearKeyframeTransition;
import ca.ulaval.glo2004.visualigue.utils.EventHandler;
import ca.ulaval.glo2004.visualigue.utils.geometry.Vector2;

public class ObstaclePositionUpdateCommand extends Command {

    private String obstacleActorUUID;
    private Vector2 position;
    private EventHandler<Play> onFrameChanged;

    private Keyframe oldObstaclePositionKeyframe;

    public ObstaclePositionUpdateCommand(Play play, Long time, String obstacleActorUUID, Vector2 position, EventHandler<Play> onFrameChanged) {
        super(play, time);
        this.obstacleActorUUID = obstacleActorUUID;
        this.position = position;
        this.onFrameChanged = onFrameChanged;
    }

    @Override
    public void execute() throws ObstacleNotFoundException {
        ObstacleActor obstacleActor = (ObstacleActor) play.getActor(obstacleActorUUID);
        oldObstaclePositionKeyframe = play.merge(0L, obstacleActor, ObstacleState.getPositionProperty(), position, new LinearKeyframeTransition());
        onFrameChanged.fire(this, play);
    }

    @Override
    public void revert() {
        ObstacleActor obstacleActor = (ObstacleActor) play.getActor(obstacleActorUUID);
        play.unmerge(0L, obstacleActor, ObstacleState.getPositionProperty(), oldObstaclePositionKeyframe);
        onFrameChanged.fire(this, play);
    }

}
