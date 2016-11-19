package ca.ulaval.glo2004.visualigue.ui.controllers.playeditor.actor;

import ca.ulaval.glo2004.visualigue.ui.controllers.common.Arrow;
import ca.ulaval.glo2004.visualigue.ui.controllers.common.ExtendedButton;
import ca.ulaval.glo2004.visualigue.ui.controllers.common.ExtendedLabel;
import ca.ulaval.glo2004.visualigue.ui.controllers.common.PlayerIcon;
import ca.ulaval.glo2004.visualigue.ui.controllers.playeditor.scene.scene2d.ActorController;
import ca.ulaval.glo2004.visualigue.ui.models.actors.ActorModel;
import ca.ulaval.glo2004.visualigue.ui.models.actors.BallActorModel;
import ca.ulaval.glo2004.visualigue.ui.models.actors.PlayerActorModel;
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

public class PlayerController extends ActorController {

    public static final String VIEW_NAME = "/views/playeditor/actor/player-actor.fxml";
    private static final Double LABEL_OFFSET_Y = 30.0;
    private static final Vector2 ROTATION_ARROW_OFFSET = new Vector2(30, -30);
    private static final Integer ROTATION_ARROW_HIDE_DELAY = 500;
    private static final Double BASE_BUTTON_SCALING = 1.25;
    private static final Double ARROW_HEAD_SIZE = 15.0;
    private static final Double ARROW_STROKE_DASH_ARRAY_SIZE = 10.0;
    private PlayerActorModel playerActorModel;
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
    public void init(ActorModel actorModel) {
        this.playerActorModel = (PlayerActorModel) actorModel;
        label.textProperty().bind(playerActorModel.label);
        addListeners();
        render();
    }

    private void addListeners() {
        playerActorModel.position.addListener(onChange);
        playerActorModel.nextPosition.addListener(onChange);
        playerActorModel.orientation.addListener(onChange);
        playerActorModel.label.addListener(onChange);
        playerActorModel.showRotationArrow.addListener(onChange);
        playerActorModel.snappedBallUUID.addListener(onChange);
        sceneController.settings.showActorLabelsProperty.addListener(onChange);
        sceneController.settings.showMovementArrowsProperty.addListener(onChange);
        sceneController.settings.resizeActorsOnZoomProperty.addListener(onChange);
        sceneController.zoomProperty().addListener(onChange);
        actorButton.layoutReadyProperty().addListener(this::onChange);
        label.layoutReadyProperty().addListener(this::onChange);
    }

    @Override
    public void clean() {
        sceneController.settings.showActorLabelsProperty.removeListener(onChange);
        sceneController.settings.showMovementArrowsProperty.removeListener(onChange);
        sceneController.settings.resizeActorsOnZoomProperty.removeListener(onChange);
        sceneController.zoomProperty().removeListener(onChange);
        super.clean();
    }

    private void onChange(final ObservableValue<? extends Object> value, final Object oldPropertyValue, final Object newPropertyValue) {
        render();
    }

    @Override
    public void render() {
        Platform.runLater(() -> {
            renderActorButton();
            renderDirectionArrow();
            renderLabel();
            renderRotationArrow();
        });
    }

    private void renderActorButton() {
        Vector2 actorPixelPosition = sceneController.worldToPixelPoint(actorModel.position.get());
        actorButton.setScaleX(getScaledValue(BASE_BUTTON_SCALING));
        actorButton.setScaleY(getScaledValue(BASE_BUTTON_SCALING));
        actorButton.setLayoutX(actorPixelPosition.getX() - actorButton.getWidth() / 2);
        actorButton.setLayoutY(actorPixelPosition.getY() - actorButton.getHeight() / 2);
        actorButton.setRotate(-playerActorModel.orientation.get());
        actorButton.setCursor(actorModel.isLocked.get() ? Cursor.DEFAULT : Cursor.MOVE);
        actorButton.setVisible(true);
        playerIcon.setColor(playerActorModel.color.get());
    }

    private void renderDirectionArrow() {
        Vector2 currentActorPixelPosition = sceneController.worldToPixelPoint(actorModel.position.get());
        Vector2 nextActorPixelPosition = playerActorModel.nextPosition.isNotNull().get() ? sceneController.worldToPixelPoint(playerActorModel.nextPosition.get()) : null;
        Boolean showArrow = sceneController.settings.showMovementArrowsProperty.get() == true && nextActorPixelPosition != null;
        if (showArrow) {
            arrow.setStroke(playerActorModel.color.get());
            arrow.setStrokeWidth(getScaledValue(3.0));
            arrow.getStrokeDashArray().setAll(getScaledValue(ARROW_STROKE_DASH_ARRAY_SIZE));
            arrow.setArrowFill(playerActorModel.color.get());
            arrow.setHeadSize(new Vector2(getScaledValue(ARROW_HEAD_SIZE), getScaledValue(ARROW_HEAD_SIZE)));
            arrow.setTailGrow(-actorButton.getWidth() * actorButton.getScaleX());
            arrow.setHeadGrow(-actorButton.getWidth() * actorButton.getScaleX());
            arrow.setHeadLocation(nextActorPixelPosition);
            arrow.setTailLocation(currentActorPixelPosition);
        }
        arrow.setVisible(showArrow);
    }

