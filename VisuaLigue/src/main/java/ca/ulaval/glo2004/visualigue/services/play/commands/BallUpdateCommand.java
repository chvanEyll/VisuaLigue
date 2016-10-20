package ca.ulaval.glo2004.visualigue.services.play.commands;

import ca.ulaval.glo2004.visualigue.domain.play.Play;
import ca.ulaval.glo2004.visualigue.domain.play.PlayRepository;
import ca.ulaval.glo2004.visualigue.domain.play.actor.ballinstance.BallInstance;
import ca.ulaval.glo2004.visualigue.domain.play.actor.playerinstance.PlayerInstance;
import ca.ulaval.glo2004.visualigue.domain.play.actorstate.BallState;
import ca.ulaval.glo2004.visualigue.domain.play.position.Position;
import java.util.Optional;
import java.util.UUID;
import javax.inject.Inject;

public class BallUpdateCommand implements Command {

    private UUID playUUID;
    private Integer time;
    private UUID ballInstanceUUID;
    private UUID ownerPlayerInstanceUUID;
    private Position position;
    @Inject private PlayRepository playRepository;

    private Play play;
    private BallState ballState;
    private BallInstance ballInstance;
    private PlayerInstance playerInstance;
    private BallState oldBallState;

    public BallUpdateCommand(UUID playUUID, Integer time, UUID ballInstanceUUID, UUID ownerPlayerInstanceUUID, Position position) {
        this.playUUID = playUUID;
        this.time = time;
        this.ballInstanceUUID = ballInstanceUUID;
        this.ownerPlayerInstanceUUID = ownerPlayerInstanceUUID;
        this.position = position;
    }
    
    @Override
    public void execute() throws Exception {
        play = playRepository.get(playUUID);
        playerInstance = (PlayerInstance) play.getActorInstance(ownerPlayerInstanceUUID);
        ballInstance = (BallInstance) play.getActorInstance(ballInstanceUUID);
        ballState = new BallState(Optional.of(position), Optional.of(playerInstance));
        oldBallState = (BallState) play.mergeActorState(time, ballInstance, ballState);
    }

    @Override
    public void revert() {
        play.unmergeActorState(time, ballInstance, ballState);
    }

}
