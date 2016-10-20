package ca.ulaval.glo2004.visualigue.services.play.commands;

import ca.ulaval.glo2004.visualigue.domain.play.Play;
import ca.ulaval.glo2004.visualigue.domain.play.PlayRepository;
import ca.ulaval.glo2004.visualigue.domain.play.actor.playerinstance.PlayerInstance;
import ca.ulaval.glo2004.visualigue.domain.play.actorstate.ActorState;
import ca.ulaval.glo2004.visualigue.domain.play.actorstate.PlayerState;
import ca.ulaval.glo2004.visualigue.domain.play.position.Position;
import java.util.Optional;
import java.util.UUID;
import javax.inject.Inject;

public class PlayerPositionUpdateCommand implements Command {

    private UUID playUUID;
    private Integer time;
    private UUID ownerPlayerInstanceUUID;
    private Position position;
    @Inject private PlayRepository playRepository;

    private Play play;
    private PlayerInstance playerInstance;
    private Optional<ActorState> oldPlayerState;

    public PlayerPositionUpdateCommand(UUID playUUID, Integer time, UUID ownerPlayerInstanceUUID, Position position) {
        this.playUUID = playUUID;
        this.time = time;
        this.ownerPlayerInstanceUUID = ownerPlayerInstanceUUID;
        this.position = position;
    }

    @Override
    public void execute() throws Exception {
        play = playRepository.get(playUUID);
        PlayerState playerState = new PlayerState(Optional.of(position), Optional.empty());
        playerInstance = (PlayerInstance) play.getActorInstance(ownerPlayerInstanceUUID);
        oldPlayerState = play.mergeActorState(time, playerInstance, playerState);
    }

    @Override
    public void revert() {
        play.unmergeActorState(time, playerInstance, oldPlayerState);
    }

}
