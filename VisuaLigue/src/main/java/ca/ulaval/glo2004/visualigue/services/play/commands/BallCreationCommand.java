package ca.ulaval.glo2004.visualigue.services.play.commands;

import ca.ulaval.glo2004.visualigue.domain.play.Play;
import ca.ulaval.glo2004.visualigue.domain.play.actorinstance.BallInstance;
import ca.ulaval.glo2004.visualigue.domain.play.actorinstance.PlayerInstance;
import ca.ulaval.glo2004.visualigue.domain.play.actorstate.BallState;
import ca.ulaval.glo2004.visualigue.domain.play.actorstate.transition.LinearStateTransition;
import ca.ulaval.glo2004.visualigue.utils.EventHandler;
import ca.ulaval.glo2004.visualigue.utils.geometry.Vector2;

public class BallCreationCommand extends Command {

    private String ownerPlayerInstanceUUID;
    private Vector2 position;
    private EventHandler<Play> onFrameChanged;

    private BallInstance ballInstance;

    public BallCreationCommand(Play play, Integer time, String ownerPlayerInstanceUUID, Vector2 position, EventHandler<Play> onFrameChanged) {
        super(play, time);
        this.ownerPlayerInstanceUUID = ownerPlayerInstanceUUID;
        this.position = position;
        this.onFrameChanged = onFrameChanged;
    }

    @Override
    public void execute() {
        PlayerInstance playerInstance = null;
        if (ownerPlayerInstanceUUID != null) {
            playerInstance = (PlayerInstance) play.getActorInstance(ownerPlayerInstanceUUID);
        }
        BallState ballState = new BallState(position, new LinearStateTransition(), playerInstance);
        ballInstance = new BallInstance();
        play.mergeKeyframe(time, ballInstance, ballState);
        onFrameChanged.fire(this, play);
    }

    @Override
    public void revert() {
        play.unmergeKeyframe(time, ballInstance, null);
        onFrameChanged.fire(this, play);
    }

}
