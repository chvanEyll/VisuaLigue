package ca.ulaval.glo2004.visualigue.ui.controllers.playeditor.scene.scene2d;

import ca.ulaval.glo2004.visualigue.services.play.PlayService;
import ca.ulaval.glo2004.visualigue.ui.controllers.ControllerBase;
import ca.ulaval.glo2004.visualigue.ui.controllers.common.ExtendedButton;
import ca.ulaval.glo2004.visualigue.ui.controllers.playeditor.scene.SceneController;
import ca.ulaval.glo2004.visualigue.ui.models.actors.ActorModel;
import ca.ulaval.glo2004.visualigue.utils.EventHandler;
import ca.ulaval.glo2004.visualigue.utils.geometry.Vector2;
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
    protected SceneController sceneController;
    protected ActorModel actorModel;

    final void init(ActorModel actorModel, SceneController sceneController) {
        this.actorModel = actorModel;
        this.sceneController = sceneController;
        tooltip.textProperty().bind(actorModel.hoverText);
        rootNode.mouseTransparentProperty().bindBidirectional(actorModel.isLocked);
        rootNode.opacityProperty().bindBidirectional(actorModel.opacity);
        rootNode.visibleProperty().bindBidirectional(actorModel.visible);
        init(actorModel);
    }

    public ActorModel getModel() {
        return actorModel;
    }

    public abstract void init(ActorModel actorModel);

    public abstract void render();

    protected Double getScaledValue(Double value) {
        if (sceneController.settings.resizeActorsOnZoomProperty.get()) {
            return value * sceneController.zoomProperty().get().getValue();
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
