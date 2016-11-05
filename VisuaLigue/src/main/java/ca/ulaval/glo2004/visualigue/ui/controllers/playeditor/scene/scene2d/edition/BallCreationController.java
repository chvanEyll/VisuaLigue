package ca.ulaval.glo2004.visualigue.ui.controllers.playeditor.scene.scene2d.edition;

import ca.ulaval.glo2004.visualigue.services.play.PlayService;
import ca.ulaval.glo2004.visualigue.ui.controllers.playeditor.scene.scene2d.LayerController;
import ca.ulaval.glo2004.visualigue.ui.controllers.playeditor.scene.scene2d.layers.PlayingSurfaceLayerController;
import ca.ulaval.glo2004.visualigue.ui.converters.BallActorModelConverter;
import ca.ulaval.glo2004.visualigue.ui.models.BallModel;
import ca.ulaval.glo2004.visualigue.ui.models.PlayModel;
import ca.ulaval.glo2004.visualigue.ui.models.actors.BallActorModel;
import ca.ulaval.glo2004.visualigue.utils.geometry.Vector2;
import javafx.scene.input.MouseEvent;

public class BallCreationController extends ActorCreationController {

    private BallActorModelConverter ballActorModelConverter;

    public BallCreationController(PlayingSurfaceLayerController playingSurfaceLayerController, LayerController layerController, BallActorModelConverter ballActorModelConverter, PlayModel playModel, PlayService playService) {
        super(playingSurfaceLayerController, layerController, playModel, playService);
        this.ballActorModelConverter = ballActorModelConverter;
    }

    public void activate(BallModel ballModel) {
        BallActorModel ballActorModel = ballActorModelConverter.convert(ballModel);
        super.activate(ballActorModel);
    }

    @Override
    protected void onPlayingSurfaceMouseClicked(Object sender, MouseEvent e) {
        if (enabled) {
            Vector2 position = playingSurfaceLayerController.getRelativeMousePosition();
            playService.addBall(playModel.getUUID(), 0, null, position);
            initEditionLayer(actorModel);
        }
    }

}
