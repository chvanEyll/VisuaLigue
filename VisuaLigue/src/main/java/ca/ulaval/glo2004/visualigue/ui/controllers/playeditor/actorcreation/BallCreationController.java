package ca.ulaval.glo2004.visualigue.ui.controllers.playeditor.actorcreation;

import ca.ulaval.glo2004.visualigue.ui.controllers.playeditor.scene.scene2d.ActorCreationController;
import ca.ulaval.glo2004.visualigue.ui.converters.actor.BallActorModelConverter;
import ca.ulaval.glo2004.visualigue.ui.models.BallModel;
import ca.ulaval.glo2004.visualigue.utils.geometry.Vector2;
import javafx.scene.input.MouseEvent;
import javax.inject.Inject;

public class BallCreationController extends ActorCreationController {

    @Inject private BallActorModelConverter ballActorModelConverter;
    private BallModel ballModel;

    public void init(BallModel ballModel) {
        this.ballModel = ballModel;
        this.actorModel = ballActorModelConverter.convert(ballModel);
    }

    @Override
    public void onSceneMouseClicked(MouseEvent e) {
        if (enabled) {
            Vector2 worldMousePosition = sceneController.getMouseWorldPosition(true);
            playService.beginUpdate(sceneController.getPlayUUID());
            playService.addBallActor(sceneController.getPlayUUID(), sceneController.getTime(), null, worldMousePosition);
            playService.endUpdate(sceneController.getPlayUUID());
            initCreationLayer(actorModel);
        }
    }

}
