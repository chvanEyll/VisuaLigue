package ca.ulaval.glo2004.visualigue.ui.controllers.playeditor.scene.scene2d.actorcreation;

import ca.ulaval.glo2004.visualigue.domain.play.actorinstance.TeamSide;
import ca.ulaval.glo2004.visualigue.services.play.PlayService;
import ca.ulaval.glo2004.visualigue.ui.controllers.playeditor.scene.scene2d.LayerController;
import ca.ulaval.glo2004.visualigue.ui.controllers.playeditor.scene.scene2d.layers.PlayingSurfaceLayerController;
import ca.ulaval.glo2004.visualigue.ui.converters.ActorModelConverter;
import ca.ulaval.glo2004.visualigue.ui.models.PlayModel;
import ca.ulaval.glo2004.visualigue.ui.models.PlayerCategoryModel;
import ca.ulaval.glo2004.visualigue.utils.geometry.Vector2;
import javafx.scene.input.MouseEvent;

public class PlayerCreationController extends ActorCreationController {

    private PlayerCategoryModel playerCategoryModel;
    private TeamSide teamSide;

    public PlayerCreationController(PlayingSurfaceLayerController playingSurfaceLayerController, LayerController layerController, ActorModelConverter actorModelConverter, PlayModel playModel, PlayService playService) {
        super(playingSurfaceLayerController, layerController, actorModelConverter, playModel, playService);
    }

    public void enterCreationMode(PlayerCategoryModel playerCategoryModel, TeamSide teamSide) {
        if (!enabled) {
            this.playerCategoryModel = playerCategoryModel;
            this.teamSide = teamSide;
            actorModel = actorModelConverter.convertPlayer(playerCategoryModel, teamSide);
            super.enterCreationMode();
        }
    }

    @Override
    protected void onPlayingSurfaceMouseClicked(Object sender, MouseEvent e) {
        if (enabled) {
            Vector2 position = playingSurfaceLayerController.getRelativeMousePosition();
            playService.addPlayer(playModel.getUUID(), 0, playerCategoryModel.getUUID(), teamSide, 0.0, position);
            initActorCreationLayer(actorModel);
        }
    }

}
