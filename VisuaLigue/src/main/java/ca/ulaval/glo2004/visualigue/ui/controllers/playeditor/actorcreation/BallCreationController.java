package ca.ulaval.glo2004.visualigue.ui.controllers.playeditor.actorcreation;

import ca.ulaval.glo2004.visualigue.ui.controllers.playeditor.scene.scene2d.ActorCreationController;
import ca.ulaval.glo2004.visualigue.ui.converters.BallActorModelConverter;
import ca.ulaval.glo2004.visualigue.ui.models.BallModel;
import ca.ulaval.glo2004.visualigue.utils.geometry.Vector2;
import javax.inject.Inject;

public class BallCreationController extends ActorCreationController {

    @Inject private BallActorModelConverter ballActorModelConverter;
    private BallModel ballModel;

    public void init(BallModel ballModel) {
        this.ballModel = ballModel;
        this.actorModel = ballActorModelConverter.convert(ballModel);
    }

    @Override
    public void onPlayingSurfaceMouseClicked(Vector2 sizeRelativePosition) {
        if (enabled) {
            playService.addBallInstance(playModel.getUUID(), 0, null, sizeRelativePosition);
            initCreationLayer(actorModel);
        }
    }

}
