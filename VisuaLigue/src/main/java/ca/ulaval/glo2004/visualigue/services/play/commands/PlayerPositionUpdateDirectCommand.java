package ca.ulaval.glo2004.visualigue.services.play.commands;

import ca.ulaval.glo2004.visualigue.domain.play.Play;
import ca.ulaval.glo2004.visualigue.domain.play.actor.PlayerActor;
import ca.ulaval.glo2004.visualigue.domain.play.actorstate.ActorState;
import ca.ulaval.glo2004.visualigue.domain.play.actorstate.PlayerState;
import ca.ulaval.glo2004.visualigue.domain.play.actorstate.transition.LinearStateTransition;
import ca.ulaval.glo2004.visualigue.utils.EventHandler;
import ca.ulaval.glo2004.visualigue.utils.geometry.Vector2;

public class PlayerPositionUpdateDirectCommand extends Command {

    private String playerActorUUID;
    private Vector2 position;
    private EventHandler<Play> onFrameChanged;

    private PlayerActor playerActor;
    private ActorState oldPlayerState;

    public PlayerPositionUpdateDirectCommand(Play play, Integer time, String playerActorUUID, Vector2 position, EventHandler<Play> onFrameChanged) {
        super(play, time);
        this.playerActorUUID = playerActorUUID;
        this.position = position;
    }

    @Override
    public void execute() {
        PlayerState playerState = new PlayerState(position, new LinearStateTransition(), null, null);
        playerActor = (PlayerActor) play.getActor(playerActorUUID);
        oldPlayerState = play.mergeKeyframe(time, playerActor, playerState);
        onFrameChanged.fire(this, play);
        this.onFrameChanged = onFrameChanged;
    }

    @Override
    public void revert() {
        play.unmergeKeyframe(time, playerActor, oldPlayerState);
        onFrameChanged.fire(this, play);
    }

}
