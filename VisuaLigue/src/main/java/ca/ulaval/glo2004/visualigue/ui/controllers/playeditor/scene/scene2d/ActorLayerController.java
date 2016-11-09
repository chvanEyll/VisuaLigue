package ca.ulaval.glo2004.visualigue.ui.controllers.playeditor.scene.scene2d;

import ca.ulaval.glo2004.visualigue.services.play.PlayService;
import ca.ulaval.glo2004.visualigue.ui.controllers.ControllerBase;
import ca.ulaval.glo2004.visualigue.ui.controllers.common.ExtendedButton;
import ca.ulaval.glo2004.visualigue.ui.controllers.playeditor.scene.Settings;
import ca.ulaval.glo2004.visualigue.ui.controllers.playeditor.scene.Zoom;
import ca.ulaval.glo2004.visualigue.ui.models.FrameModel;
import ca.ulaval.glo2004.visualigue.ui.models.PlayModel;
import ca.ulaval.glo2004.visualigue.ui.models.layers.ActorLayerModel;
import ca.ulaval.glo2004.visualigue.utils.EventHandler;
import ca.ulaval.glo2004.visualigue.utils.geometry.Vector2;
import javafx.beans.property.ObjectProperty;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.Tooltip;
import javax.inject.Inject;

public abstract class ActorLayerController extends ControllerBase {

    public EventHandler<Vector2> onMouseMoved = new EventHandler();
    public EventHandler<Vector2> onMouseClicked = new EventHandler();
    @FXML protected ExtendedButton actorButton;
    @FXML protected Tooltip tooltip;
    @Inject protected PlayService playService;
    protected PlayingSurfaceLayerController playingSurfaceLayerController;
    protected ActorLayerModel layerModel;
    protected ObjectProperty<Zoom> zoomProperty;
    protected Settings settings;
    protected PlayModel playModel;
    protected FrameModel frameModel;

    final void init(ActorLayerModel layerModel, PlayModel playModel, PlayingSurfaceLayerController playingSurfaceController, ObjectProperty<Zoom> zoomProperty, Settings settings) {
        this.layerModel = layerModel;
        this.playModel = playModel;
        this.playingSurfaceLayerController = playingSurfaceController;
        this.zoomProperty = zoomProperty;
        this.settings = settings;
        tooltip.textProperty().bind(layerModel.hoverText);
        actorButton.setCursor(Cursor.MOVE);
        init(layerModel);
    }

    public abstract void init(ActorLayerModel layerModel);

    public abstract void update();

    protected Double getScaledValue(Double value) {
        if (settings.resizeActorsOnZoomProperty.get()) {
            return value * zoomProperty.get().getValue();
        } else {
            return value;
        }
    }

}
