package ca.ulaval.glo2004.visualigue.ui.controllers.playeditor.scene.scene2d.layers;

import ca.ulaval.glo2004.visualigue.ui.InjectableFXMLLoader;
import ca.ulaval.glo2004.visualigue.ui.View;
import ca.ulaval.glo2004.visualigue.ui.models.ActorModel;

public class ActorLayerViewFactory {

    public View create(ActorModel actorModel) {
        switch (actorModel.type.get()) {
            case PLAYER:
                return InjectableFXMLLoader.loadView(PlayerLayerController.VIEW_NAME);
            case OBSTACLE:
                return InjectableFXMLLoader.loadView(ObstacleLayerController.VIEW_NAME);
            case BALL:
                return InjectableFXMLLoader.loadView(BallLayerController.VIEW_NAME);
        }
        throw new RuntimeException("Unsupported ActorModel subclass.");
    }

}
