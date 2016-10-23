package ca.ulaval.glo2004.visualigue.services.play.commands;

import ca.ulaval.glo2004.visualigue.domain.play.Play;
import ca.ulaval.glo2004.visualigue.domain.play.PlayRepository;
import ca.ulaval.glo2004.visualigue.domain.play.actorinstance.PlayerInstance;
import ca.ulaval.glo2004.visualigue.domain.play.actorstate.ActorState;
import ca.ulaval.glo2004.visualigue.domain.play.actorstate.PlayerState;
import ca.ulaval.glo2004.visualigue.domain.play.position.Position;
import java.util.Optional;
import java.util.UUID;
import javax.inject.Inject;

public class PlayerPositionUpdateDirectCommand implements Command {

    private Play play;
    private Integer time;
    private UUID ownerPlayerInstanceUUID;
    private Position position;
    @Inject private PlayRepository playRepository;

    private PlayerInstance playerInstance;
    private Optional<ActorState> oldPlayerState;

    public PlayerPositionUpdateDirectCommand(Play play, Integer time, UUID ownerPlayerInstanceUUID, Position position) {
        this.play = play;
        this.time = time;
        this.ownerPlayerInstanceUUID = ownerPlayerInstanceUUID;
        this.position = position;
    }

    @Override
    public void execute() {
        PlayerState playerState = new PlayerState(Optional.of(position), Optional.empty(), Optional.empty());
        playerInstance = (PlayerInstance) play.getActorInstance(ownerPlayerInstanceUUID);
        oldPlayerState = play.mergeKeyframe(time, playerInstance, playerState);
    }

    @Override
    public void revert() {
        play.unmergeKeyframe(time, playerInstance, oldPlayerState);
    }

}
