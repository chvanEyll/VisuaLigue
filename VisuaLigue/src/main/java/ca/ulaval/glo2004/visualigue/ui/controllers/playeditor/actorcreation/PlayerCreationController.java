package ca.ulaval.glo2004.visualigue.ui.controllers.playeditor.actorcreation;

import ca.ulaval.glo2004.visualigue.domain.play.actor.TeamSide;
import ca.ulaval.glo2004.visualigue.ui.controllers.playeditor.scene.scene2d.ActorCreationController;
import ca.ulaval.glo2004.visualigue.ui.converters.layers.PlayeLayerModelConverter;
import ca.ulaval.glo2004.visualigue.ui.models.PlayerCategoryModel;
import ca.ulaval.glo2004.visualigue.utils.geometry.Vector2;
import javafx.scene.input.MouseEvent;
import javax.inject.Inject;

public class PlayerCreationController extends ActorCreationController {

    @Inject private PlayeLayerModelConverter playerLayerModelConverter;
    private PlayerCategoryModel playerCategoryModel;
    private TeamSide teamSide;

    public void init(PlayerCategoryModel playerCategoryModel, TeamSide teamSide) {
        this.playerCategoryModel = playerCategoryModel;
        this.teamSide = teamSide;
        this.layerModel = playerLayerModelConverter.convert(playerCategoryModel, teamSide);
    }

    @Override
    public void onSceneMouseClicked(MouseEvent e) {
        if (enabled) {
            Vector2 sizeRelativePosition = playingSurfaceLayerController.getSizeRelativeMousePosition(true);
            playService.addPlayerActor(playModel.getUUID(), frameModel.time.get(), playerCategoryModel.getUUID(), teamSide, 0.0, sizeRelativePosition);
            initCreationLayer(layerModel);
        }
    }

}
