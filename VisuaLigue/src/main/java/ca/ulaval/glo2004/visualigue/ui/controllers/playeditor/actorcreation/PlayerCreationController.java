package ca.ulaval.glo2004.visualigue.ui.controllers.playeditor.actorcreation;

import ca.ulaval.glo2004.visualigue.ui.controllers.playeditor.scene.scene2d.ActorCreationController;
import ca.ulaval.glo2004.visualigue.domain.play.actorinstance.TeamSide;
import ca.ulaval.glo2004.visualigue.services.play.PlayService;
import ca.ulaval.glo2004.visualigue.ui.controllers.playeditor.scene.scene2d.LayerController;
import ca.ulaval.glo2004.visualigue.ui.converters.PlayerActorModelConverter;
import ca.ulaval.glo2004.visualigue.ui.models.PlayModel;
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

    public void enable(LayerController layerController, PlayerActorModelConverter playerActorModelConverter, PlayModel playModel, PlayService playService) {
        super.enable(layerController, playModel, playService);
    }

    @Override
    public void onPlayingSurfaceMouseClicked(Vector2 sizeRelativePosition) {
        if (enabled) {
            playService.addPlayerInstance(playModel.getUUID(), playerCategoryModel.getUUID(), teamSide, 0.0, sizeRelativePosition);
            initEditionLayer(actorModel);
        }
    }

}
