package ca.ulaval.glo2004.visualigue.services.play.commands;

import ca.ulaval.glo2004.visualigue.domain.play.Play;
import ca.ulaval.glo2004.visualigue.domain.play.actor.PlayerActor;
import ca.ulaval.glo2004.visualigue.domain.play.actorstate.PlayerState;
import ca.ulaval.glo2004.visualigue.domain.play.keyframe.transition.LinearKeyframeTransition;
import ca.ulaval.glo2004.visualigue.domain.play.keyframe.Keyframe;
import ca.ulaval.glo2004.visualigue.utils.EventHandler;

public class PlayerOrientationUpdateCommand extends Command {

    private String ownerPlayerActorUUID;
    private Double orientation;
    private EventHandler<Play> onFrameChanged;

    private PlayerActor createdPlayerActor;
    private Keyframe oldPlayerOrientationKeyframe;

    public PlayerOrientationUpdateCommand(Play play, Long time, String ownerPlayerActorUUID, Double orientation, EventHandler<Play> onFrameChanged) {
        super(play, time);
        this.ownerPlayerActorUUID = ownerPlayerActorUUID;
        this.orientation = orientation;
        this.onFrameChanged = onFrameChanged;
    }

    @Override
    public void execute() {
        createdPlayerActor = (PlayerActor) play.getActor(ownerPlayerActorUUID);
        oldPlayerOrientationKeyframe = play.merge(time, createdPlayerActor, PlayerState.getOrientationProperty(), orientation, new LinearKeyframeTransition());
        onFrameChanged.fire(this, play);
    }

    @Override
    public void revert() {
        play.unmerge(time, createdPlayerActor, PlayerState.getOrientationProperty(), oldPlayerOrientationKeyframe);
        onFrameChanged.fire(this, play);
    }
}
