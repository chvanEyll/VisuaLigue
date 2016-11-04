package ca.ulaval.glo2004.visualigue.ui.controllers.playeditor.scene.scene2d.actorcreation;

import ca.ulaval.glo2004.visualigue.services.play.PlayService;
import ca.ulaval.glo2004.visualigue.ui.controllers.playeditor.scene.scene2d.LayerController;
import ca.ulaval.glo2004.visualigue.ui.controllers.playeditor.scene.scene2d.layers.PlayingSurfaceLayerController;
import ca.ulaval.glo2004.visualigue.ui.converters.ActorModelConverter;
import ca.ulaval.glo2004.visualigue.ui.models.ObstacleModel;
import ca.ulaval.glo2004.visualigue.ui.models.PlayModel;
import ca.ulaval.glo2004.visualigue.utils.geometry.Vector2;
import javafx.scene.input.MouseEvent;

public class ObstacleCreationController extends ActorCreationController {

    private ObstacleModel obstacleModel;

    public ObstacleCreationController(PlayingSurfaceLayerController playingSurfaceLayerController, LayerController layerController, ActorModelConverter actorModelConverter, PlayModel playModel, PlayService playService) {
        super(playingSurfaceLayerController, layerController, actorModelConverter, playModel, playService);
    }

    public void enterCreationMode(ObstacleModel obstacleModel) {
        if (!enabled) {
            this.obstacleModel = obstacleModel;
            actorModel = actorModelConverter.convertObstacle(obstacleModel);
            super.enterCreationMode();
        }
    }

    @Override
    protected void onPlayingSurfaceMouseClicked(Object sender, MouseEvent e) {
        if (enabled) {
            Vector2 position = playingSurfaceLayerController.getRelativeMousePosition();
            playService.addObstacle(playModel.getUUID(), 0, obstacleModel.getUUID(), position);
            initActorCreationLayer(actorModel);
        }
    }

}
