package ca.ulaval.glo2004.visualigue.ui.controllers.playeditor.actorlayers;

import ca.ulaval.glo2004.visualigue.ui.controllers.common.Arrow;
import ca.ulaval.glo2004.visualigue.ui.controllers.common.ExtendedButton;
import ca.ulaval.glo2004.visualigue.ui.controllers.common.ExtendedLabel;
import ca.ulaval.glo2004.visualigue.ui.controllers.common.PlayerIcon;
import ca.ulaval.glo2004.visualigue.ui.controllers.playeditor.scene.scene2d.ActorLayerController;
import ca.ulaval.glo2004.visualigue.ui.models.layers.ActorLayerModel;
import ca.ulaval.glo2004.visualigue.ui.models.layers.BallLayerModel;
import ca.ulaval.glo2004.visualigue.ui.models.layers.PlayerLayerModel;
import ca.ulaval.glo2004.visualigue.utils.TimerTaskUtils;
import ca.ulaval.glo2004.visualigue.utils.geometry.Line;
import ca.ulaval.glo2004.visualigue.utils.geometry.Vector2;
import ca.ulaval.glo2004.visualigue.utils.javafx.DragUtils;
import java.util.Timer;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.input.MouseEvent;

public class PlayerLayerController extends ActorLayerController {

    public static final String VIEW_NAME = "/views/playeditor/actorlayers/player-layer.fxml";
    private static final Double LABEL_OFFSET_Y = 30.0;
    private static final Vector2 ROTATION_ARROW_OFFSET = new Vector2(30, -30);
    private static final Integer ROTATION_ARROW_HIDE_DELAY = 500;
    private static final Double BASE_BUTTON_SCALING = 1.25;
    private static final Double ARROW_HEAD_SIZE = 15.0;
    private static final Double ARROW_STROKE_DASH_ARRAY_SIZE = 10.0;
    private PlayerLayerModel playerLayerModel;
    private ChangeListener<Object> onChange = this::onChange;
    @FXML private PlayerIcon playerIcon;
    @FXML private ExtendedLabel label;
    @FXML private Arrow arrow;
    @FXML private ExtendedButton rotationArrow;
    private Timer rotationArrowTimer = new Timer();
    private Double initialRotationAngle;
    private Double initialPlayerOrientation;
    private Boolean rotationArrowDragging = false;

    @Override
    public void init(ActorLayerModel layerModel) {
        this.playerLayerModel = (PlayerLayerModel) layerModel;
        label.textProperty().bind(playerLayerModel.label);
        addListeners();
        update();
    }

    private void addListeners() {
        playerLayerModel.position.addListener(onChange);
        playerLayerModel.nextPosition.addListener(onChange);
        playerLayerModel.orientation.addListener(onChange);
        playerLayerModel.label.addListener(onChange);
        playerLayerModel.showRotationArrow.addListener(onChange);
        settings.showActorLabelsProperty.addListener(onChange);
        settings.showMovementArrowsProperty.addListener(onChange);
        settings.resizeActorsOnZoomProperty.addListener(onChange);
        zoomProperty.addListener(onChange);
        actorButton.layoutReadyProperty().addListener(this::onChange);
        label.layoutReadyProperty().addListener(this::onChange);
    }

    @Override
    public void clean() {
        settings.showActorLabelsProperty.removeListener(onChange);
        settings.showMovementArrowsProperty.removeListener(onChange);
        settings.resizeActorsOnZoomProperty.removeListener(onChange);
        zoomProperty.removeListener(onChange);
        super.clean();
    }

    private void onChange(final ObservableValue<? extends Object> value, final Object oldPropertyValue, final Object newPropertyValue) {
        update();
    }

    @Override
    public void update() {
        Vector2 actorPosition;
        if (playerLayerModel.position.isNotNull().get()) {
            actorPosition = playingSurfaceLayerController.sizeRelativeToSurfacePoint(playerLayerModel.position.get());
        } else {
            actorPosition = null;
        }
        Vector2 nextActorPosition;
        if (playerLayerModel.nextPosition.isNotNull().get()) {
            nextActorPosition = playingSurfaceLayerController.sizeRelativeToSurfacePoint(playerLayerModel.nextPosition.get());
        } else {
            nextActorPosition = null;
        }
        Platform.runLater(() -> {
            updateActor(actorPosition);
            updateArrow(actorPosition, nextActorPosition);
            updateLabel(actorPosition);
            updateRotationArrow(actorPosition);
        });
    }

    private void updateActor(Vector2 actorPosition) {
        Boolean showActor = actorPosition != null;
        if (showActor) {
            actorButton.setScaleX(getScaledValue(BASE_BUTTON_SCALING));
            actorButton.setScaleY(getScaledValue(BASE_BUTTON_SCALING));
            actorButton.setLayoutX(actorPosition.getX() - actorButton.getWidth() / 2);
            actorButton.setLayoutY(actorPosition.getY() - actorButton.getHeight() / 2);
            actorButton.setRotate(-playerLayerModel.orientation.get());
            playerIcon.setColor(playerLayerModel.color.get());
        }
        actorButton.setVisible(showActor);
        actorButton.setCursor(layerModel.isLocked.get() ? Cursor.DEFAULT : Cursor.MOVE);
    }

