package ca.ulaval.glo2004.visualigue.services.play.commands;

import ca.ulaval.glo2004.visualigue.domain.play.Play;
import ca.ulaval.glo2004.visualigue.domain.play.actorinstance.BallInstance;
import ca.ulaval.glo2004.visualigue.domain.play.actorinstance.PlayerInstance;
import ca.ulaval.glo2004.visualigue.domain.play.actorstate.BallState;
import ca.ulaval.glo2004.visualigue.domain.play.actorstate.transition.LinearTransition;
import ca.ulaval.glo2004.visualigue.utils.geometry.Vector2;

public class BallCreationCommand implements Command {

    private Play play;
    private Integer time;
    private String ownerPlayerInstanceUUID;
    private Vector2 position;

    private BallInstance ballInstance;

    public BallCreationCommand(Play play, Integer time, String ownerPlayerInstanceUUID, Vector2 position) {
        this.play = play;
        this.time = time;
        this.ownerPlayerInstanceUUID = ownerPlayerInstanceUUID;
        this.position = position;
    }

    @Override
    public void execute() {
        PlayerInstance playerInstance = (PlayerInstance) play.getActorInstance(ownerPlayerInstanceUUID);
        BallState ballState = new BallState(position, new LinearTransition(), playerInstance);
        ballInstance = new BallInstance();
        play.mergeKeyframe(time, ballInstance, ballState);
    }

    @Override
    public void revert() {
        play.unmergeKeyframe(time, ballInstance, null);
    }

}
