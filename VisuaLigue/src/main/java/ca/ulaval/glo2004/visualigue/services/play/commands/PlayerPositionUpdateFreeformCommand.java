package ca.ulaval.glo2004.visualigue.services.play.commands;

import ca.ulaval.glo2004.visualigue.domain.play.Play;
import ca.ulaval.glo2004.visualigue.domain.play.actor.PlayerActor;
import ca.ulaval.glo2004.visualigue.domain.play.actorstate.ActorState;
import ca.ulaval.glo2004.visualigue.domain.play.actorstate.PlayerState;
import ca.ulaval.glo2004.visualigue.domain.play.actorstate.transition.FreeformStateTransition;
import ca.ulaval.glo2004.visualigue.utils.EventHandler;
import ca.ulaval.glo2004.visualigue.utils.geometry.Vector2;
import java.util.List;

public class PlayerPositionUpdateFreeformCommand extends Command {

    private String playerActorUUID;
    private List<Vector2> positions;
    private EventHandler<Play> onFrameChanged;

    private PlayerActor createdPlayerActor;
    private ActorState oldPlayerState;

    public PlayerPositionUpdateFreeformCommand(Play play, Long time, String playerActorUUID, List<Vector2> positions, EventHandler<Play> onFrameChanged) {
        super(play, time);
        this.playerActorUUID = playerActorUUID;
        this.positions = positions;
        this.onFrameChanged = onFrameChanged;
    }

    @Override
    public void execute() {
        PlayerState playerState = new PlayerState(positions.get(0), new FreeformStateTransition(positions), null, null);
        createdPlayerActor = (PlayerActor) play.getActor(playerActorUUID);
        oldPlayerState = play.mergeKeyframe(time, createdPlayerActor, playerState);
        onFrameChanged.fire(this, play);
    }

    @Override
    public void revert() {
        play.unmergeKeyframe(time, createdPlayerActor, oldPlayerState);
        onFrameChanged.fire(this, play);
    }

}
