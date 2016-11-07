package ca.ulaval.glo2004.visualigue.ui.controllers.playeditor.scene.scene2d;

import ca.ulaval.glo2004.visualigue.ui.controllers.ControllerBase;
import ca.ulaval.glo2004.visualigue.ui.controllers.common.ExtendedButton;
import ca.ulaval.glo2004.visualigue.ui.controllers.playeditor.scene.Zoom;
import ca.ulaval.glo2004.visualigue.ui.models.actors.ActorModel;
import ca.ulaval.glo2004.visualigue.utils.EventHandler;
import ca.ulaval.glo2004.visualigue.utils.geometry.Vector2;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.Tooltip;

public abstract class ActorLayerController extends ControllerBase {

    public EventHandler<Vector2> onMouseMoved = new EventHandler();
    public EventHandler<Vector2> onMouseClicked = new EventHandler();
    @FXML protected ExtendedButton actorButton;
    @FXML protected Tooltip tooltip;
    protected PlayingSurfaceLayerController playingSurfaceLayerController;
    protected ActorModel actorModel;
    protected ObjectProperty<Zoom> zoomProperty;
    protected BooleanProperty showActorLabelsProperty;
    protected BooleanProperty showMovementArrowsProperty;
    protected BooleanProperty resizeActorsOnZoomProperty;

    final void init(ActorModel actorModel, PlayingSurfaceLayerController playingSurfaceController, ObjectProperty<Zoom> zoomProperty, BooleanProperty showActorLabelsProperty, BooleanProperty showMovementArrowsProperty, BooleanProperty resizeActorsOnZoomProperty) {
        this.actorModel = actorModel;
        this.playingSurfaceLayerController = playingSurfaceController;
        this.zoomProperty = zoomProperty;
        this.showActorLabelsProperty = showActorLabelsProperty;
        this.showMovementArrowsProperty = showMovementArrowsProperty;
        this.resizeActorsOnZoomProperty = resizeActorsOnZoomProperty;
        tooltip.textProperty().bind(actorModel.hoverText);
        actorButton.setCursor(Cursor.MOVE);
        init(actorModel);
    }

    public abstract void init(ActorModel actorModel);

    public abstract void update();

    protected Double getScaledValue(Double value) {
        if (resizeActorsOnZoomProperty.get()) {
            return value * zoomProperty.get().getValue();
        } else {
            return value;
        }
    }

}
