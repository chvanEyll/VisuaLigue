package ca.ulaval.glo2004.visualigue.services.play.commands;

import ca.ulaval.glo2004.visualigue.domain.play.Play;
import ca.ulaval.glo2004.visualigue.domain.play.actor.BallActor;
import ca.ulaval.glo2004.visualigue.domain.play.actor.PlayerActor;
import ca.ulaval.glo2004.visualigue.domain.play.actorstate.BallState;
import ca.ulaval.glo2004.visualigue.domain.play.keyframe.Keyframe;
import ca.ulaval.glo2004.visualigue.domain.play.keyframe.transition.LinearKeyframeTransition;
import ca.ulaval.glo2004.visualigue.utils.EventHandler;

public class BallOwnerUpdateCommand extends Command {

    private String ballActorUUID;
    private String ownerPlayerActorUUID;
    private EventHandler<Play> onFrameChanged;

    private Keyframe oldPositionKeyframe;
    private Keyframe oldPositionKeyframeAtLastKeyPoint;
    private Keyframe oldOwnerPlayerActorKeyframe;

    public BallOwnerUpdateCommand(Play play, Long time, String ballActorUUID, String ownerPlayerActorUUID, EventHandler<Play> onFrameChanged) {
        super(play, time);
        this.ballActorUUID = ballActorUUID;
        this.ownerPlayerActorUUID = ownerPlayerActorUUID;
        this.onFrameChanged = onFrameChanged;
    }

    @Override
    public void execute() {
        BallActor ballActor = (BallActor) play.getActor(ballActorUUID);
        if (ownerPlayerActorUUID != null) {
            PlayerActor playerActor = (PlayerActor) play.getActor(ownerPlayerActorUUID);
            oldOwnerPlayerActorKeyframe = play.merge(time, ballActor, BallState.getOwnerProperty(), playerActor, new LinearKeyframeTransition());
        }
        onFrameChanged.fire(this, play);
    }

    @Override
    public void revert() {
        BallActor createdBallActor = (BallActor) play.getActor(ballActorUUID);
        if (ownerPlayerActorUUID != null) {
            play.unmerge(time, createdBallActor, BallState.getOwnerProperty(), oldOwnerPlayerActorKeyframe);
        }
        onFrameChanged.fire(this, play);
    }

}
