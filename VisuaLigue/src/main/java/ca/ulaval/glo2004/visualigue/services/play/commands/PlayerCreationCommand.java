package ca.ulaval.glo2004.visualigue.services.play.commands;

import ca.ulaval.glo2004.visualigue.domain.play.Play;
import ca.ulaval.glo2004.visualigue.domain.play.actor.PlayerActor;
import ca.ulaval.glo2004.visualigue.domain.play.actor.TeamSide;
import ca.ulaval.glo2004.visualigue.domain.play.actorstate.PlayerState;
import ca.ulaval.glo2004.visualigue.domain.play.actorstate.transition.LinearStateTransition;
import ca.ulaval.glo2004.visualigue.domain.sport.playercategory.PlayerCategory;
import ca.ulaval.glo2004.visualigue.domain.sport.playercategory.PlayerCategoryRepository;
import ca.ulaval.glo2004.visualigue.utils.EventHandler;
import ca.ulaval.glo2004.visualigue.utils.geometry.Vector2;

public class PlayerCreationCommand extends Command {

    private String playerCategoryUUID;
    private TeamSide teamSide;
    private Double orientation;
    private Vector2 position;
    private EventHandler<Play> onFrameChanged;
    private PlayerCategoryRepository playerCategoryRepository;

    private PlayerActor playerActor;

    public PlayerCreationCommand(Play play, Integer time, String playerCategoryUUID, TeamSide teamSide, Double orientation, Vector2 position, PlayerCategoryRepository playerCategoryRepository, EventHandler<Play> onFrameChanged) {
        super(play, time);
        this.playerCategoryUUID = playerCategoryUUID;
        this.teamSide = teamSide;
        this.orientation = orientation;
        this.position = position;
        this.playerCategoryRepository = playerCategoryRepository;
        this.onFrameChanged = onFrameChanged;
    }

    @Override
    public void execute() {
        PlayerState playerState = new PlayerState(position, new LinearStateTransition(), orientation, new LinearStateTransition());
        PlayerCategory playerCategory = playerCategoryRepository.get(playerCategoryUUID);
        playerActor = new PlayerActor(playerCategory, teamSide);
        play.mergeKeyframe(time, playerActor, playerState);
        onFrameChanged.fire(this, play);
    }

    @Override
    public void revert() {
        play.unmergeKeyframe(time, playerActor, null);
        onFrameChanged.fire(this, play);
    }

}
