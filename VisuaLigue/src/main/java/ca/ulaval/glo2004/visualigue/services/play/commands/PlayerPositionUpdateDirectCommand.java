package ca.ulaval.glo2004.visualigue.services.play.commands;

import ca.ulaval.glo2004.visualigue.domain.play.Play;
import ca.ulaval.glo2004.visualigue.domain.play.actor.PlayerActor;
import ca.ulaval.glo2004.visualigue.domain.play.actorstate.PlayerState;
import ca.ulaval.glo2004.visualigue.domain.play.keyframe.Keyframe;
import ca.ulaval.glo2004.visualigue.domain.play.keyframe.transition.LinearKeyframeTransition;
import ca.ulaval.glo2004.visualigue.utils.geometry.Vector2;

public class PlayerPositionUpdateDirectCommand extends Command {

    private String playerActorUUID;
    private Vector2 position;

    private Keyframe oldPositionKeyframe;
    private Keyframe oldPositionKeyframeAtLastKeyPoint;

    public PlayerPositionUpdateDirectCommand(Play play, Long time, String playerActorUUID, Vector2 position) {
        super(play, time);
        this.playerActorUUID = playerActorUUID;
        this.position = position;
    }

    @Override
    public Long execute() {
        PlayerActor playerActor = (PlayerActor) play.getActor(playerActorUUID);
        oldPositionKeyframe = play.merge(time, playerActor, PlayerState.getPositionProperty(), position, new LinearKeyframeTransition());
        if (play.previousKeyPointExists(time)) {
            Vector2 lastPosition = (Vector2) play.getActorLowerPropertyValue(time, playerActor, PlayerState.getPositionProperty());
            oldPositionKeyframeAtLastKeyPoint = play.merge(play.getPreviousKeyPointTime(time), playerActor, PlayerState.getPositionProperty(), lastPosition, new LinearKeyframeTransition());
        }
        return time;
    }

    @Override
    public Long revert() {
        PlayerActor playerActor = (PlayerActor) play.getActor(playerActorUUID);
        play.unmerge(time, playerActor, PlayerState.getPositionProperty(), oldPositionKeyframe);
        if (play.previousKeyPointExists(time)) {
            play.unmerge(play.getPreviousKeyPointTime(time), playerActor, PlayerState.getPositionProperty(), oldPositionKeyframeAtLastKeyPoint);
        }
        return time;
    }

}
