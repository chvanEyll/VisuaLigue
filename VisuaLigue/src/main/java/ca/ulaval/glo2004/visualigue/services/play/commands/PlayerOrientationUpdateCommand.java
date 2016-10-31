package ca.ulaval.glo2004.visualigue.services.play.commands;

import ca.ulaval.glo2004.visualigue.domain.play.Play;
import ca.ulaval.glo2004.visualigue.domain.play.PlayRepository;
import ca.ulaval.glo2004.visualigue.domain.play.actorinstance.PlayerInstance;
import ca.ulaval.glo2004.visualigue.domain.play.actorstate.ActorState;
import ca.ulaval.glo2004.visualigue.domain.play.actorstate.PlayerState;
import java.util.UUID;
import javax.inject.Inject;

public class PlayerOrientationUpdateCommand implements Command {

    private Play play;
    private Integer time;
    private UUID ownerPlayerInstanceUUID;
    private Double orientation;
    @Inject private PlayRepository playRepository;

    private PlayerInstance playerInstance;
    private ActorState oldPlayerState;

    public PlayerOrientationUpdateCommand(Play play, Integer time, UUID ownerPlayerInstanceUUID, Double orientation) {
        this.play = play;
        this.time = time;
        this.ownerPlayerInstanceUUID = ownerPlayerInstanceUUID;
        this.orientation = orientation;
    }

    @Override
    public void execute() {
        PlayerState playerState = new PlayerState(null, null, orientation);
        playerInstance = (PlayerInstance) play.getActorInstance(ownerPlayerInstanceUUID);
        oldPlayerState = play.mergeKeyframe(time, playerInstance, playerState);
    }

    @Override
    public void revert() {
        play.unmergeKeyframe(time, playerInstance, oldPlayerState);
    }
}
