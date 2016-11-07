package ca.ulaval.glo2004.visualigue.ui.controllers.playeditor.scene.scene2d;

import ca.ulaval.glo2004.visualigue.services.play.PlayService;
import ca.ulaval.glo2004.visualigue.ui.controllers.ControllerBase;
import ca.ulaval.glo2004.visualigue.ui.models.PlayModel;
import ca.ulaval.glo2004.visualigue.ui.models.actors.ActorModel;
import ca.ulaval.glo2004.visualigue.utils.EventHandler;
import ca.ulaval.glo2004.visualigue.utils.geometry.Vector2;

public abstract class ActorCreationController extends ControllerBase {

    public EventHandler onEnabled = new EventHandler();
    public EventHandler onDisabled = new EventHandler();
    protected PlayService playService;
    protected LayerController layerController;
    protected ActorModel actorModel;
    protected PlayModel playModel;
    protected Boolean enabled = false;

    void enable(LayerController layerController, PlayModel playModel, PlayService playService) {
        this.layerController = layerController;
        this.playModel = playModel;
        this.playService = playService;
        initCreationLayer(actorModel);
        enabled = true;
        onEnabled.fire(this);
    }

    protected void initCreationLayer(ActorModel actorModel) {
        layerController.removeActorLayer(this.actorModel);
        layerController.addActorLayer(actorModel);
        layerController.setLayerOpacity(actorModel, 0.5);
        layerController.setLayerMouseTransparent(actorModel, true);
        this.actorModel = actorModel;
    }

    public void disable() {
        if (enabled) {
            layerController.removeActorLayer(actorModel);
            enabled = false;
            onDisabled.fire(this);
        }
    }

    public void onPlayingSurfaceMouseMoved(Vector2 sizeRelativePosition) {
        if (enabled) {
            actorModel.position.set(sizeRelativePosition);
        }
    }

    public abstract void onPlayingSurfaceMouseClicked(Vector2 sizeRelativePosition);

}
