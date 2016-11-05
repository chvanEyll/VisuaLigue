package ca.ulaval.glo2004.visualigue.ui.controllers.playeditor.scene.scene2d.edition;

import ca.ulaval.glo2004.visualigue.domain.play.actorinstance.TeamSide;
import ca.ulaval.glo2004.visualigue.services.play.PlayService;
import ca.ulaval.glo2004.visualigue.ui.controllers.playeditor.scene.scene2d.LayerController;
import ca.ulaval.glo2004.visualigue.ui.controllers.playeditor.scene.scene2d.layers.PlayingSurfaceLayerController;
import ca.ulaval.glo2004.visualigue.ui.converters.PlayerActorModelConverter;
import ca.ulaval.glo2004.visualigue.ui.models.PlayModel;
import ca.ulaval.glo2004.visualigue.ui.models.PlayerCategoryModel;
import ca.ulaval.glo2004.visualigue.ui.models.actors.PlayerActorModel;
import ca.ulaval.glo2004.visualigue.utils.geometry.Vector2;
import javafx.scene.input.MouseEvent;

public class PlayerCreationController extends ActorCreationController {

    private PlayerActorModelConverter playerActorModelConverter;
    private PlayerCategoryModel playerCategoryModel;
    private TeamSide teamSide;

    public PlayerCreationController(PlayingSurfaceLayerController playingSurfaceLayerController, LayerController layerController, PlayerActorModelConverter playerActorModelConverter, PlayModel playModel, PlayService playService) {
        super(playingSurfaceLayerController, layerController, playModel, playService);
        this.playerActorModelConverter = playerActorModelConverter;
    }

    public void activate(PlayerCategoryModel playerCategoryModel, TeamSide teamSide) {
        this.playerCategoryModel = playerCategoryModel;
        this.teamSide = teamSide;
        PlayerActorModel playerActorModel = playerActorModelConverter.convert(playerCategoryModel, teamSide);
        super.activate(playerActorModel);
    }

    @Override
    protected void onPlayingSurfaceMouseClicked(Object sender, MouseEvent e) {
        if (enabled) {
            Vector2 position = playingSurfaceLayerController.getRelativeMousePosition();
            playService.addPlayer(playModel.getUUID(), 0, playerCategoryModel.getUUID(), teamSide, 0.0, position);
            initEditionLayer(actorModel);
        }
    }

}
