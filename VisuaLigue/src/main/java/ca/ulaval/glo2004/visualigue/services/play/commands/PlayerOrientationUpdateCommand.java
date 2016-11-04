package ca.ulaval.glo2004.visualigue.services.play.commands;

import ca.ulaval.glo2004.visualigue.domain.play.Play;
import ca.ulaval.glo2004.visualigue.domain.play.actorinstance.PlayerInstance;
import ca.ulaval.glo2004.visualigue.domain.play.actorstate.ActorState;
import ca.ulaval.glo2004.visualigue.domain.play.actorstate.PlayerState;
import ca.ulaval.glo2004.visualigue.domain.play.actorstate.transition.LinearTransition;

public class PlayerOrientationUpdateCommand extends Command {

    private Integer time;
    private String ownerPlayerInstanceUUID;
    private Double orientation;

    private PlayerInstance playerInstance;
    private ActorState oldPlayerState;

    public PlayerOrientationUpdateCommand(Play play, Integer time, String ownerPlayerInstanceUUID, Double orientation) {
        super(play);
        this.time = time;
        this.ownerPlayerInstanceUUID = ownerPlayerInstanceUUID;
        this.orientation = orientation;
    }

    @Override
    public void execute() {
        PlayerState playerState = new PlayerState(null, null, orientation, new LinearTransition());
        playerInstance = (PlayerInstance) play.getActorInstance(ownerPlayerInstanceUUID);
        oldPlayerState = play.mergeKeyframe(time, playerInstance, playerState);
    }

    @Override
    public void revert() {
        play.unmergeKeyframe(time, playerInstance, oldPlayerState);
    }
}
