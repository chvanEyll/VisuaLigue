package ca.ulaval.glo2004.visualigue.ui.controllers.playeditor.scene.scene2d.layers;

import ca.ulaval.glo2004.visualigue.ui.controllers.common.Arrow;
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
    public static final Double BASE_BUTTON_SCALING = 1.25;
    public static final Double ARROW_HEAD_SIZE = 15.0;
    public static final Double ARROW_STROKE_DASH_ARRAY_SIZE = 10.0;
    @FXML private PlayerIcon playerIcon;
    @FXML private Label label;
    @FXML private Arrow arrow;

    @Override
    public void init(ActorModel actorModel, PlayingSurfaceLayerController playingLayerSurfaceController, Boolean showActorLabels, Boolean showMovementArrows, Boolean resizeActorsOnZoom) {
        super.init(actorModel, playingLayerSurfaceController, showActorLabels, showMovementArrows, resizeActorsOnZoom);
        label.textProperty().bind(actorModel.label);
        actorModel.position.addListener(this::onActorPositionChanged);
        actorModel.nextPosition.addListener(this::onActorPositionChanged);
        actorModel.orientation.addListener(this::onActorOrientationChanged);
        actorModel.label.addListener(this::onActorDescriptionChanged);
        update();
    }

    private void onActorPositionChanged(final ObservableValue<? extends Vector2> value, final Vector2 oldPropertyValue, final Vector2 newPropertyValue) {
        update();
    }

    private void onActorOrientationChanged(final ObservableValue<? extends Number> value, final Number oldPropertyValue, final Number newPropertyValue) {
        update();
    }

    private void onActorDescriptionChanged(final ObservableValue<? extends String> value, final String oldPropertyValue, final String newPropertyValue) {
        update();
    }

    @Override
    public void update() {
        Vector2 actorPosition = playingSurfaceLayerController.relativeToSurfacePoint(actorModel.position.get());
        Vector2 nextActorPosition;
        if (actorModel.nextPosition.isNotNull().get()) {
            nextActorPosition = playingSurfaceLayerController.relativeToSurfacePoint(actorModel.nextPosition.get());
        } else {
            nextActorPosition = null;
        }
        Platform.runLater(() -> {
            updateActor(actorPosition);
            updateArrow(actorPosition, nextActorPosition);
            updateLabel(actorPosition);
        });
    }

    private void updateActor(Vector2 actorPosition) {
        actorButton.setScaleX(getScaledValue(BASE_BUTTON_SCALING));
        actorButton.setScaleY(getScaledValue(BASE_BUTTON_SCALING));
        actorButton.setLayoutX(actorPosition.getX() - actorButton.getWidth() / 2);
        actorButton.setLayoutY(actorPosition.getY() - actorButton.getHeight() / 2);
        actorButton.setRotate(actorModel.orientation.get());
        playerIcon.setColor(actorModel.color.get());
    }

    private void updateArrow(Vector2 currentActorPosition, Vector2 nextActorPosition) {
        arrow.setVisible(showMovementArrows == true && nextActorPosition != null);
        if (showMovementArrows == true && nextActorPosition != null) {
            arrow.setStroke(actorModel.color.get());
            arrow.setStrokeWidth(getScaledValue(3.0));
            arrow.getStrokeDashArray().setAll(getScaledValue(ARROW_STROKE_DASH_ARRAY_SIZE));
            arrow.setArrowFill(actorModel.color.get());
            arrow.setHeadSize(new Vector2(getScaledValue(ARROW_HEAD_SIZE), getScaledValue(ARROW_HEAD_SIZE)));
            arrow.setTailGrow(-actorButton.getWidth() * actorButton.getScaleX());
            arrow.setHeadLocation(nextActorPosition);
            arrow.setTailLocation(currentActorPosition);
        }
    }

    private void updateLabel(Vector2 actorPosition) {
        label.setVisible(showActorLabels);
        label.setScaleX(getScaledValue(1.0));
        label.setScaleY(getScaledValue(1.0));
        label.setLayoutX(actorPosition.getX() - label.getWidth() / 2);
        label.setLayoutY(actorPosition.getY() + getScaledValue(LABEL_OFFSET_Y) + getScaledValue(label.getHeight()) / 2);
    }

    @Override
    public void updateZoom(Zoom zoom) {
        this.zoom = zoom;
        update();
    }

}
