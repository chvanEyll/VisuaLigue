package ca.ulaval.glo2004.visualigue.services.play.commands;

import ca.ulaval.glo2004.visualigue.domain.play.Play;
import ca.ulaval.glo2004.visualigue.domain.play.actor.BallActor;
import ca.ulaval.glo2004.visualigue.domain.play.actor.PlayerActor;
import ca.ulaval.glo2004.visualigue.domain.play.actorstate.BallState;
import ca.ulaval.glo2004.visualigue.domain.play.keyframe.transition.LinearKeyframeTransition;
import ca.ulaval.glo2004.visualigue.utils.EventHandler;
import ca.ulaval.glo2004.visualigue.utils.geometry.Vector2;

public class BallCreationCommand extends Command {

    private String ownerPlayerActorUUID;
    private Vector2 position;
    private EventHandler<Play> onFrameChanged;

    private BallActor ballActor;
    private PlayerActor ownerPlayerActor;
    private String ballActorUUID;

    public BallCreationCommand(Play play, Long time, String ownerPlayerActorUUID, Vector2 position, EventHandler<Play> onFrameChanged) {
        super(play, time);
        this.ownerPlayerActorUUID = ownerPlayerActorUUID;
        this.position = position;
        this.onFrameChanged = onFrameChanged;
    }

    @Override
    public void execute() {
        if (ownerPlayerActorUUID != null) {
            ownerPlayerActor = (PlayerActor) play.getActor(ownerPlayerActorUUID);
        }
        ballActor = new BallActor();
        if (ballActorUUID != null) {
            ballActor.setUUID(ballActorUUID);
        } else {
            ballActorUUID = ballActor.getUUID();
        }
        play.merge(0L, ballActor, BallState.getPositionProperty(), position, new LinearKeyframeTransition());
        play.merge(0L, ballActor, BallState.getOwnerProperty(), ownerPlayerActor, null);
        onFrameChanged.fire(this, play);
    }

    @Override
    public void revert() {
        play.unmerge(0L, ballActor, BallState.getOwnerProperty(), null);
        play.unmerge(0L, ballActor, BallState.getPositionProperty(), null);
        onFrameChanged.fire(this, play);
    }

}
