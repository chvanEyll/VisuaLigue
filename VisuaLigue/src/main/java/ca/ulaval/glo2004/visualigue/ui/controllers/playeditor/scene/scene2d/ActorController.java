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
import javafx.scene.control.Tooltip;
import javafx.scene.layout.Pane;
import javax.inject.Inject;

public abstract class ActorController extends ControllerBase {

    private static final String DROP_HIGHLIGHT_CSS_STYLE_CLASS = "drop-highlight";
    public EventHandler<Vector2> onMouseMoved = new EventHandler();
    public EventHandler<Vector2> onMouseClicked = new EventHandler();
    @FXML protected Pane rootNode;
    @FXML protected ExtendedButton actorButton;
    @FXML protected Tooltip tooltip;
    @Inject protected PlayService playService;
    protected PlayingSurfaceLayerController playingSurfaceLayerController;
    protected ActorModel layerModel;
    protected ObjectProperty<Zoom> zoomProperty;
    protected Settings settings;
    protected PlayModel playModel;
    protected FrameModel frameModel;

    final void init(ActorModel actorModel, PlayModel playModel, FrameModel frameModel, PlayingSurfaceLayerController playingSurfaceLayerController, ObjectProperty<Zoom> zoomProperty, Settings settings) {
        this.layerModel = actorModel;
        this.playModel = playModel;
        this.frameModel = frameModel;
        this.playingSurfaceLayerController = playingSurfaceLayerController;
        this.zoomProperty = zoomProperty;
        this.settings = settings;
        tooltip.textProperty().bind(actorModel.hoverText);
        rootNode.mouseTransparentProperty().bindBidirectional(actorModel.isLocked);
        rootNode.opacityProperty().bindBidirectional(actorModel.opacity);
        rootNode.visibleProperty().bindBidirectional(actorModel.visible);
        rootNode.mouseTransparentProperty().bindBidirectional(actorModel.mouseTransparent);
        init(actorModel);
    }

    public ActorModel getModel() {
        return layerModel;
    }

    public abstract void init(ActorModel layerModel);

    public abstract void update();

    protected Double getScaledValue(Double value) {
        if (settings.resizeActorsOnZoomProperty.get()) {
            return value * zoomProperty.get().getValue();
        } else {
            return value;
        }
    }

    protected void enableDropHighlight() {
        actorButton.getStyleClass().add(DROP_HIGHLIGHT_CSS_STYLE_CLASS);
    }

    protected void disableDropHighlight() {
        actorButton.getStyleClass().remove(DROP_HIGHLIGHT_CSS_STYLE_CLASS);
    }
}
