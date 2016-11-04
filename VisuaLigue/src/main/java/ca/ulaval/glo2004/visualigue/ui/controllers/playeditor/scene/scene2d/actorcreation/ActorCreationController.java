package ca.ulaval.glo2004.visualigue.ui.controllers.playeditor.scene.scene2d.actorcreation;

import ca.ulaval.glo2004.visualigue.services.play.PlayService;
import ca.ulaval.glo2004.visualigue.ui.controllers.ControllerBase;
import ca.ulaval.glo2004.visualigue.ui.controllers.playeditor.scene.scene2d.LayerController;
import ca.ulaval.glo2004.visualigue.ui.controllers.playeditor.scene.scene2d.layers.PlayingSurfaceLayerController;
import ca.ulaval.glo2004.visualigue.ui.converters.ActorModelConverter;
import ca.ulaval.glo2004.visualigue.ui.models.ActorModel;
import ca.ulaval.glo2004.visualigue.ui.models.PlayModel;
import ca.ulaval.glo2004.visualigue.utils.EventHandler;
import javafx.scene.input.MouseEvent;

public abstract class ActorCreationController extends ControllerBase {

    public EventHandler onCreationModeEntered = new EventHandler();
    public EventHandler onCreationModeExited = new EventHandler();
    protected PlayService playService;
    protected ActorModelConverter actorModelConverter;
    protected PlayingSurfaceLayerController playingSurfaceLayerController;
    protected LayerController layerController;
    protected ActorModel actorModel;
    protected PlayModel playModel;
    protected Boolean enabled = false;

    public ActorCreationController(PlayingSurfaceLayerController playingSurfaceLayerController, LayerController layerController, ActorModelConverter actorModelConverter, PlayModel playModel, PlayService playService) {
        this.playingSurfaceLayerController = playingSurfaceLayerController;
        this.layerController = layerController;
        this.actorModelConverter = actorModelConverter;
        this.playModel = playModel;
        this.playService = playService;
        playingSurfaceLayerController.onMouseMoved.addHandler(this::onPlayingSurfaceMouseMoved);
        playingSurfaceLayerController.onMouseClicked.addHandler(this::onPlayingSurfaceMouseClicked);
    }

    protected void enterCreationMode(ActorModel actorModel) {
        initActorCreationLayer(actorModel);
        enabled = true;
        onCreationModeEntered.fire(this);
    }

    protected void initActorCreationLayer(ActorModel actorModel) {
        layerController.removeActorLayer(this.actorModel);
        layerController.setAllOpacity(0.5);
        layerController.addActorLayer(actorModel);
        layerController.setAllMouseTransparent(true);
        this.actorModel = actorModel;
    }

    public void exitCreationMode() {
        if (enabled) {
            layerController.removeActorLayer(actorModel);
            layerController.setAllMouseTransparent(false);
            layerController.setAllOpacity(1.0);
            enabled = false;
            onCreationModeExited.fire(this);
        }
    }

    protected void onPlayingSurfaceMouseMoved(Object sender, MouseEvent e) {
        if (enabled) {
            actorModel.position.set(playingSurfaceLayerController.getRelativeMousePosition());
        }
    }

    protected abstract void onPlayingSurfaceMouseClicked(Object sender, MouseEvent e);

}
