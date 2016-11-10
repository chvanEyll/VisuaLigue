package ca.ulaval.glo2004.visualigue.ui.controllers.playeditor.actorcreation;

import ca.ulaval.glo2004.visualigue.ui.controllers.playeditor.scene.scene2d.ActorCreationController;
import ca.ulaval.glo2004.visualigue.ui.converters.layers.ObstacleLayerModelConverter;
import ca.ulaval.glo2004.visualigue.ui.models.ObstacleModel;
import ca.ulaval.glo2004.visualigue.utils.geometry.Vector2;
import javafx.scene.input.MouseEvent;
import javax.inject.Inject;

public class ObstacleCreationController extends ActorCreationController {

    @Inject private ObstacleLayerModelConverter obstacleLayerModelConverter;
    private ObstacleModel obstacleModel;

    public void init(ObstacleModel obstacleModel) {
        this.obstacleModel = obstacleModel;
        this.layerModel = obstacleLayerModelConverter.convert(obstacleModel);
    }

    @Override
    public void onSceneMouseClicked(MouseEvent e) {
        if (enabled) {
            Vector2 sizeRelativePosition = playingSurfaceLayerController.getSizeRelativeMousePosition(true);
            playService.addObstacleActor(playModel.getUUID(), frameModel.time.get(), obstacleModel.getUUID(), sizeRelativePosition);
            initCreationLayer(layerModel);
        }
    }

}
