package ca.ulaval.glo2004.visualigue.services.play.commands;

import ca.ulaval.glo2004.visualigue.domain.play.Play;
import ca.ulaval.glo2004.visualigue.domain.play.actor.PlayerActor;
import ca.ulaval.glo2004.visualigue.domain.play.actor.TeamSide;
import ca.ulaval.glo2004.visualigue.domain.play.actorstate.PlayerState;
import ca.ulaval.glo2004.visualigue.domain.play.keyframe.transition.LinearKeyframeTransition;
import ca.ulaval.glo2004.visualigue.domain.sport.playercategory.PlayerCategory;
import ca.ulaval.glo2004.visualigue.domain.sport.playercategory.PlayerCategoryRepository;
import ca.ulaval.glo2004.visualigue.utils.geometry.Vector2;

public class PlayerCreationCommand extends Command {

    private String playerCategoryUUID;
    private TeamSide teamSide;
    private Double orientation;
    private Vector2 position;
    private PlayerCategoryRepository playerCategoryRepository;

    private PlayerActor createdPlayerActor;
    private String createdPlayerActorUUID;

    public PlayerCreationCommand(Play play, Long time, String playerCategoryUUID, TeamSide teamSide, Double orientation, Vector2 position, PlayerCategoryRepository playerCategoryRepository) {
        super(play, time);
        this.playerCategoryUUID = playerCategoryUUID;
        this.teamSide = teamSide;
        this.orientation = orientation;
        this.position = position;
        this.playerCategoryRepository = playerCategoryRepository;
    }

    @Override
    public Long execute() {
        PlayerCategory playerCategory = playerCategoryRepository.get(playerCategoryUUID);
        createdPlayerActor = new PlayerActor(playerCategory, teamSide);
        if (createdPlayerActorUUID != null) {
            createdPlayerActor.setUUID(createdPlayerActorUUID);
        } else {
            createdPlayerActorUUID = createdPlayerActor.getUUID();
        }
        play.merge(0L, createdPlayerActor, PlayerState.getPositionProperty(), position, new LinearKeyframeTransition());
        play.merge(0L, createdPlayerActor, PlayerState.getOrientationProperty(), orientation, new LinearKeyframeTransition());
        return time;
    }

    @Override
    public Long revert() {
        play.unmerge(0L, createdPlayerActor, PlayerState.getOrientationProperty(), null);
        play.unmerge(0L, createdPlayerActor, PlayerState.getPositionProperty(), null);
        return time;
    }

}
