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
    protected Boolean showActorLabels = false;
    protected Boolean showMovementArrows = false;
    protected Boolean resizeActorsOnZoom = true;

    public void init(ActorModel actorModel, PlayingSurfaceLayerController playingSurfaceController, Boolean showActorLabels, Boolean showMovementArrows, Boolean resizeActorsOnZoom) {
        this.actorModel = actorModel;
        this.playingSurfaceLayerController = playingSurfaceController;
        this.showActorLabels = showActorLabels;
        this.showMovementArrows = showMovementArrows;
        this.resizeActorsOnZoom = resizeActorsOnZoom;
        tooltip.textProperty().bind(actorModel.hoverText);
    }

    protected Double getScaledValue(Double value) {
        if (resizeActorsOnZoom) {
            return value * zoom.getValue();
        } else {
            return value;
        }
    }

    public abstract void update();

    public abstract void updateZoom(Zoom zoom);

    public void setActorLabelDisplay(Boolean showActorLabels) {
        this.showActorLabels = showActorLabels;
        update();
    }

    public void setMovementArrowDisplay(Boolean showMovementArrows) {
        this.showMovementArrows = showMovementArrows;
        update();
    }

    public void setResizeActorOnZoom(Boolean resizeActorsOnZoom) {
        this.resizeActorsOnZoom = resizeActorsOnZoom;
        update();
    }

}
