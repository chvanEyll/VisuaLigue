package ca.ulaval.glo2004.visualigue.ui.controllers.playeditor.actorcreation;

import ca.ulaval.glo2004.visualigue.services.play.PlayService;
import ca.ulaval.glo2004.visualigue.ui.controllers.playeditor.scene.scene2d.ActorCreationController;
import ca.ulaval.glo2004.visualigue.ui.controllers.playeditor.scene.scene2d.LayerController;
import ca.ulaval.glo2004.visualigue.ui.converters.BallActorModelConverter;
import ca.ulaval.glo2004.visualigue.ui.models.BallModel;
import ca.ulaval.glo2004.visualigue.ui.models.PlayModel;
import ca.ulaval.glo2004.visualigue.utils.geometry.Vector2;
import javax.inject.Inject;

public class BallCreationController extends ActorCreationController {

    private BallModel ballModel;
    @Inject private BallActorModelConverter ballActorModelConverter;

    public void init(BallModel ballModel) {
        this.ballModel = ballModel;
        this.actorModel = ballActorModelConverter.convert(ballModel);
    }

    public void enable(LayerController layerController, BallActorModelConverter ballActorModelConverter, PlayModel playModel, PlayService playService) {
        super.enable(layerController, playModel, playService);
    }

    @Override
    public void onPlayingSurfaceMouseClicked(Vector2 sizeRelativePosition) {
        if (enabled) {
            playService.addBallInstance(playModel.getUUID(), 0, null, sizeRelativePosition);
            initEditionLayer(actorModel);
        }
    }

}
