package ca.ulaval.glo2004.visualigue.ui.controllers.playeditor.scene.scene2d.layers;

import ca.ulaval.glo2004.visualigue.ui.controllers.ControllerBase;
import ca.ulaval.glo2004.visualigue.ui.models.ActorModel;

public abstract class ActorLayerController extends ControllerBase {

    protected PlayingSurfaceLayerController playingSurfaceLayerController;
    protected ActorModel actorModel;

    public void init(ActorModel actorModel, PlayingSurfaceLayerController playingSurfaceController) {
        this.actorModel = actorModel;
        this.playingSurfaceLayerController = playingSurfaceController;
    }

    public abstract void update();

    public abstract void setPlayerCategoryLabelDisplayEnabled(Boolean enabled);

}
