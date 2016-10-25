package ca.ulaval.glo2004.visualigue.services.play.commands;

import ca.ulaval.glo2004.visualigue.domain.play.Play;
import ca.ulaval.glo2004.visualigue.domain.play.PlayRepository;
import ca.ulaval.glo2004.visualigue.domain.play.actorinstance.PlayerInstance;
import ca.ulaval.glo2004.visualigue.domain.play.actorstate.ActorState;
import ca.ulaval.glo2004.visualigue.domain.play.actorstate.PlayerState;
import ca.ulaval.glo2004.visualigue.utils.geometry.Vector2;
import java.util.Optional;
import java.util.UUID;
import javax.inject.Inject;
import ca.ulaval.glo2004.visualigue.domain.play.keyframe.KeyframeTransition;

public class PlayerPositionUpdateFreeformCommand implements Command {

    private Play play;
    private Integer time;
    private UUID ownerPlayerInstanceUUID;
    private Vector2 position;
    private KeyframeTransition positionTransition;
    @Inject private PlayRepository playRepository;

    private PlayerInstance playerInstance;
    private Optional<ActorState> oldPlayerState;

    public PlayerPositionUpdateFreeformCommand(Play play, Integer time, UUID ownerPlayerInstanceUUID, Vector2 position, KeyframeTransition positionTransition) {
        this.time = time;
        this.ownerPlayerInstanceUUID = ownerPlayerInstanceUUID;
        this.position = position;
        this.positionTransition = positionTransition;
    }

    @Override
    public void execute() {
        PlayerState playerState = new PlayerState(Optional.of(position), Optional.of(positionTransition), Optional.empty());
        playerInstance = (PlayerInstance) play.getActorInstance(ownerPlayerInstanceUUID);
        oldPlayerState = play.mergeKeyframe(time, playerInstance, playerState);
    }

    @Override
    public void revert() {
        play.unmergeKeyframe(time, playerInstance, oldPlayerState);
    }

}
