package ca.ulaval.glo2004.visualigue.ui.controllers.playeditor.scene.scene2d.layers;

import ca.ulaval.glo2004.visualigue.ui.controllers.common.PlayerIcon;
import ca.ulaval.glo2004.visualigue.ui.controllers.playeditor.scene.Zoom;
import ca.ulaval.glo2004.visualigue.ui.models.ActorModel;
import ca.ulaval.glo2004.visualigue.utils.geometry.Vector2;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class PlayerLayerController extends ActorLayerController {

    private static final Double LABEL_OFFSET_Y = 15.0;
    public static final String VIEW_NAME = "/views/playeditor/scene2d/layers/player-layer.fxml";
    public static final Double BASE_BUTTON_SCALING = 1.5;
    @FXML private PlayerIcon playerIcon;
    @FXML protected Label label;
    protected Boolean showLabel = false;

    @Override
    public void init(ActorModel actorModel, PlayingSurfaceLayerController playingLayerSurfaceController) {
        super.init(actorModel, playingLayerSurfaceController);
        label.textProperty().bind(actorModel.label);
        actorModel.x.addListener(this::onActorPositionChanged);
        actorModel.y.addListener(this::onActorPositionChanged);
        actorModel.orientation.addListener(this::onActorPositionChanged);
        actorModel.label.addListener(this::onActorDescriptionChanged);
        update();
    }

    private void onActorPositionChanged(final ObservableValue<? extends Number> value, final Number oldPropertyValue, final Number newPropertyValue) {
        update();
    }

    private void onActorDescriptionChanged(final ObservableValue<? extends String> value, final String oldPropertyValue, final String newPropertyValue) {
        update();
    }

    @Override
    public void update() {
        Vector2 actorLocation = new Vector2(actorModel.x.get(), actorModel.y.get());
        Vector2 surfacePoint = playingSurfaceLayerController.relativeToSurfacePoint(actorLocation);
        Platform.runLater(() -> {
            updateActor(surfacePoint);
            updateLabel(surfacePoint);
        });
    }

    private void updateActor(Vector2 surfacePoint) {
        actorButton.setScaleX(getScaledValue(1.0) * BASE_BUTTON_SCALING);
        actorButton.setScaleY(getScaledValue(1.0) * BASE_BUTTON_SCALING);
        actorButton.setLayoutX(surfacePoint.getX() - actorButton.getWidth() / 2);
        actorButton.setLayoutY(surfacePoint.getY() - actorButton.getHeight() / 2);
        actorButton.setRotate(actorModel.orientation.get());
        playerIcon.setColor(actorModel.color.get());
    }

    private void updateLabel(Vector2 surfacePoint) {
        label.setVisible(showLabel);
        label.setScaleX(getScaledValue(1.0));
        label.setScaleY(getScaledValue(1.0));
        label.setLayoutX(surfacePoint.getX() - label.getWidth() / 2);
        label.setLayoutY(surfacePoint.getY() + getScaledValue(LABEL_OFFSET_Y) + getScaledValue(label.getHeight()) / 2);
    }

    @Override
    public void updateZoom(Zoom zoom) {
        this.zoom = zoom;
        update();
    }

    @Override
    public void setActorLabelDisplayEnabled(Boolean enabled) {
        showLabel = enabled;
        update();
    }

}
