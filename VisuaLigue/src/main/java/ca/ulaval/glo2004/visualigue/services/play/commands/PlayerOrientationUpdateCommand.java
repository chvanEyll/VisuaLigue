package ca.ulaval.glo2004.visualigue.services.play.commands;

import ca.ulaval.glo2004.visualigue.domain.play.Play;
import ca.ulaval.glo2004.visualigue.domain.play.PlayRepository;
import ca.ulaval.glo2004.visualigue.domain.play.actorinstance.PlayerInstance;
import ca.ulaval.glo2004.visualigue.domain.play.actorstate.ActorState;
import ca.ulaval.glo2004.visualigue.domain.play.actorstate.PlayerState;
import java.util.Optional;
import java.util.UUID;
import javax.inject.Inject;

public class PlayerOrientationUpdateCommand implements Command {

    private UUID playUUID;
    private Integer time;
    private UUID ownerPlayerInstanceUUID;
    private Double orientation;
    @Inject private PlayRepository playRepository;

    private Play play;
    private PlayerInstance playerInstance;
    private Optional<ActorState> oldPlayerState;

    public PlayerOrientationUpdateCommand(UUID playUUID, Integer time, UUID ownerPlayerInstanceUUID, Double orientation) {
        this.playUUID = playUUID;
        this.time = time;
        this.ownerPlayerInstanceUUID = ownerPlayerInstanceUUID;
        this.orientation = orientation;
    }

    @Override
    public void execute() throws Exception {
        play = playRepository.get(playUUID);
        PlayerState playerState = new PlayerState(Optional.empty(), Optional.empty(), Optional.of(orientation));
        playerInstance = (PlayerInstance) play.getActorInstance(ownerPlayerInstanceUUID);
        oldPlayerState = play.mergeActorState(time, playerInstance, playerState);
    }

    @Override
    public void revert() {
        play.unmergeActorState(time, playerInstance, oldPlayerState);
    }
}
