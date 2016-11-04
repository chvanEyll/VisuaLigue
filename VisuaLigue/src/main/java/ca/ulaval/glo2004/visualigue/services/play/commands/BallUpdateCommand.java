package ca.ulaval.glo2004.visualigue.services.play.commands;

import ca.ulaval.glo2004.visualigue.domain.play.Play;
import ca.ulaval.glo2004.visualigue.domain.play.actorinstance.BallInstance;
import ca.ulaval.glo2004.visualigue.domain.play.actorinstance.PlayerInstance;
import ca.ulaval.glo2004.visualigue.domain.play.actorstate.ActorState;
import ca.ulaval.glo2004.visualigue.domain.play.actorstate.BallState;
import ca.ulaval.glo2004.visualigue.domain.play.actorstate.transition.LinearTransition;
import ca.ulaval.glo2004.visualigue.utils.EventHandler;
import ca.ulaval.glo2004.visualigue.utils.geometry.Vector2;

public class BallUpdateCommand extends Command {

    private String ballInstanceUUID;
    private String ownerPlayerInstanceUUID;
    private Vector2 position;
    private EventHandler<Play> onFrameChanged;

    private BallInstance ballInstance;
    private ActorState oldBallState;

    public BallUpdateCommand(Play play, Integer time, String ballInstanceUUID, String ownerPlayerInstanceUUID, Vector2 position, EventHandler<Play> onFrameChanged) {
        super(play, time);
        this.ballInstanceUUID = ballInstanceUUID;
        this.ownerPlayerInstanceUUID = ownerPlayerInstanceUUID;
        this.position = position;
        this.onFrameChanged = onFrameChanged;
    }

    @Override
    public void execute() {
        PlayerInstance playerInstance = (PlayerInstance) play.getActorInstance(ownerPlayerInstanceUUID);
        ballInstance = (BallInstance) play.getActorInstance(ballInstanceUUID);
        BallState ballState = new BallState(position, new LinearTransition(), playerInstance);
        oldBallState = play.mergeKeyframe(time, ballInstance, ballState);
        onFrameChanged.fire(this, play);
    }

    @Override
    public void revert() {
        play.unmergeKeyframe(time, ballInstance, oldBallState);
        onFrameChanged.fire(this, play);
    }

}
