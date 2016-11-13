package ca.ulaval.glo2004.visualigue.services.play.commands;

import ca.ulaval.glo2004.visualigue.domain.play.Play;
import ca.ulaval.glo2004.visualigue.domain.play.actor.BallActor;
import ca.ulaval.glo2004.visualigue.domain.play.actor.PlayerActor;
import ca.ulaval.glo2004.visualigue.domain.play.actorstate.BallState;
import ca.ulaval.glo2004.visualigue.domain.play.actorstate.PlayerState;
import ca.ulaval.glo2004.visualigue.domain.play.keyframe.Keyframe;
import ca.ulaval.glo2004.visualigue.domain.play.keyframe.transition.LinearKeyframeTransition;

public class BallSnapCommand extends Command {

    private String ballActorUUID;
    private String playerActorUUID;

    private Keyframe oldSnappedBallKeyframe;
    private Keyframe oldPlayerOwnerKeyframe;

    public BallSnapCommand(Play play, Long time, String ballActorUUID, String playerActorUUID) {
        super(play, time);
        this.ballActorUUID = ballActorUUID;
        this.playerActorUUID = playerActorUUID;
    }

    @Override
    public Long execute() {
        BallActor ballActor = (BallActor) play.getActor(ballActorUUID);
        PlayerActor playerActor = (PlayerActor) play.getActor(playerActorUUID);
        oldPlayerOwnerKeyframe = play.merge(time, ballActor, BallState.getOwnerPlayerProperty(), playerActor, new LinearKeyframeTransition());
        oldSnappedBallKeyframe = play.merge(time, playerActor, PlayerState.getSnappedBallProperty(), ballActor, new LinearKeyframeTransition());
        return time;
    }

    @Override
    public Long revert() {
        BallActor ballActor = (BallActor) play.getActor(ballActorUUID);
        PlayerActor playerActor = (PlayerActor) play.getActor(playerActorUUID);
        play.unmerge(time, ballActor, BallState.getOwnerPlayerProperty(), oldPlayerOwnerKeyframe);
        play.unmerge(time, playerActor, PlayerState.getSnappedBallProperty(), oldSnappedBallKeyframe);
        return time;
    }

}
