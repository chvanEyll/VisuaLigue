package ca.ulaval.glo2004.visualigue.services.play.commands;

import ca.ulaval.glo2004.visualigue.domain.play.Play;
import ca.ulaval.glo2004.visualigue.domain.play.actor.PlayerActor;
import ca.ulaval.glo2004.visualigue.domain.play.actorstate.PlayerState;
import ca.ulaval.glo2004.visualigue.domain.play.keyframe.transition.FreeformKeyframeTransition;
import ca.ulaval.glo2004.visualigue.domain.play.keyframe.Keyframe;
import ca.ulaval.glo2004.visualigue.utils.EventHandler;
import ca.ulaval.glo2004.visualigue.utils.geometry.Vector2;
import java.util.List;

public class PlayerPositionUpdateFreeformCommand extends Command {

    private String playerActorUUID;
    private List<Vector2> positions;
    private EventHandler<Play> onFrameChanged;

    private PlayerActor createdPlayerActor;
    private Keyframe oldPlayerPositionKeyframe;

    public PlayerPositionUpdateFreeformCommand(Play play, Long time, String playerActorUUID, List<Vector2> positions, EventHandler<Play> onFrameChanged) {
        super(play, time);
        this.playerActorUUID = playerActorUUID;
        this.positions = positions;
        this.onFrameChanged = onFrameChanged;
    }

    @Override
    public void execute() {
        createdPlayerActor = (PlayerActor) play.getActor(playerActorUUID);
        oldPlayerPositionKeyframe = play.merge(time, createdPlayerActor, PlayerState.getPositionProperty(), positions.get(0), new FreeformKeyframeTransition(positions));
        onFrameChanged.fire(this, play);
    }

    @Override
    public void revert() {
        play.unmerge(time, createdPlayerActor, PlayerState.getPositionProperty(), oldPlayerPositionKeyframe);
        onFrameChanged.fire(this, play);
    }

}
