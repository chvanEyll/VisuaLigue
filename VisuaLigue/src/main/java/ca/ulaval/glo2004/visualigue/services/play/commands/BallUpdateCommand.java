package ca.ulaval.glo2004.visualigue.services.play.commands;

import ca.ulaval.glo2004.visualigue.domain.play.Play;
import ca.ulaval.glo2004.visualigue.domain.play.actor.BallActor;
import ca.ulaval.glo2004.visualigue.domain.play.actor.PlayerActor;
import ca.ulaval.glo2004.visualigue.domain.play.actorstate.BallState;
import ca.ulaval.glo2004.visualigue.domain.play.keyframe.transition.LinearKeyframeTransition;
import ca.ulaval.glo2004.visualigue.domain.play.keyframe.Keyframe;
import ca.ulaval.glo2004.visualigue.utils.EventHandler;
import ca.ulaval.glo2004.visualigue.utils.geometry.Vector2;

public class BallUpdateCommand extends Command {

    private String ballActorUUID;
    private String ownerPlayerActorUUID;
    private Vector2 position;
    private EventHandler<Play> onFrameChanged;

    private BallActor createdBallActor;
    private Keyframe oldPositionKeyframe;
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
        PlayerActor playerActor = (PlayerActor) play.getActor(ownerPlayerActorUUID);
        createdBallActor = (BallActor) play.getActor(ballActorUUID);
        oldPositionKeyframe = play.merge(time, createdBallActor, BallState.getPositionProperty(), position, new LinearKeyframeTransition());
        oldOwnerPlayerActorKeyframe = play.merge(time, createdBallActor, BallState.getOwnerProperty(), playerActor, new LinearKeyframeTransition());
        onFrameChanged.fire(this, play);
    }

    @Override
    public void revert() {
        play.unmerge(time, createdBallActor, BallState.getOwnerProperty(), oldOwnerPlayerActorKeyframe);
        play.unmerge(time, createdBallActor, BallState.getPositionProperty(), oldPositionKeyframe);
        onFrameChanged.fire(this, play);
    }

}
