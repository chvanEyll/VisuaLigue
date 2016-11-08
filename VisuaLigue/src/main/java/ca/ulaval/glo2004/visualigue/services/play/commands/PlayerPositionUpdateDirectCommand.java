package ca.ulaval.glo2004.visualigue.services.play.commands;

import ca.ulaval.glo2004.visualigue.domain.play.Play;
import ca.ulaval.glo2004.visualigue.domain.play.actor.PlayerActor;
import ca.ulaval.glo2004.visualigue.domain.play.actorstate.PlayerState;
import ca.ulaval.glo2004.visualigue.domain.play.keyframe.transition.LinearKeyframeTransition;
import ca.ulaval.glo2004.visualigue.domain.play.keyframe.Keyframe;
import ca.ulaval.glo2004.visualigue.utils.EventHandler;
import ca.ulaval.glo2004.visualigue.utils.geometry.Vector2;

public class PlayerPositionUpdateDirectCommand extends Command {

    private String playerActorUUID;
    private Vector2 position;
    private EventHandler<Play> onFrameChanged;

    private PlayerActor createdPlayerActor;
    private Keyframe oldPlayerPositionKeyframe;

    public PlayerPositionUpdateDirectCommand(Play play, Long time, String playerActorUUID, Vector2 position, EventHandler<Play> onFrameChanged) {
        super(play, time);
        this.playerActorUUID = playerActorUUID;
        this.position = position;
        this.onFrameChanged = onFrameChanged;
    }

    @Override
    public void execute() {
        createdPlayerActor = (PlayerActor) play.getActor(playerActorUUID);
        oldPlayerPositionKeyframe = play.merge(time, createdPlayerActor, PlayerState.getPositionProperty(), position, new LinearKeyframeTransition());
        onFrameChanged.fire(this, play);
    }

    @Override
    public void revert() {
        play.unmerge(time, createdPlayerActor, PlayerState.getPositionProperty(), oldPlayerPositionKeyframe);
        onFrameChanged.fire(this, play);
    }

}
