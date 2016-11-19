package ca.ulaval.glo2004.visualigue.services.play.commands;

import ca.ulaval.glo2004.visualigue.domain.play.Play;
import ca.ulaval.glo2004.visualigue.domain.play.actor.BallActor;
import ca.ulaval.glo2004.visualigue.domain.play.actorstate.BallState;
import ca.ulaval.glo2004.visualigue.domain.play.keyframe.transition.LinearKeyframeTransition;
import ca.ulaval.glo2004.visualigue.utils.geometry.Vector2;

public class BallCreationCommand extends Command {

    private String ownerPlayerActorUUID;
    private Vector2 position;

    private BallActor ballActor;
    private String ballActorUUID;

    public BallCreationCommand(Play play, Long time, String ownerPlayerActorUUID, Vector2 position) {
        super(play, time);
        this.ownerPlayerActorUUID = ownerPlayerActorUUID;
        this.position = position;
    }

    @Override
    public Long execute() {
        ballActor = new BallActor();
        if (ballActorUUID != null) {
            ballActor.setUUID(ballActorUUID);
        } else {
            ballActorUUID = ballActor.getUUID();
        }
        play.merge(0L, ballActor, BallState.getPositionProperty(), position, new LinearKeyframeTransition());
        return time;
    }

    @Override
    public Long revert() {
        play.unmerge(0L, ballActor, BallState.getPositionProperty(), null);
        return time;
    }

}
