package ca.ulaval.glo2004.visualigue.services.play.commands;

import ca.ulaval.glo2004.visualigue.domain.play.Play;
import ca.ulaval.glo2004.visualigue.domain.play.actor.PlayerActor;
import ca.ulaval.glo2004.visualigue.domain.play.actorstate.ActorState;
import ca.ulaval.glo2004.visualigue.domain.play.actorstate.PlayerState;
import ca.ulaval.glo2004.visualigue.domain.play.actorstate.transition.LinearStateTransition;
import ca.ulaval.glo2004.visualigue.utils.EventHandler;

public class PlayerOrientationUpdateCommand extends Command {

    private String ownerPlayerActorUUID;
    private Double orientation;
    private EventHandler<Play> onFrameChanged;

    private PlayerActor playerActor;
    private ActorState oldPlayerState;

    public PlayerOrientationUpdateCommand(Play play, Integer time, String ownerPlayerActorUUID, Double orientation, EventHandler<Play> onFrameChanged) {
        super(play, time);
        this.ownerPlayerActorUUID = ownerPlayerActorUUID;
        this.orientation = orientation;
        this.onFrameChanged = onFrameChanged;
    }

    @Override
    public void execute() {
        PlayerState playerState = new PlayerState(null, null, orientation, new LinearStateTransition());
        playerActor = (PlayerActor) play.getActor(ownerPlayerActorUUID);
        oldPlayerState = play.mergeKeyframe(time, playerActor, playerState);
        onFrameChanged.fire(this, play);
    }

    @Override
    public void revert() {
        play.unmergeKeyframe(time, playerActor, oldPlayerState);
        onFrameChanged.fire(this, play);
    }
}
