package ca.ulaval.glo2004.visualigue.services.play.commands;

import ca.ulaval.glo2004.visualigue.domain.play.Play;
import ca.ulaval.glo2004.visualigue.domain.play.actor.PlayerActor;
import ca.ulaval.glo2004.visualigue.domain.play.actorstate.PlayerState;
import ca.ulaval.glo2004.visualigue.domain.play.keyframe.Keyframe;
import ca.ulaval.glo2004.visualigue.domain.play.keyframe.transition.LinearKeyframeTransition;
import ca.ulaval.glo2004.visualigue.utils.EventHandler;

public class PlayerOrientationUpdateCommand extends Command {

    private String ownerPlayerActorUUID;
    private Double orientation;
    private EventHandler<Play> onFrameChanged;

    private PlayerActor playerActor;
    private Keyframe oldOrientationKeyframe;
    private Keyframe oldOrientationKeyframeAtLastKeyPoint;

    public PlayerOrientationUpdateCommand(Play play, Long time, String ownerPlayerActorUUID, Double orientation) {
        super(play, time);
        this.ownerPlayerActorUUID = ownerPlayerActorUUID;
        this.orientation = orientation;
    }

    @Override
    public Long execute() {
        playerActor = (PlayerActor) play.getActor(ownerPlayerActorUUID);
        oldOrientationKeyframe = play.merge(time, playerActor, PlayerState.getOrientationProperty(), orientation, new LinearKeyframeTransition());
        if (play.previousKeyPointExists(time)) {
            Double lastOrientation = (Double) play.getActorLowerPropertyValue(time, playerActor, PlayerState.getOrientationProperty());
            oldOrientationKeyframeAtLastKeyPoint = play.merge(play.getPreviousKeyPointTime(time), playerActor, PlayerState.getOrientationProperty(), lastOrientation, new LinearKeyframeTransition());
        }
        return time;
    }

    @Override
    public Long revert() {
        play.unmerge(time, playerActor, PlayerState.getOrientationProperty(), oldOrientationKeyframe);
        if (play.previousKeyPointExists(time)) {
            play.unmerge(play.getPreviousKeyPointTime(time), playerActor, PlayerState.getOrientationProperty(), oldOrientationKeyframeAtLastKeyPoint);
        }
        return time;
    }
}
