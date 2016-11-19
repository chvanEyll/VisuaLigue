package ca.ulaval.glo2004.visualigue.services.play.commands;

import ca.ulaval.glo2004.visualigue.domain.play.Play;
import ca.ulaval.glo2004.visualigue.domain.play.actor.BallActor;
import ca.ulaval.glo2004.visualigue.domain.play.actorstate.BallState;
import ca.ulaval.glo2004.visualigue.domain.play.keyframe.Keyframe;
import ca.ulaval.glo2004.visualigue.domain.play.keyframe.transition.LinearKeyframeTransition;
import ca.ulaval.glo2004.visualigue.utils.geometry.Vector2;

public class BallPositionUpdateCommand extends Command {

    private String ballActorUUID;
    private Vector2 position;

    private Keyframe oldPositionKeyframe;
    private Keyframe oldPositionKeyframeAtLastKeyPoint;

    public BallPositionUpdateCommand(Play play, Long time, String ballActorUUID, Vector2 position) {
        super(play, time);
        this.ballActorUUID = ballActorUUID;
        this.position = position;
    }

    @Override
    public Long execute() {
        BallActor ballActor = (BallActor) play.getActor(ballActorUUID);
        oldPositionKeyframe = play.merge(time, ballActor, BallState.getPositionProperty(), position, new LinearKeyframeTransition());
        if (play.previousKeyPointExists(time)) {
            Vector2 lastPosition = (Vector2) play.getActorLowerPropertyValue(time, ballActor, BallState.getPositionProperty());
            oldPositionKeyframeAtLastKeyPoint = play.merge(play.getPreviousKeyPointTime(time), ballActor, BallState.getPositionProperty(), lastPosition, new LinearKeyframeTransition());
        }
        return time;
    }

    @Override
    public Long revert() {
        BallActor createdBallActor = (BallActor) play.getActor(ballActorUUID);
        play.unmerge(time, createdBallActor, BallState.getPositionProperty(), oldPositionKeyframe);
        if (play.previousKeyPointExists(time)) {
            play.unmerge(play.getPreviousKeyPointTime(time), createdBallActor, BallState.getPositionProperty(), oldPositionKeyframeAtLastKeyPoint);
        }
        return time;
    }

}
