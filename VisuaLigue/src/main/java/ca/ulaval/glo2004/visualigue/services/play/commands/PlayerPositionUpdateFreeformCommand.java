package ca.ulaval.glo2004.visualigue.services.play.commands;

import ca.ulaval.glo2004.visualigue.domain.play.Play;
import ca.ulaval.glo2004.visualigue.domain.play.actor.PlayerActor;
import ca.ulaval.glo2004.visualigue.domain.play.actorstate.PlayerState;
import ca.ulaval.glo2004.visualigue.domain.play.keyframe.Keyframe;
import ca.ulaval.glo2004.visualigue.domain.play.keyframe.transition.FreeformKeyframeTransition;
import ca.ulaval.glo2004.visualigue.utils.geometry.Vector2;
import java.util.List;

public class PlayerPositionUpdateFreeformCommand extends Command {

    private String playerActorUUID;
    private List<Vector2> positions;

    private Keyframe oldPlayerPositionKeyframe;

    public PlayerPositionUpdateFreeformCommand(Play play, Long time, String playerActorUUID, List<Vector2> positions) {
        super(play, time);
        this.playerActorUUID = playerActorUUID;
        this.positions = positions;
    }

    @Override
    public Long execute() {
        PlayerActor playerActor = (PlayerActor) play.getActor(playerActorUUID);
        oldPlayerPositionKeyframe = play.merge(time, playerActor, PlayerState.getPositionProperty(), positions.get(0), new FreeformKeyframeTransition(positions));
        return time;
    }

    @Override
    public Long revert() {
        PlayerActor playerActor = (PlayerActor) play.getActor(playerActorUUID);
        play.unmerge(time, playerActor, PlayerState.getPositionProperty(), oldPlayerPositionKeyframe);
        return time;
    }

}