    private void updateArrow(Vector2 currentActorPosition, Vector2 nextActorPosition) {
        Boolean showArrow = settings.showMovementArrowsProperty.get() == true && nextActorPosition != null;
        if (showArrow) {
            arrow.setStroke(playerLayerModel.color.get());
            arrow.setStrokeWidth(getScaledValue(3.0));
            arrow.getStrokeDashArray().setAll(getScaledValue(ARROW_STROKE_DASH_ARRAY_SIZE));
            arrow.setArrowFill(playerLayerModel.color.get());
            arrow.setHeadSize(new Vector2(getScaledValue(ARROW_HEAD_SIZE), getScaledValue(ARROW_HEAD_SIZE)));
            arrow.setTailGrow(-actorButton.getWidth() * actorButton.getScaleX());
            arrow.setHeadGrow(-actorButton.getWidth() * actorButton.getScaleX());
            arrow.setHeadLocation(nextActorPosition);
            arrow.setTailLocation(currentActorPosition);
        }
        arrow.setVisible(showArrow);
    }

    private void updateLabel(Vector2 actorPosition) {
        Boolean showLabel = actorPosition != null && playerLayerModel.showLabel.get() && settings.showActorLabelsProperty.get();
        if (showLabel) {
            label.setScaleX(getScaledValue(1.0));
            label.setScaleY(getScaledValue(1.0));
            label.setLayoutX(actorPosition.getX() - label.getWidth() / 2);
            label.setLayoutY(actorPosition.getY() - label.getHeight() / 2 - getScaledValue(LABEL_OFFSET_Y));
        }
        label.setVisible(showLabel);
    }

    private void updateRotationArrow(Vector2 actorPosition) {
        Boolean showArrow = actorPosition != null && playerLayerModel.showRotationArrow.get();
        if (showArrow) {
            rotationArrow.setScaleX(getScaledValue(1.0));
            rotationArrow.setScaleY(getScaledValue(1.0));
            rotationArrow.setLayoutX(actorPosition.getX() - rotationArrow.getWidth() / 2 + getScaledValue(ROTATION_ARROW_OFFSET.getX()));
            rotationArrow.setLayoutY(actorPosition.getY() - rotationArrow.getHeight() / 2 + getScaledValue(ROTATION_ARROW_OFFSET.getY()));
        }
        rotationArrow.setVisible(showArrow);
    }

    @FXML
    protected void onMouseDragEntered(MouseEvent e) {
        if (DragUtils.getSource() instanceof BallLayerModel) {
            enableDropHighlight();
        }
    }

    @FXML
    protected void onMouseDragExited(MouseEvent e) {
        disableDropHighlight();
    }

    @FXML
    protected void onMouseDragReleased(MouseEvent e) {
        disableDropHighlight();
    }

    @FXML
    protected void onMouseEntered(MouseEvent e) {
        playerLayerModel.showRotationArrow.set(true);
    }

    @FXML
    protected void onMouseExited(MouseEvent e) {
        scheduleRotationArrowHide();
    }

    @FXML
    protected void onMouseDragged(MouseEvent e) {
        playerLayerModel.position.set(playingSurfaceLayerController.getSizeRelativeMousePosition(true));
    }

    @FXML
    protected void onMouseReleased(MouseEvent e) {
        playService.updatePlayerActorPositionDirect(playModel.getUUID(), frameModel.time.get(), playerLayerModel.getUUID(), playerLayerModel.position.get());
    }

    @FXML
    protected void onRotateArrowMouseEntered(MouseEvent e) {
        cancelRotationArrowHide();
    }

    @FXML
    protected void onRotateArrowMouseExited(MouseEvent e) {
        scheduleRotationArrowHide();
    }

    @FXML
    protected void onRotateArrowMousePressed(MouseEvent e) {
        cancelRotationArrowHide();
        Vector2 mousePosition = playingSurfaceLayerController.getMousePosition();
        initialRotationAngle = new Line(playingSurfaceLayerController.sizeRelativeToSurfacePoint(playerLayerModel.position.get()), mousePosition).getAngle();
        initialPlayerOrientation = playerLayerModel.orientation.get();
    }

    @FXML
    protected void onRotateArrowMouseDragged(MouseEvent e) {
        Vector2 mousePosition = playingSurfaceLayerController.getMousePosition();
        Double newAngle = new Line(playingSurfaceLayerController.sizeRelativeToSurfacePoint(playerLayerModel.position.get()), mousePosition).getAngle();
        playerLayerModel.orientation.set(initialPlayerOrientation + (newAngle - initialRotationAngle));
        rotationArrowDragging = true;
    }

    @FXML
    protected void onRotateArrowMouseReleased(MouseEvent e) {
        rotationArrowDragging = false;
        scheduleRotationArrowHide();
        playService.updatePlayerActorOrientation(playModel.getUUID(), frameModel.time.get(), playerLayerModel.getUUID(), playerLayerModel.orientation.get());
    }

    private void cancelRotationArrowHide() {
        rotationArrowTimer.cancel();
    }

    private void scheduleRotationArrowHide() {
        if (!rotationArrowDragging) {
            rotationArrowTimer.cancel();
            rotationArrowTimer = new Timer();
            rotationArrowTimer.schedule(TimerTaskUtils.wrap(() -> playerLayerModel.showRotationArrow.set(false)), ROTATION_ARROW_HIDE_DELAY);
        }
    }
}
