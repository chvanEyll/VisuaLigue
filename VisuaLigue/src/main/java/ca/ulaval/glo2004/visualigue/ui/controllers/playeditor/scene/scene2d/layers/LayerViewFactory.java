package ca.ulaval.glo2004.visualigue.ui.controllers.playeditor.scene.scene2d.layers;

import ca.ulaval.glo2004.visualigue.ui.InjectableFXMLLoader;
import ca.ulaval.glo2004.visualigue.ui.View;
import ca.ulaval.glo2004.visualigue.ui.models.ActorModel;

public class LayerViewFactory {

    public View create(ActorModel actorModel) {
        if (actorModel.type.get() == ActorModel.Type.PLAYER) {
            return InjectableFXMLLoader.loadView(PlayerLayerController.VIEW_NAME);
        }
        throw new RuntimeException("Unsupported ActorModel subclass.");
    }

}
