package ca.ulaval.glo2004.visualigue.ui.controllers.playeditor.scene.scene2d.layers;

import ca.ulaval.glo2004.visualigue.ui.controllers.ControllerBase;
import ca.ulaval.glo2004.visualigue.ui.controllers.playeditor.scene.Zoom;
import ca.ulaval.glo2004.visualigue.ui.models.ActorModel;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;

public abstract class ActorLayerController extends ControllerBase {

    @FXML protected Button actorButton;
    @FXML protected Tooltip tooltip;
    protected PlayingSurfaceLayerController playingSurfaceLayerController;
    protected ActorModel actorModel;
    protected Zoom zoom;

    public void init(ActorModel actorModel, PlayingSurfaceLayerController playingSurfaceController) {
        this.actorModel = actorModel;
        this.playingSurfaceLayerController = playingSurfaceController;
        tooltip.textProperty().bind(actorModel.hoverText);
    }

    protected Double getScaledValue(Double value) {
        return value * zoom.getValue();
    }

    public abstract void update();

    public abstract void updateZoom(Zoom zoom);

    public void setActorLabelDisplayEnabled(Boolean enabled) {
    }

}
