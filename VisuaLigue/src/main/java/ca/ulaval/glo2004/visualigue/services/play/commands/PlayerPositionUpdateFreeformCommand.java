package ca.ulaval.glo2004.visualigue.services.play.commands;

import ca.ulaval.glo2004.visualigue.domain.play.Play;
import ca.ulaval.glo2004.visualigue.domain.play.actorinstance.PlayerInstance;
import ca.ulaval.glo2004.visualigue.domain.play.actorstate.ActorState;
import ca.ulaval.glo2004.visualigue.domain.play.actorstate.PlayerState;
import ca.ulaval.glo2004.visualigue.domain.play.actorstate.transition.StateTransition;
import ca.ulaval.glo2004.visualigue.utils.geometry.Vector2;

public class PlayerPositionUpdateFreeformCommand extends Command {

    private Integer time;
    private String ownerPlayerInstanceUUID;
    private Vector2 position;
    private StateTransition positionTransition;

    private PlayerInstance playerInstance;
    private ActorState oldPlayerState;

    public PlayerPositionUpdateFreeformCommand(Play play, Integer time, String ownerPlayerInstanceUUID, Vector2 position, StateTransition positionTransition) {
        super(play);
        this.time = time;
        this.ownerPlayerInstanceUUID = ownerPlayerInstanceUUID;
        this.position = position;
        this.positionTransition = positionTransition;
    }

    @Override
    public void execute() {
        PlayerState playerState = new PlayerState(position, positionTransition, null, null);
        playerInstance = (PlayerInstance) play.getActorInstance(ownerPlayerInstanceUUID);
        oldPlayerState = play.mergeKeyframe(time, playerInstance, playerState);
    }

    @Override
    public void revert() {
        play.unmergeKeyframe(time, playerInstance, oldPlayerState);
    }

}
