package ca.ulaval.glo2004.visualigue.services.play.commands;

import ca.ulaval.glo2004.visualigue.domain.play.Play;
import ca.ulaval.glo2004.visualigue.domain.play.PlayRepository;
import ca.ulaval.glo2004.visualigue.domain.play.actor.BallInstance;
import ca.ulaval.glo2004.visualigue.domain.play.actor.PlayerInstance;
import ca.ulaval.glo2004.visualigue.domain.play.actorstate.BallState;
import ca.ulaval.glo2004.visualigue.domain.play.position.Position;
import java.util.Optional;
import java.util.UUID;
import javax.inject.Inject;

public class BallCreationCommand implements Command {

    private UUID playUUID;
    private Integer time;
    private UUID ownerPlayerInstanceUUID;
    private Position position;
    @Inject private PlayRepository playRepository;

    private Play play;
    private BallInstance ballInstance;

    public BallCreationCommand(UUID playUUID, Integer time, UUID ownerPlayerInstanceUUID, Position position) {
        this.playUUID = playUUID;
        this.time = time;
        this.ownerPlayerInstanceUUID = ownerPlayerInstanceUUID;
        this.position = position;
    }

    @Override
    public void execute() throws Exception {
        play = playRepository.get(playUUID);
        PlayerInstance playerInstance = (PlayerInstance) play.getActorInstance(ownerPlayerInstanceUUID);
        BallState ballState = new BallState(Optional.of(position), Optional.of(playerInstance));
        ballInstance = new BallInstance();
        play.mergeActorState(time, ballInstance, ballState);
    }

    @Override
    public void revert() {
        play.unmergeActorState(time, ballInstance, Optional.empty());
    }

}
