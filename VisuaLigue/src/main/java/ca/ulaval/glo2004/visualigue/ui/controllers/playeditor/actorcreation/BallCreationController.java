package ca.ulaval.glo2004.visualigue.ui.controllers.playeditor.actorcreation;

import ca.ulaval.glo2004.visualigue.ui.controllers.playeditor.scene.scene2d.ActorCreationController;
import ca.ulaval.glo2004.visualigue.ui.converters.layers.BallLayerModelConverter;
import ca.ulaval.glo2004.visualigue.ui.models.BallModel;
import ca.ulaval.glo2004.visualigue.utils.geometry.Vector2;
import javafx.scene.input.MouseEvent;
import javax.inject.Inject;

public class BallCreationController extends ActorCreationController {

    @Inject private BallLayerModelConverter ballLayerModelConverter;
    private BallModel ballModel;

    public void init(BallModel ballModel) {
        this.ballModel = ballModel;
        this.layerModel = ballLayerModelConverter.convert(ballModel);
    }

    @Override
    public void onSceneMouseClicked(MouseEvent e) {
        if (enabled) {
            Vector2 sizeRelativePosition = playingSurfaceLayerController.getSizeRelativeMousePosition(true);
            playService.addBallActor(playModel.getUUID(), frameModel.time.get(), null, sizeRelativePosition);
            initCreationLayer(layerModel);
        }
    }

}
