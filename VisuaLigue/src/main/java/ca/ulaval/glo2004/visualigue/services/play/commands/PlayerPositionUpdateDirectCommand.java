package ca.ulaval.glo2004.visualigue.services.play.commands;

import ca.ulaval.glo2004.visualigue.domain.play.Play;
import ca.ulaval.glo2004.visualigue.domain.play.PlayRepository;
import ca.ulaval.glo2004.visualigue.domain.play.actorinstance.PlayerInstance;
import ca.ulaval.glo2004.visualigue.domain.play.actorstate.ActorState;
import ca.ulaval.glo2004.visualigue.domain.play.actorstate.PlayerState;
import ca.ulaval.glo2004.visualigue.domain.play.actorstate.transition.LinearTransition;
import ca.ulaval.glo2004.visualigue.utils.geometry.Vector2;
import javax.inject.Inject;

public class PlayerPositionUpdateDirectCommand implements Command {

    private Play play;
    private Integer time;
    private String ownerPlayerInstanceUUID;
    private Vector2 position;
    @Inject private PlayRepository playRepository;

    private PlayerInstance playerInstance;
    private ActorState oldPlayerState;

    public PlayerPositionUpdateDirectCommand(Play play, Integer time, String ownerPlayerInstanceUUID, Vector2 position) {
        this.play = play;
        this.time = time;
        this.ownerPlayerInstanceUUID = ownerPlayerInstanceUUID;
        this.position = position;
    }

    @Override
    public void execute() {
        PlayerState playerState = new PlayerState(position, new LinearTransition(), null, null);
        playerInstance = (PlayerInstance) play.getActorInstance(ownerPlayerInstanceUUID);
        oldPlayerState = play.mergeKeyframe(time, playerInstance, playerState);
    }

    @Override
    public void revert() {
        play.unmergeKeyframe(time, playerInstance, oldPlayerState);
    }

}
