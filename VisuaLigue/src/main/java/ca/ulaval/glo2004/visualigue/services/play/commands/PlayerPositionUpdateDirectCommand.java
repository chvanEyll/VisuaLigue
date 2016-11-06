package ca.ulaval.glo2004.visualigue.services.play.commands;

import ca.ulaval.glo2004.visualigue.domain.play.Play;
import ca.ulaval.glo2004.visualigue.domain.play.actorinstance.PlayerInstance;
import ca.ulaval.glo2004.visualigue.domain.play.actorstate.ActorState;
import ca.ulaval.glo2004.visualigue.domain.play.actorstate.PlayerState;
import ca.ulaval.glo2004.visualigue.domain.play.actorstate.transition.LinearStateTransition;
import ca.ulaval.glo2004.visualigue.utils.EventHandler;
import ca.ulaval.glo2004.visualigue.utils.geometry.Vector2;

public class PlayerPositionUpdateDirectCommand extends Command {

    private String playerInstanceUUID;
    private Vector2 position;
    private EventHandler<Play> onFrameChanged;

    private PlayerInstance playerInstance;
    private ActorState oldPlayerState;

    public PlayerPositionUpdateDirectCommand(Play play, Integer time, String playerInstanceUUID, Vector2 position, EventHandler<Play> onFrameChanged) {
        super(play, time);
        this.playerInstanceUUID = playerInstanceUUID;
        this.position = position;
    }

    @Override
    public void execute() {
        PlayerState playerState = new PlayerState(position, new LinearStateTransition(), null, null);
        playerInstance = (PlayerInstance) play.getActorInstance(playerInstanceUUID);
        oldPlayerState = play.mergeKeyframe(time, playerInstance, playerState);
        onFrameChanged.fire(this, play);
        this.onFrameChanged = onFrameChanged;
    }

    @Override
    public void revert() {
        play.unmergeKeyframe(time, playerInstance, oldPlayerState);
        onFrameChanged.fire(this, play);
    }

}
