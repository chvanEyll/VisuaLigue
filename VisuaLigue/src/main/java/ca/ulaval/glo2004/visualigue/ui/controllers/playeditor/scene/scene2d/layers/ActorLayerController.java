package ca.ulaval.glo2004.visualigue.ui.controllers.playeditor.scene.scene2d.layers;

import ca.ulaval.glo2004.visualigue.ui.controllers.ControllerBase;
import ca.ulaval.glo2004.visualigue.ui.controllers.playeditor.scene.Zoom;
import ca.ulaval.glo2004.visualigue.ui.models.ActorModel;

public abstract class ActorLayerController extends ControllerBase {

    protected PlayingSurfaceLayerController playingSurfaceLayerController;
    protected ActorModel actorModel;
    protected Zoom zoom;

    public void init(ActorModel actorModel, PlayingSurfaceLayerController playingSurfaceController) {
        this.actorModel = actorModel;
        this.playingSurfaceLayerController = playingSurfaceController;
    }

    public abstract void update();

    public abstract void updateZoom(Zoom zoom);

    public abstract void setPlayerCategoryLabelDisplayEnabled(Boolean enabled);

}
