package ca.ulaval.glo2004.visualigue.ui.controllers.playeditor.scene.scene2d;

import ca.ulaval.glo2004.visualigue.services.play.PlayService;
import ca.ulaval.glo2004.visualigue.ui.controllers.ControllerBase;
import ca.ulaval.glo2004.visualigue.ui.controllers.common.ExtendedButton;
import ca.ulaval.glo2004.visualigue.ui.controllers.playeditor.scene.Settings;
import ca.ulaval.glo2004.visualigue.ui.controllers.playeditor.scene.Zoom;
import ca.ulaval.glo2004.visualigue.ui.models.FrameModel;
import ca.ulaval.glo2004.visualigue.ui.models.PlayModel;
import ca.ulaval.glo2004.visualigue.ui.models.actors.ActorModel;
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
    protected ActorModel actorModel;
    protected ObjectProperty<Zoom> zoomProperty;
    protected Settings settings;
    protected PlayModel playModel;
    protected FrameModel frameModel;

    final void init(ActorModel actorModel, PlayModel playModel, FrameModel frameModel, PlayingSurfaceLayerController playingSurfaceController, ObjectProperty<Zoom> zoomProperty, Settings settings) {
        this.actorModel = actorModel;
        this.playModel = playModel;
        this.frameModel = frameModel;
        this.playingSurfaceLayerController = playingSurfaceController;
        this.zoomProperty = zoomProperty;
        this.settings = settings;
        tooltip.textProperty().bind(actorModel.hoverText);
        actorButton.setCursor(Cursor.MOVE);
        init(actorModel);
    }

    public abstract void init(ActorModel actorModel);

    public abstract void update();

    protected Double getScaledValue(Double value) {
        if (settings.resizeActorsOnZoomProperty.get()) {
            return value * zoomProperty.get().getValue();
        } else {
            return value;
        }
    }

}
