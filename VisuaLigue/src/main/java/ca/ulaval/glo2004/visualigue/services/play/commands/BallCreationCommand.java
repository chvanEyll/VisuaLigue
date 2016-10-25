package ca.ulaval.glo2004.visualigue.services.play.commands;

import ca.ulaval.glo2004.visualigue.domain.play.Play;
import ca.ulaval.glo2004.visualigue.domain.play.PlayRepository;
import ca.ulaval.glo2004.visualigue.domain.play.actorinstance.BallInstance;
import ca.ulaval.glo2004.visualigue.domain.play.actorinstance.PlayerInstance;
import ca.ulaval.glo2004.visualigue.domain.play.actorstate.BallState;
import ca.ulaval.glo2004.visualigue.utils.geometry.Vector2;
import java.util.Optional;
import java.util.UUID;
import javax.inject.Inject;

public class BallCreationCommand implements Command {

    private Play play;
    private Integer time;
    private UUID ownerPlayerInstanceUUID;
    private Vector2 position;
    @Inject private PlayRepository playRepository;

    private BallInstance ballInstance;

    public BallCreationCommand(Play play, Integer time, UUID ownerPlayerInstanceUUID, Vector2 position) {
        this.play = play;
        this.time = time;
        this.ownerPlayerInstanceUUID = ownerPlayerInstanceUUID;
        this.position = position;
    }

    @Override
    public void execute() {
        PlayerInstance playerInstance = (PlayerInstance) play.getActorInstance(ownerPlayerInstanceUUID);
        BallState ballState = new BallState(Optional.of(position), Optional.of(playerInstance));
        ballInstance = new BallInstance();
        play.mergeKeyframe(time, ballInstance, ballState);
    }

    @Override
    public void revert() {
        play.unmergeKeyframe(time, ballInstance, Optional.empty());
    }

}
