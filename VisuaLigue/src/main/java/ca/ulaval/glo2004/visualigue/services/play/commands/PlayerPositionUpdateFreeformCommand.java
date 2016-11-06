package ca.ulaval.glo2004.visualigue.services.play.commands;

import ca.ulaval.glo2004.visualigue.domain.play.Play;
import ca.ulaval.glo2004.visualigue.domain.play.actorinstance.PlayerInstance;
import ca.ulaval.glo2004.visualigue.domain.play.actorstate.ActorState;
import ca.ulaval.glo2004.visualigue.domain.play.actorstate.PlayerState;
import ca.ulaval.glo2004.visualigue.domain.play.actorstate.transition.FreeformStateTransition;
import ca.ulaval.glo2004.visualigue.utils.EventHandler;
import ca.ulaval.glo2004.visualigue.utils.geometry.Vector2;
import java.util.List;

public class PlayerPositionUpdateFreeformCommand extends Command {

    private String playerInstanceUUID;
    private List<Vector2> positions;
    private EventHandler<Play> onFrameChanged;

    private PlayerInstance playerInstance;
    private ActorState oldPlayerState;

    public PlayerPositionUpdateFreeformCommand(Play play, Integer time, String playerInstanceUUID, List<Vector2> positions, EventHandler<Play> onFrameChanged) {
        super(play, time);
        this.playerInstanceUUID = playerInstanceUUID;
        this.positions = positions;
        this.onFrameChanged = onFrameChanged;
    }

    @Override
    public void execute() {
        PlayerState playerState = new PlayerState(positions.get(0), new FreeformStateTransition(positions), null, null);
        playerInstance = (PlayerInstance) play.getActorInstance(playerInstanceUUID);
        oldPlayerState = play.mergeKeyframe(time, playerInstance, playerState);
        onFrameChanged.fire(this, play);
    }

    @Override
    public void revert() {
        play.unmergeKeyframe(time, playerInstance, oldPlayerState);
        onFrameChanged.fire(this, play);
    }

}
