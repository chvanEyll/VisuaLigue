package ca.ulaval.glo2004.visualigue.services.play.commands;

import ca.ulaval.glo2004.visualigue.domain.play.Play;
import ca.ulaval.glo2004.visualigue.domain.play.actor.BallActor;
import ca.ulaval.glo2004.visualigue.domain.play.actor.PlayerActor;
import ca.ulaval.glo2004.visualigue.domain.play.actorstate.ActorState;
import ca.ulaval.glo2004.visualigue.domain.play.actorstate.BallState;
import ca.ulaval.glo2004.visualigue.domain.play.actorstate.transition.LinearStateTransition;
import ca.ulaval.glo2004.visualigue.utils.EventHandler;
import ca.ulaval.glo2004.visualigue.utils.geometry.Vector2;

public class BallUpdateCommand extends Command {

    private String ballActorUUID;
    private String ownerPlayerActorUUID;
    private Vector2 position;
    private EventHandler<Play> onFrameChanged;

    private BallActor ballActor;
    private ActorState oldBallState;

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
        ballActor = (BallActor) play.getActor(ballActorUUID);
        BallState ballState = new BallState(position, new LinearStateTransition(), playerActor);
        oldBallState = play.mergeKeyframe(time, ballActor, ballState);
        onFrameChanged.fire(this, play);
    }

    @Override
    public void revert() {
        play.unmergeKeyframe(time, ballActor, oldBallState);
        onFrameChanged.fire(this, play);
    }

}
