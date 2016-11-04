package ca.ulaval.glo2004.visualigue.ui.controllers.playeditor.scene.scene2d.actorcreation;

import ca.ulaval.glo2004.visualigue.services.play.PlayService;
import ca.ulaval.glo2004.visualigue.ui.controllers.playeditor.scene.scene2d.LayerController;
import ca.ulaval.glo2004.visualigue.ui.controllers.playeditor.scene.scene2d.layers.PlayingSurfaceLayerController;
import ca.ulaval.glo2004.visualigue.ui.converters.ActorModelConverter;
import ca.ulaval.glo2004.visualigue.ui.models.ActorModel;
import ca.ulaval.glo2004.visualigue.ui.models.BallModel;
import ca.ulaval.glo2004.visualigue.ui.models.PlayModel;
import ca.ulaval.glo2004.visualigue.utils.geometry.Vector2;
import javafx.scene.input.MouseEvent;

public class BallCreationController extends ActorCreationController {

    public BallCreationController(PlayingSurfaceLayerController playingSurfaceLayerController, LayerController layerController, ActorModelConverter actorModelConverter, PlayModel playModel, PlayService playService) {
        super(playingSurfaceLayerController, layerController, actorModelConverter, playModel, playService);
    }

    public void enterCreationMode(BallModel ballModel) {
        ActorModel actorModel = actorModelConverter.convertBall(ballModel);
        super.enterCreationMode(actorModel);
    }

    @Override
    protected void onPlayingSurfaceMouseClicked(Object sender, MouseEvent e) {
        if (enabled) {
            Vector2 position = playingSurfaceLayerController.getRelativeMousePosition();
            playService.addBall(playModel.getUUID(), 0, null, position);
            initActorCreationLayer(actorModel);
        }
    }

}
