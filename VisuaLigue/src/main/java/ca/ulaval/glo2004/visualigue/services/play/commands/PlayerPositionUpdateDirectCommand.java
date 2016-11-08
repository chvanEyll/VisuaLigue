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

    private PlayerActor createdPlayerActor;
    private ActorState oldPlayerState;

    public PlayerPositionUpdateDirectCommand(Play play, Long time, String playerActorUUID, Vector2 position, EventHandler<Play> onFrameChanged) {
        super(play, time);
        this.playerActorUUID = playerActorUUID;
        this.position = position;
        this.onFrameChanged = onFrameChanged;
    }

    @Override
    public void execute() {
        PlayerState playerState = new PlayerState(position, new LinearStateTransition(), null, null);
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
