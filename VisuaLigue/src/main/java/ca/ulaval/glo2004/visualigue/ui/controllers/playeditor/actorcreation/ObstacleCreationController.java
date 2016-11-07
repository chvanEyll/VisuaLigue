package ca.ulaval.glo2004.visualigue.ui.controllers.playeditor.actorcreation;

import ca.ulaval.glo2004.visualigue.services.play.PlayService;
import ca.ulaval.glo2004.visualigue.ui.controllers.playeditor.scene.scene2d.ActorCreationController;
import ca.ulaval.glo2004.visualigue.ui.controllers.playeditor.scene.scene2d.LayerController;
import ca.ulaval.glo2004.visualigue.ui.converters.ObstacleActorModelConverter;
import ca.ulaval.glo2004.visualigue.ui.models.ObstacleModel;
import ca.ulaval.glo2004.visualigue.ui.models.PlayModel;
import ca.ulaval.glo2004.visualigue.utils.geometry.Vector2;
import javax.inject.Inject;

public class ObstacleCreationController extends ActorCreationController {

    @Inject private ObstacleActorModelConverter obstacleActorModelConverter;
    private ObstacleModel obstacleModel;

    public void init(ObstacleModel obstacleModel) {
        this.obstacleModel = obstacleModel;
        this.actorModel = obstacleActorModelConverter.convert(obstacleModel);
    }

    public void enable(LayerController layerController, ObstacleActorModelConverter obstacleActorModelConverter, PlayModel playModel, PlayService playService) {
        super.enable(layerController, playModel, playService);
    }

    @Override
    public void onPlayingSurfaceMouseClicked(Vector2 sizeRelativePosition) {
        if (enabled) {
            playService.addObstacleInstance(playModel.getUUID(), 0, obstacleModel.getUUID(), sizeRelativePosition);
            initEditionLayer(actorModel);
        }
    }

}
