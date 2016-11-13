package ca.ulaval.glo2004.visualigue.ui.controllers.playeditor.actorcreation;

import ca.ulaval.glo2004.visualigue.ui.controllers.playeditor.scene.scene2d.ActorCreationController;
import ca.ulaval.glo2004.visualigue.ui.converters.actor.ObstacleActorModelConverter;
import ca.ulaval.glo2004.visualigue.ui.models.ObstacleModel;
import ca.ulaval.glo2004.visualigue.utils.geometry.Vector2;
import javafx.scene.input.MouseEvent;
import javax.inject.Inject;

public class ObstacleCreationController extends ActorCreationController {

    @Inject private ObstacleActorModelConverter obstacleActorModelConverter;
    private ObstacleModel obstacleModel;

    public void init(ObstacleModel obstacleModel) {
        this.obstacleModel = obstacleModel;
        this.actorModel = obstacleActorModelConverter.convert(obstacleModel);
    }

    @Override
    public void onSceneMouseClicked(MouseEvent e) {
        if (enabled) {
            Vector2 worldMousePosition = sceneController.getMouseWorldPosition(true);
            playService.beginUpdate(sceneController.getPlayUUID());
            playService.addObstacleActor(sceneController.getPlayUUID(), sceneController.getTime(), obstacleModel.getUUID(), worldMousePosition);
            playService.endUpdate(sceneController.getPlayUUID());
            initCreationLayer(actorModel);
        }
    }

}
