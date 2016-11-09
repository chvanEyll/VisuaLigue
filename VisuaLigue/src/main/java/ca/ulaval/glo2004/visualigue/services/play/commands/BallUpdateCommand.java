package ca.ulaval.glo2004.visualigue.services.play.commands;

import ca.ulaval.glo2004.visualigue.domain.play.Play;
import ca.ulaval.glo2004.visualigue.domain.play.actor.BallActor;
import ca.ulaval.glo2004.visualigue.domain.play.actor.PlayerActor;
import ca.ulaval.glo2004.visualigue.domain.play.actorstate.BallState;
import ca.ulaval.glo2004.visualigue.domain.play.keyframe.Keyframe;
import ca.ulaval.glo2004.visualigue.domain.play.keyframe.transition.LinearKeyframeTransition;
import ca.ulaval.glo2004.visualigue.utils.EventHandler;
import ca.ulaval.glo2004.visualigue.utils.geometry.Vector2;

public class BallUpdateCommand extends Command {

    private String ballActorUUID;
    private String ownerPlayerActorUUID;
    private Vector2 position;
    private EventHandler<Play> onFrameChanged;

    private Keyframe oldPositionKeyframe;
    private Keyframe oldPositionKeyframeAtLastKeyPoint;
    private Keyframe oldOwnerPlayerActorKeyframe;

    public BallUpdateCommand(Play play, Long time, String ballActorUUID, String ownerPlayerActorUUID, Vector2 position, EventHandler<Play> onFrameChanged) {
        super(play, time);
        this.ballActorUUID = ballActorUUID;
        this.ownerPlayerActorUUID = ownerPlayerActorUUID;
        this.position = position;
        this.onFrameChanged = onFrameChanged;
    }

    @Override
    public void execute() {
        BallActor ballActor = (BallActor) play.getActor(ballActorUUID);
        oldPositionKeyframe = play.merge(time, ballActor, BallState.getPositionProperty(), position, new LinearKeyframeTransition());
        if (play.previousKeyPointExists(time)) {
            Vector2 lastPosition = (Vector2) play.getActorLowerPropertyValue(time, ballActor, BallState.getPositionProperty());
            oldPositionKeyframeAtLastKeyPoint = play.merge(play.getPreviousKeyPointTime(time), ballActor, BallState.getPositionProperty(), lastPosition, new LinearKeyframeTransition());
        }
        if (ownerPlayerActorUUID != null) {
            PlayerActor playerActor = (PlayerActor) play.getActor(ownerPlayerActorUUID);
            oldOwnerPlayerActorKeyframe = play.merge(time, ballActor, BallState.getOwnerProperty(), playerActor, new LinearKeyframeTransition());
        }
        onFrameChanged.fire(this, play);
    }

    @Override
    public void revert() {
        BallActor createdBallActor = (BallActor) play.getActor(ballActorUUID);
        play.unmerge(time, createdBallActor, BallState.getPositionProperty(), oldPositionKeyframe);
        if (play.previousKeyPointExists(time)) {
            play.unmerge(play.getPreviousKeyPointTime(time), createdBallActor, BallState.getPositionProperty(), oldPositionKeyframeAtLastKeyPoint);
        }
        if (ownerPlayerActorUUID != null) {
            play.unmerge(time, createdBallActor, BallState.getOwnerProperty(), oldOwnerPlayerActorKeyframe);
        }
        onFrameChanged.fire(this, play);
    }

}
