package ca.ulaval.glo2004.visualigue.services.play.commands;

import ca.ulaval.glo2004.visualigue.domain.play.Play;
import ca.ulaval.glo2004.visualigue.domain.play.actor.BallActor;
import ca.ulaval.glo2004.visualigue.domain.play.actor.PlayerActor;
import ca.ulaval.glo2004.visualigue.domain.play.actorstate.BallState;
import ca.ulaval.glo2004.visualigue.domain.play.actorstate.transition.LinearStateTransition;
import ca.ulaval.glo2004.visualigue.utils.EventHandler;
import ca.ulaval.glo2004.visualigue.utils.geometry.Vector2;

public class BallCreationCommand extends Command {

    private String ownerPlayerActorUUID;
    private Vector2 position;
    private EventHandler<Play> onFrameChanged;

    private BallActor ballActor;

    public BallCreationCommand(Play play, Long time, String ownerPlayerActorUUID, Vector2 position, EventHandler<Play> onFrameChanged) {
        super(play, time);
        this.ownerPlayerActorUUID = ownerPlayerActorUUID;
        this.position = position;
        this.onFrameChanged = onFrameChanged;
    }

    @Override
    public void execute() {
        PlayerActor playerActor = null;
        if (ownerPlayerActorUUID != null) {
            playerActor = (PlayerActor) play.getActor(ownerPlayerActorUUID);
        }
        BallState ballState = new BallState(position, new LinearStateTransition(), playerActor);
        ballActor = new BallActor();
        play.mergeKeyframe(time, ballActor, ballState);
        onFrameChanged.fire(this, play);
    }

    @Override
    public void revert() {
        play.unmergeKeyframe(time, ballActor, null);
        onFrameChanged.fire(this, play);
    }

}
