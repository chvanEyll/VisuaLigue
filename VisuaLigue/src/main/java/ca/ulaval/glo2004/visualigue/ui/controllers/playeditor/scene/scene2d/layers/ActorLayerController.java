package ca.ulaval.glo2004.visualigue.ui.controllers.playeditor.scene.scene2d.layers;

import ca.ulaval.glo2004.visualigue.ui.controllers.ControllerBase;
import ca.ulaval.glo2004.visualigue.ui.controllers.common.ExtendedButton;
import ca.ulaval.glo2004.visualigue.ui.controllers.playeditor.scene.Zoom;
import ca.ulaval.glo2004.visualigue.ui.models.ActorModel;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Tooltip;

public abstract class ActorLayerController extends ControllerBase {

    @FXML protected ExtendedButton actorButton;
    @FXML protected Tooltip tooltip;
    protected PlayingSurfaceLayerController playingSurfaceLayerController;
    protected ActorModel actorModel;
    protected ObjectProperty<Zoom> zoomProperty;
    protected BooleanProperty showActorLabelsProperty;
    protected BooleanProperty showMovementArrowsProperty;
    protected BooleanProperty resizeActorsOnZoomProperty;

    public void init(ActorModel actorModel, PlayingSurfaceLayerController playingSurfaceController, ObjectProperty<Zoom> zoomProperty, BooleanProperty showActorLabelsProperty, BooleanProperty showMovementArrowsProperty, BooleanProperty resizeActorsOnZoomProperty) {
        this.actorModel = actorModel;
        this.playingSurfaceLayerController = playingSurfaceController;
        this.zoomProperty = zoomProperty;
        this.showActorLabelsProperty = showActorLabelsProperty;
        this.showMovementArrowsProperty = showMovementArrowsProperty;
        this.resizeActorsOnZoomProperty = resizeActorsOnZoomProperty;
        tooltip.textProperty().bind(actorModel.hoverText);
    }

    protected Double getScaledValue(Double value) {
        if (resizeActorsOnZoomProperty.get()) {
            return value * zoomProperty.get().getValue();
        } else {
            return value;
        }
    }

    public abstract void update();

}
