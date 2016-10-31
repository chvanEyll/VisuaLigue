package ca.ulaval.glo2004.visualigue.services.play.commands;

import ca.ulaval.glo2004.visualigue.domain.play.Play;
import ca.ulaval.glo2004.visualigue.domain.play.PlayRepository;
import ca.ulaval.glo2004.visualigue.domain.play.actorinstance.BallInstance;
import ca.ulaval.glo2004.visualigue.domain.play.actorinstance.PlayerInstance;
import ca.ulaval.glo2004.visualigue.domain.play.actorstate.ActorState;
import ca.ulaval.glo2004.visualigue.domain.play.actorstate.BallState;
import ca.ulaval.glo2004.visualigue.utils.geometry.Vector2;
import javax.inject.Inject;

public class BallUpdateCommand implements Command {

    private Play play;
    private Integer time;
    private String ballInstanceUUID;
    private String ownerPlayerInstanceUUID;
    private Vector2 position;
    @Inject private PlayRepository playRepository;

    private BallInstance ballInstance;
    private ActorState oldBallState;

    public BallUpdateCommand(Play play, Integer time, String ballInstanceUUID, String ownerPlayerInstanceUUID, Vector2 position) {
        this.play = play;
        this.time = time;
        this.ballInstanceUUID = ballInstanceUUID;
        this.ownerPlayerInstanceUUID = ownerPlayerInstanceUUID;
        this.position = position;
    }

    @Override
    public void execute() {
        PlayerInstance playerInstance = (PlayerInstance) play.getActorInstance(ownerPlayerInstanceUUID);
        ballInstance = (BallInstance) play.getActorInstance(ballInstanceUUID);
        BallState ballState = new BallState(position, playerInstance);
        oldBallState = play.mergeKeyframe(time, ballInstance, ballState);
    }

    @Override
    public void revert() {
        play.unmergeKeyframe(time, ballInstance, oldBallState);
    }

}
