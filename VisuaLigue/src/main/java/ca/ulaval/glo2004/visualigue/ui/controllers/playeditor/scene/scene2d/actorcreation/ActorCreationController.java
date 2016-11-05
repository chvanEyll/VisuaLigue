package ca.ulaval.glo2004.visualigue.ui.controllers.playeditor.scene.scene2d.actorcreation;

import ca.ulaval.glo2004.visualigue.services.play.PlayService;
import ca.ulaval.glo2004.visualigue.ui.controllers.ControllerBase;
import ca.ulaval.glo2004.visualigue.ui.controllers.playeditor.scene.scene2d.LayerController;
import ca.ulaval.glo2004.visualigue.ui.controllers.playeditor.scene.scene2d.layers.PlayingSurfaceLayerController;
import ca.ulaval.glo2004.visualigue.ui.models.PlayModel;
import ca.ulaval.glo2004.visualigue.ui.models.actors.ActorModel;
import ca.ulaval.glo2004.visualigue.utils.EventHandler;
import javafx.scene.input.MouseEvent;

public abstract class ActorCreationController extends ControllerBase {

    public EventHandler onActivate = new EventHandler();
    public EventHandler onDeactivate = new EventHandler();
    protected PlayService playService;
    protected PlayingSurfaceLayerController playingSurfaceLayerController;
    protected LayerController layerController;
    protected ActorModel actorModel;
    protected PlayModel playModel;
    protected Boolean enabled = false;

    public ActorCreationController(PlayingSurfaceLayerController playingSurfaceLayerController, LayerController layerController, PlayModel playModel, PlayService playService) {
        this.playingSurfaceLayerController = playingSurfaceLayerController;
        this.layerController = layerController;
        this.playModel = playModel;
        this.playService = playService;
        playingSurfaceLayerController.onMouseMoved.addHandler(this::onPlayingSurfaceMouseMoved);
        playingSurfaceLayerController.onMouseClicked.addHandler(this::onPlayingSurfaceMouseClicked);
    }

    protected void activate(ActorModel actorModel) {
        initEditionLayer(actorModel);
        enabled = true;
        onActivate.fire(this);
    }

    protected void initEditionLayer(ActorModel actorModel) {
        layerController.removeActorLayer(this.actorModel);
        layerController.setAllOpacity(0.5);
        layerController.addActorLayer(actorModel);
        layerController.setAllMouseTransparent(true);
        this.actorModel = actorModel;
    }

    public void deactivate() {
        if (enabled) {
            layerController.removeActorLayer(actorModel);
            layerController.setAllMouseTransparent(false);
            layerController.setAllOpacity(1.0);
            enabled = false;
            onDeactivate.fire(this);
        }
    }

    protected void onPlayingSurfaceMouseMoved(Object sender, MouseEvent e) {
        if (enabled) {
            actorModel.position.set(playingSurfaceLayerController.getRelativeMousePosition());
        }
    }

    protected abstract void onPlayingSurfaceMouseClicked(Object sender, MouseEvent e);

}
