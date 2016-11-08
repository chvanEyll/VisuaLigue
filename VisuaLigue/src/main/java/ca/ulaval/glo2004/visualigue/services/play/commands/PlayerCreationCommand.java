package ca.ulaval.glo2004.visualigue.services.play.commands;

import ca.ulaval.glo2004.visualigue.domain.play.Play;
import ca.ulaval.glo2004.visualigue.domain.play.actor.PlayerActor;
import ca.ulaval.glo2004.visualigue.domain.play.actor.TeamSide;
import ca.ulaval.glo2004.visualigue.domain.play.actorstate.PlayerState;
import ca.ulaval.glo2004.visualigue.domain.play.keyframe.transition.LinearKeyframeTransition;
import ca.ulaval.glo2004.visualigue.domain.sport.playercategory.PlayerCategory;
import ca.ulaval.glo2004.visualigue.domain.sport.playercategory.PlayerCategoryRepository;
import ca.ulaval.glo2004.visualigue.utils.EventHandler;
import ca.ulaval.glo2004.visualigue.utils.geometry.Vector2;

public class PlayerCreationCommand extends Command {

    private String playerCategoryUUID;
    private TeamSide teamSide;
    private Double orientation;
    private Vector2 position;
    private PlayerCategoryRepository playerCategoryRepository;
    private EventHandler<Play> onFrameChanged;

    private PlayerActor createdPlayerActor;
    private String createdPlayerActorUUID;

    public PlayerCreationCommand(Play play, Long time, String playerCategoryUUID, TeamSide teamSide, Double orientation, Vector2 position, PlayerCategoryRepository playerCategoryRepository, EventHandler<Play> onFrameChanged) {
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
        PlayerCategory playerCategory = playerCategoryRepository.get(playerCategoryUUID);
        createdPlayerActor = new PlayerActor(playerCategory, teamSide);
        if (createdPlayerActorUUID != null) {
            createdPlayerActor.setUUID(createdPlayerActorUUID);
        } else {
            createdPlayerActorUUID = createdPlayerActor.getUUID();
        }
        play.merge(time, createdPlayerActor, PlayerState.getPositionProperty(), position, new LinearKeyframeTransition());
        play.merge(time, createdPlayerActor, PlayerState.getOrientationProperty(), orientation, new LinearKeyframeTransition());
        onFrameChanged.fire(this, play);
    }

    @Override
    public void revert() {
        play.unmerge(time, createdPlayerActor, PlayerState.getOrientationProperty(), null);
        play.unmerge(time, createdPlayerActor, PlayerState.getPositionProperty(), null);
        onFrameChanged.fire(this, play);
    }

}
