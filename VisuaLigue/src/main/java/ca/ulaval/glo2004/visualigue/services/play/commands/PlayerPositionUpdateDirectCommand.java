package ca.ulaval.glo2004.visualigue.services.play.commands;

import ca.ulaval.glo2004.visualigue.domain.play.Play;
import ca.ulaval.glo2004.visualigue.domain.play.actorinstance.PlayerInstance;
import ca.ulaval.glo2004.visualigue.domain.play.actorstate.ActorState;
import ca.ulaval.glo2004.visualigue.domain.play.actorstate.PlayerState;
import ca.ulaval.glo2004.visualigue.domain.play.actorstate.transition.LinearTransition;
import ca.ulaval.glo2004.visualigue.utils.geometry.Vector2;

public class PlayerPositionUpdateDirectCommand extends Command {

    private String ownerPlayerInstanceUUID;
    private Vector2 position;

    private PlayerInstance playerInstance;
    private ActorState oldPlayerState;

    public PlayerPositionUpdateDirectCommand(Play play, Integer time, String ownerPlayerInstanceUUID, Vector2 position) {
        super(play, time);
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
