package ca.ulaval.glo2004.visualigue.services.play.commands;

import ca.ulaval.glo2004.visualigue.domain.play.Play;
import ca.ulaval.glo2004.visualigue.domain.play.actorinstance.PlayerInstance;
import ca.ulaval.glo2004.visualigue.domain.play.actorinstance.TeamSide;
import ca.ulaval.glo2004.visualigue.domain.play.actorstate.PlayerState;
import ca.ulaval.glo2004.visualigue.domain.play.actorstate.transition.LinearTransition;
import ca.ulaval.glo2004.visualigue.domain.sport.playercategory.PlayerCategory;
import ca.ulaval.glo2004.visualigue.domain.sport.playercategory.PlayerCategoryRepository;
import ca.ulaval.glo2004.visualigue.utils.geometry.Vector2;
import javax.inject.Inject;

public class PlayerCreationCommand implements Command {

    private Play play;
    private Integer time;
    private String playerCategoryUUID;
    private TeamSide teamSide;
    private Double orientation;
    private Vector2 position;
    @Inject private PlayerCategoryRepository playerCategoryRepository;

    private PlayerInstance playerInstance;

    public PlayerCreationCommand(Play play, Integer time, String playerCategoryUUID, TeamSide teamSide, Double orientation, Vector2 position) {
        this.play = play;
        this.time = time;
        this.playerCategoryUUID = playerCategoryUUID;
        this.teamSide = teamSide;
        this.orientation = orientation;
        this.position = position;
    }

    @Override
    public void execute() {
        PlayerState playerState = new PlayerState(position, new LinearTransition(), orientation, new LinearTransition());
        PlayerCategory playerCategory = playerCategoryRepository.get(playerCategoryUUID);
        playerInstance = new PlayerInstance(playerCategory, teamSide);
        play.mergeKeyframe(time, playerInstance, playerState);
    }

    @Override
    public void revert() {
        play.unmergeKeyframe(time, playerInstance, null);
    }

}
