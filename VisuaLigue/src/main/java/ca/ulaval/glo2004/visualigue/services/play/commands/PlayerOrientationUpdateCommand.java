package ca.ulaval.glo2004.visualigue.services.play.commands;

import ca.ulaval.glo2004.visualigue.domain.play.Play;
import ca.ulaval.glo2004.visualigue.domain.play.actorinstance.PlayerInstance;
import ca.ulaval.glo2004.visualigue.domain.play.actorstate.ActorState;
import ca.ulaval.glo2004.visualigue.domain.play.actorstate.PlayerState;
import ca.ulaval.glo2004.visualigue.domain.play.actorstate.transition.LinearStateTransition;
import ca.ulaval.glo2004.visualigue.utils.EventHandler;

public class PlayerOrientationUpdateCommand extends Command {

    private String ownerPlayerInstanceUUID;
    private Double orientation;
    private EventHandler<Play> onFrameChanged;

    private PlayerInstance playerInstance;
    private ActorState oldPlayerState;

    public PlayerOrientationUpdateCommand(Play play, Integer time, String ownerPlayerInstanceUUID, Double orientation, EventHandler<Play> onFrameChanged) {
        super(play, time);
        this.ownerPlayerInstanceUUID = ownerPlayerInstanceUUID;
        this.orientation = orientation;
        this.onFrameChanged = onFrameChanged;
    }

    @Override
    public void execute() {
        PlayerState playerState = new PlayerState(null, null, orientation, new LinearStateTransition());
        playerInstance = (PlayerInstance) play.getActorInstance(ownerPlayerInstanceUUID);
        oldPlayerState = play.mergeKeyframe(time, playerInstance, playerState);
        onFrameChanged.fire(this, play);
    }

    @Override
    public void revert() {
        play.unmergeKeyframe(time, playerInstance, oldPlayerState);
        onFrameChanged.fire(this, play);
    }
}
