package ca.ulaval.glo2004.visualigue.ui.controllers.playeditor.scene.scene2d.actorcreation;

import ca.ulaval.glo2004.visualigue.services.play.PlayService;
import ca.ulaval.glo2004.visualigue.ui.controllers.playeditor.scene.scene2d.LayerController;
import ca.ulaval.glo2004.visualigue.ui.controllers.playeditor.scene.scene2d.layers.PlayingSurfaceLayerController;
import ca.ulaval.glo2004.visualigue.ui.converters.ObstacleActorModelConverter;
import ca.ulaval.glo2004.visualigue.ui.models.actors.ObstacleActorModel;
import ca.ulaval.glo2004.visualigue.ui.models.ObstacleModel;
import ca.ulaval.glo2004.visualigue.ui.models.PlayModel;
import ca.ulaval.glo2004.visualigue.utils.geometry.Vector2;
import javafx.scene.input.MouseEvent;

public class ObstacleCreationController extends ActorCreationController {

    private ObstacleActorModelConverter obstacleActorModelConverter;
    private ObstacleModel obstacleModel;

    public ObstacleCreationController(PlayingSurfaceLayerController playingSurfaceLayerController, LayerController layerController, ObstacleActorModelConverter obstacleActorModelConverter, PlayModel playModel, PlayService playService) {
        super(playingSurfaceLayerController, layerController, playModel, playService);
        this.obstacleActorModelConverter = obstacleActorModelConverter;
    }

    public void enterCreationMode(ObstacleModel obstacleModel) {
        this.obstacleModel = obstacleModel;
        ObstacleActorModel obstacleActorModel = obstacleActorModelConverter.convert(obstacleModel);
        super.enterCreationMode(obstacleActorModel);
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
