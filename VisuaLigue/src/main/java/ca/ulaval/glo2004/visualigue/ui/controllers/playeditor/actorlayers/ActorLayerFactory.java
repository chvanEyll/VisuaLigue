package ca.ulaval.glo2004.visualigue.ui.controllers.playeditor.actorlayers;

import ca.ulaval.glo2004.visualigue.ui.InjectableFXMLLoader;
import ca.ulaval.glo2004.visualigue.ui.View;
import ca.ulaval.glo2004.visualigue.ui.models.layers.ActorLayerModel;
import ca.ulaval.glo2004.visualigue.ui.models.layers.BallLayerModel;
import ca.ulaval.glo2004.visualigue.ui.models.layers.ObstacleLayerModel;
import ca.ulaval.glo2004.visualigue.ui.models.layers.PlayerLayerModel;

public class ActorLayerFactory {

    public View create(ActorLayerModel layerModel) {
        if (layerModel instanceof PlayerLayerModel) {
            return InjectableFXMLLoader.loadView(PlayerLayerController.VIEW_NAME);
        } else if (layerModel instanceof ObstacleLayerModel) {
            return InjectableFXMLLoader.loadView(ObstacleLayerController.VIEW_NAME);
        } else if (layerModel instanceof BallLayerModel) {
            return InjectableFXMLLoader.loadView(BallLayerController.VIEW_NAME);
        } else {
            throw new RuntimeException("Unsupported LayerModel subclass.");
        }
    }

}
