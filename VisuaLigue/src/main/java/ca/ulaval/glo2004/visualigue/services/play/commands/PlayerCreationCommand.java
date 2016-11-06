package ca.ulaval.glo2004.visualigue.services.play.commands;

import ca.ulaval.glo2004.visualigue.domain.play.Play;
import ca.ulaval.glo2004.visualigue.domain.play.actorinstance.PlayerInstance;
import ca.ulaval.glo2004.visualigue.domain.play.actorinstance.TeamSide;
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

    private PlayerInstance playerInstance;

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
        playerInstance = new PlayerInstance(playerCategory, teamSide);
        play.mergeKeyframe(time, playerInstance, playerState);
        onFrameChanged.fire(this, play);
    }

    @Override
    public void revert() {
        play.unmergeKeyframe(time, playerInstance, null);
        onFrameChanged.fire(this, play);
    }

}
