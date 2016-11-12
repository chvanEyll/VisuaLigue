package ca.ulaval.glo2004.visualigue.ui.controllers.playeditor.scene.scene2d;

import ca.ulaval.glo2004.visualigue.services.play.PlayService;
import ca.ulaval.glo2004.visualigue.ui.controllers.ControllerBase;
import ca.ulaval.glo2004.visualigue.ui.models.FrameModel;
import ca.ulaval.glo2004.visualigue.ui.models.PlayModel;
import ca.ulaval.glo2004.visualigue.ui.models.actors.ActorModel;
import ca.ulaval.glo2004.visualigue.utils.EventHandler;
import ca.ulaval.glo2004.visualigue.utils.geometry.Vector2;
import javafx.scene.input.MouseEvent;
import javax.inject.Inject;

public abstract class ActorCreationController extends ControllerBase {

    public EventHandler onEnabled = new EventHandler();
    public EventHandler onDisabled = new EventHandler();
    @Inject protected PlayService playService;
    protected LayerController layerController;
    protected PlayingSurfaceLayerController playingSurfaceLayerController;
    protected ActorModel actorModel;
    protected PlayModel playModel;
    protected FrameModel frameModel;
    protected Boolean enabled = false;

    void enable(LayerController layerController, PlayingSurfaceLayerController playingSurfaceLayerController, PlayModel playModel, FrameModel frameModel) {
        this.layerController = layerController;
        this.playingSurfaceLayerController = playingSurfaceLayerController;
        this.playModel = playModel;
        this.frameModel = frameModel;
        initCreationLayer(actorModel);
        enabled = true;
        onEnabled.fire(this);
    }

    protected void initCreationLayer(ActorModel actorModel) {
        layerController.removeActorModel(this.actorModel);
        layerController.addActorModel(actorModel);
        actorModel.opacity.set(0.5);
        actorModel.mouseTransparent.set(true);
        this.actorModel = actorModel;
    }

    public void disable() {
        if (enabled) {
            layerController.removeActorModel(actorModel);
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
            Vector2 sizeRelativePosition = playingSurfaceLayerController.getSizeRelativeMousePosition(true);
            actorModel.position.set(sizeRelativePosition);
        }
    }

    public abstract void onSceneMouseClicked(MouseEvent e);

}