    private void renderLabel() {
        Vector2 actorPixelPosition = sceneController.worldToPixelPoint(actorModel.position.get());
        Boolean showLabel = playerActorModel.showLabel.get() && sceneController.settings.showActorLabelsProperty.get();
        if (showLabel) {
            label.setScaleX(getScaledValue(1.0));
            label.setScaleY(getScaledValue(1.0));
            label.setLayoutX(actorPixelPosition.getX() - label.getWidth() / 2);
            label.setLayoutY(actorPixelPosition.getY() - label.getHeight() / 2 - getScaledValue(LABEL_OFFSET_Y));
        }
        label.setVisible(showLabel);
    }

    private void renderRotationArrow() {
        Vector2 actorPixelPosition = sceneController.worldToPixelPoint(actorModel.position.get());
        Boolean showArrow = playerActorModel.showRotationArrow.get();
        if (showArrow) {
            rotationArrow.setScaleX(getScaledValue(1.0));
            rotationArrow.setScaleY(getScaledValue(1.0));
            rotationArrow.setLayoutX(actorPixelPosition.getX() - rotationArrow.getWidth() / 2 + getScaledValue(ROTATION_ARROW_OFFSET.getX()));
            rotationArrow.setLayoutY(actorPixelPosition.getY() - rotationArrow.getHeight() / 2 + getScaledValue(ROTATION_ARROW_OFFSET.getY()));
        }
        rotationArrow.setVisible(showArrow);
    }

    @FXML
    protected void onMouseDragEntered(MouseEvent e) {
        if (DragUtils.getSource() instanceof BallActorModel) {
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
        if (DragUtils.getSource() instanceof BallActorModel) {
            DragUtils.setResult(true);
            playerActorModel.snappedBallUUID.set(((BallActorModel) DragUtils.getSource()).getUUID());
            playService.beginUpdate(sceneController.getPlayUUID());
            playService.snapBallToPlayer(sceneController.getPlayUUID(), sceneController.getTime(), playerActorModel.getUUID(), playerActorModel.snappedBallUUID.get());
            updateSnappedBallPosition();
            playService.endUpdate(sceneController.getPlayUUID());
        }
    }

    @FXML
    protected void onMouseEntered(MouseEvent e) {
        playerActorModel.showRotationArrow.set(true);
    }

    @FXML
    protected void onMouseExited(MouseEvent e) {
        scheduleRotationArrowHide();
    }

    @FXML
    protected void onMouseDragged(MouseEvent e) {
        playerActorModel.position.set(sceneController.getMouseWorldPosition(true));
    }

    @FXML
    protected void onMouseReleased(MouseEvent e) {
        playService.beginUpdate(sceneController.getPlayUUID());
        playService.updatePlayerPositionDirect(sceneController.getPlayUUID(), sceneController.getTime(), playerActorModel.getUUID(), playerActorModel.position.get());
        updateSnappedBallPosition();
        playService.endUpdate(sceneController.getPlayUUID());
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
        Vector2 mousePosition = sceneController.getMousePixelPosition();
        initialRotationAngle = new Line(sceneController.worldToPixelPoint(playerActorModel.position.get()), mousePosition).getAngle();
        initialPlayerOrientation = playerActorModel.orientation.get();
    }

    @FXML
    protected void onRotateArrowMouseDragged(MouseEvent e) {
        Vector2 mousePosition = sceneController.getMousePixelPosition();
        Double newAngle = new Line(sceneController.worldToPixelPoint(playerActorModel.position.get()), mousePosition).getAngle();
        playerActorModel.orientation.set(initialPlayerOrientation + (newAngle - initialRotationAngle));
        rotationArrowDragging = true;
    }

    @FXML
    protected void onRotateArrowMouseReleased(MouseEvent e) {
        rotationArrowDragging = false;
        scheduleRotationArrowHide();
        playService.beginUpdate(sceneController.getPlayUUID());
        playService.updatePlayerOrientation(sceneController.getPlayUUID(), sceneController.getTime(), playerActorModel.getUUID(), playerActorModel.orientation.get());
        updateSnappedBallPosition();
        playService.endUpdate(sceneController.getPlayUUID());
    }

    private void cancelRotationArrowHide() {
        rotationArrowTimer.cancel();
    }

    private void scheduleRotationArrowHide() {
        if (!rotationArrowDragging) {
            rotationArrowTimer.cancel();
            rotationArrowTimer = new Timer();
            rotationArrowTimer.schedule(TimerTaskUtils.wrap(() -> playerActorModel.showRotationArrow.set(false)), ROTATION_ARROW_HIDE_DELAY);
        }
    }

    private void updateSnappedBallPosition() {
        if (playerActorModel.snappedBallUUID.isNotNull().get()) {
            BallActorModel snappedBallActorModel = (BallActorModel) sceneController.findActor(playerActorModel.snappedBallUUID.get(), 0);
            playService.updateBallPosition(sceneController.getPlayUUID(), sceneController.getTime(), snappedBallActorModel.getUUID(), getSnappedBallPosition());
        }
    }

    private Vector2 getSnappedBallPosition() {
        Vector2 actorPosition = sceneController.worldToPixelPoint(actorModel.position.get());
        Vector2 snappedPosition = actorPosition.offset(new Vector2(getScaledValue(30.0), 0)).rotateCenter(actorPosition, playerActorModel.orientation.get());
        return sceneController.pixelToWorldPoint(snappedPosition);
    }

}
