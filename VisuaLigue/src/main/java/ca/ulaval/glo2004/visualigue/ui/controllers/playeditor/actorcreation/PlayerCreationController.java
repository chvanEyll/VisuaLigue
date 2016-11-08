package ca.ulaval.glo2004.visualigue.ui.controllers.playeditor.actorcreation;

import ca.ulaval.glo2004.visualigue.domain.play.actor.TeamSide;
import ca.ulaval.glo2004.visualigue.ui.controllers.playeditor.scene.scene2d.ActorCreationController;
import ca.ulaval.glo2004.visualigue.ui.converters.PlayerActorModelConverter;
import ca.ulaval.glo2004.visualigue.ui.models.PlayerCategoryModel;
import ca.ulaval.glo2004.visualigue.utils.geometry.Vector2;
import javax.inject.Inject;

public class PlayerCreationController extends ActorCreationController {

    @Inject private PlayerActorModelConverter playerActorModelConverter;
    private PlayerCategoryModel playerCategoryModel;
    private TeamSide teamSide;

    public void init(PlayerCategoryModel playerCategoryModel, TeamSide teamSide) {
        this.playerCategoryModel = playerCategoryModel;
        this.teamSide = teamSide;
        this.actorModel = playerActorModelConverter.convert(playerCategoryModel, teamSide);
    }

    @Override
    public void onSceneMouseClicked(Vector2 sizeRelativePosition) {
        if (enabled) {
            playService.addPlayerActor(playModel.getUUID(), frameModel.time.get(), playerCategoryModel.getUUID(), teamSide, 0.0, sizeRelativePosition);
            initCreationLayer(actorModel);
        }
    }

}
