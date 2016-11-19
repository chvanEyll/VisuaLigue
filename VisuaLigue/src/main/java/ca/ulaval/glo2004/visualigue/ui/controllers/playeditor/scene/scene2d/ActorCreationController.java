package ca.ulaval.glo2004.visualigue.ui.controllers.playeditor.scene.scene2d;

import ca.ulaval.glo2004.visualigue.services.play.PlayService;
import ca.ulaval.glo2004.visualigue.ui.controllers.ControllerBase;
import ca.ulaval.glo2004.visualigue.ui.controllers.playeditor.scene.SceneController;
import ca.ulaval.glo2004.visualigue.ui.models.actors.ActorModel;
import ca.ulaval.glo2004.visualigue.utils.EventHandler;
import ca.ulaval.glo2004.visualigue.utils.geometry.Vector2;
import javafx.scene.input.MouseEvent;
import javax.inject.Inject;

public abstract class ActorCreationController extends ControllerBase {

    public EventHandler onEnabled = new EventHandler();
    public EventHandler onDisabled = new EventHandler();
    @Inject protected PlayService playService;
    protected SceneController sceneController;
    protected ActorModel actorModel;
    protected Boolean enabled = false;

    void enable(SceneController sceneController) {
        this.sceneController = sceneController;
        initCreationLayer(actorModel);
        enabled = true;
        onEnabled.fire(this);
    }

    protected void initCreationLayer(ActorModel actorModel) {
        sceneController.removeActor(this.actorModel);
        actorModel.position.set(sceneController.getMouseWorldPosition(false));
        actorModel.opacity.set(0.5);
        actorModel.isLocked.set(true);
        sceneController.addActor(actorModel);
        this.actorModel = actorModel;
    }

    public void disable() {
        if (enabled) {
            sceneController.removeActor(actorModel);
            enabled = false;
            onDisabled.fire(this);
        }
    }

    public void onSceneMouseEntered(MouseEvent e) {
        actorModel.visible.set(true);
    }

    public void onSceneMouseExited(MouseEvent e) {
        actorModel.visible.set(false);
    }

    public void onSceneMouseMoved(MouseEvent e) {
        if (enabled) {
            Vector2 worldMousePosition = sceneController.getMouseWorldPosition(true);
            actorModel.position.set(worldMousePosition);
        }
    }

    public abstract void onSceneMouseClicked(MouseEvent e);

}
