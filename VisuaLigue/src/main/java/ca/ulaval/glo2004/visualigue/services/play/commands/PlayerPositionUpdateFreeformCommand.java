package ca.ulaval.glo2004.visualigue.services.play.commands;

import ca.ulaval.glo2004.visualigue.domain.play.Play;
import ca.ulaval.glo2004.visualigue.domain.play.PlayRepository;
import ca.ulaval.glo2004.visualigue.domain.play.actorinstance.PlayerInstance;
import ca.ulaval.glo2004.visualigue.domain.play.actorstate.ActorState;
import ca.ulaval.glo2004.visualigue.domain.play.actorstate.PlayerState;
import ca.ulaval.glo2004.visualigue.domain.play.position.Position;
import ca.ulaval.glo2004.visualigue.domain.play.transition.Transition;
import java.util.Optional;
import java.util.UUID;
import javax.inject.Inject;

public class PlayerPositionUpdateFreeformCommand implements Command {

    private Play play;
    private Integer time;
    private UUID ownerPlayerInstanceUUID;
    private Position position;
    private Transition positionTransition;
    @Inject private PlayRepository playRepository;

    private PlayerInstance playerInstance;
    private Optional<ActorState> oldPlayerState;

    public PlayerPositionUpdateFreeformCommand(Play play, Integer time, UUID ownerPlayerInstanceUUID, Position position, Transition positionTransition) {
        this.time = time;
        this.ownerPlayerInstanceUUID = ownerPlayerInstanceUUID;
        this.position = position;
        this.positionTransition = positionTransition;
    }

    @Override
    public void execute() {
        PlayerState playerState = new PlayerState(Optional.of(position), Optional.of(positionTransition), Optional.empty());
        playerInstance = (PlayerInstance) play.getActorInstance(ownerPlayerInstanceUUID);
        oldPlayerState = play.mergeActorState(time, playerInstance, playerState);
    }

    @Override
    public void revert() {
        play.unmergeActorState(time, playerInstance, oldPlayerState);
    }

}
