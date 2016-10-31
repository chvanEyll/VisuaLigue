package ca.ulaval.glo2004.visualigue.ui.controllers.playeditor.scene.scene2d.layers;

import ca.ulaval.glo2004.visualigue.ui.controllers.ControllerBase;
import ca.ulaval.glo2004.visualigue.ui.controllers.playeditor.scene.Zoom;
import ca.ulaval.glo2004.visualigue.ui.controllers.playeditor.scene.scene2d.PlayingSurfaceController;
import ca.ulaval.glo2004.visualigue.ui.models.ActorModel;

public abstract class SceneLayerController extends ControllerBase {

    protected PlayingSurfaceController playingSurfaceController;
    protected ActorModel actorModel;

    public void init(ActorModel actorModel, PlayingSurfaceController playingSurfaceController) {
        this.actorModel = actorModel;
        this.playingSurfaceController = playingSurfaceController;
    }

    public abstract void setZoom(Zoom zoom);

    public abstract void setPlayerCategoryLabelDisplayEnabled(Boolean enabled);

}
