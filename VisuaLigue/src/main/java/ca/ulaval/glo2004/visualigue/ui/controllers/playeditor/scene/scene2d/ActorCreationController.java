package ca.ulaval.glo2004.visualigue.ui.controllers.playeditor.scene.scene2d;

import ca.ulaval.glo2004.visualigue.services.play.PlayService;
import ca.ulaval.glo2004.visualigue.ui.controllers.ControllerBase;
import ca.ulaval.glo2004.visualigue.ui.models.FrameModel;
import ca.ulaval.glo2004.visualigue.ui.models.PlayModel;
import ca.ulaval.glo2004.visualigue.ui.models.layers.ActorLayerModel;
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
    protected ActorLayerModel layerModel;
    protected PlayModel playModel;
    protected FrameModel frameModel;
    protected Boolean enabled = false;

    void enable(LayerController layerController, PlayingSurfaceLayerController playingSurfaceLayerController, PlayModel playModel, FrameModel frameModel) {
        this.layerController = layerController;
        this.playingSurfaceLayerController = playingSurfaceLayerController;
        this.playModel = playModel;
        this.frameModel = frameModel;
        initCreationLayer(layerModel);
        enabled = true;
        onEnabled.fire(this);
    }

    protected void initCreationLayer(ActorLayerModel layerModel) {
        layerController.removeActorLayer(this.layerModel);
        layerController.addActorLayer(layerModel);
        layerController.setLayerOpacity(layerModel, 0.5);
        layerController.setLayerMouseTransparent(layerModel, true);
        this.layerModel = layerModel;
    }

    public void disable() {
        if (enabled) {
            layerController.removeActorLayer(layerModel);
            enabled = false;
            onDisabled.fire(this);
        }
    }

    public void onSceneMouseEntered(MouseEvent e) {
        layerModel.visible.set(true);
    }

    public void onSceneMouseExited(MouseEvent e) {
        layerModel.visible.set(false);
    }

    public void onSceneMouseMoved(MouseEvent e) {
        if (enabled) {
            Vector2 sizeRelativePosition = playingSurfaceLayerController.getSizeRelativeMousePosition(true);
            layerModel.position.set(sizeRelativePosition);
        }
    }

    public abstract void onSceneMouseClicked(MouseEvent e);

}
