package ca.ulaval.glo2004.visualigue.ui.controllers.playeditor.scene.scene2d;

import ca.ulaval.glo2004.visualigue.services.play.PlayService;
import ca.ulaval.glo2004.visualigue.ui.controllers.ControllerBase;
import ca.ulaval.glo2004.visualigue.ui.models.FrameModel;
import ca.ulaval.glo2004.visualigue.ui.models.PlayModel;
import ca.ulaval.glo2004.visualigue.ui.models.actors.ActorModel;
import ca.ulaval.glo2004.visualigue.utils.EventHandler;
import ca.ulaval.glo2004.visualigue.utils.geometry.Vector2;
import javax.inject.Inject;

public abstract class ActorCreationController extends ControllerBase {

    public EventHandler onEnabled = new EventHandler();
    public EventHandler onDisabled = new EventHandler();
    @Inject protected PlayService playService;
    protected LayerController layerController;
    protected ActorModel actorModel;
    protected PlayModel playModel;
    protected FrameModel frameModel;
    protected Boolean enabled = false;

    void enable(LayerController layerController, PlayModel playModel, FrameModel frameModel) {
        this.layerController = layerController;
        this.playModel = playModel;
        this.frameModel = frameModel;
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

    public void onSceneMouseMoved(Vector2 sizeRelativePosition) {
        if (enabled) {
            actorModel.position.set(sizeRelativePosition);
        }
    }

    public abstract void onSceneMouseClicked(Vector2 sizeRelativePosition);

}
