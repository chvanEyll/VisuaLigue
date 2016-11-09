package ca.ulaval.glo2004.visualigue.ui.controllers.playeditor.actorlayers;

import ca.ulaval.glo2004.visualigue.ui.controllers.common.Arrow;
import ca.ulaval.glo2004.visualigue.ui.controllers.common.ExtendedLabel;
import ca.ulaval.glo2004.visualigue.ui.controllers.common.PlayerIcon;
import ca.ulaval.glo2004.visualigue.ui.controllers.playeditor.scene.scene2d.ActorLayerController;
import ca.ulaval.glo2004.visualigue.ui.models.layers.ActorLayerModel;
import ca.ulaval.glo2004.visualigue.ui.models.layers.PlayerLayerModel;
import ca.ulaval.glo2004.visualigue.utils.geometry.Vector2;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.input.MouseEvent;

public class PlayerLayerController extends ActorLayerController {

    private static final Double LABEL_OFFSET_Y = 30.0;
    public static final String VIEW_NAME = "/views/playeditor/actorlayers/player-layer.fxml";
    public static final Double BASE_BUTTON_SCALING = 1.25;
    public static final Double ARROW_HEAD_SIZE = 15.0;
    public static final Double ARROW_STROKE_DASH_ARRAY_SIZE = 10.0;
    private PlayerLayerModel playerLayerModel;
    private ChangeListener<Object> onChange = this::onChange;
    @FXML private PlayerIcon playerIcon;
    @FXML private ExtendedLabel label;
    @FXML private Arrow arrow;

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
        });
    }

    private void updateActor(Vector2 actorPosition) {
        Boolean showActor = actorPosition != null;
        if (showActor) {
            actorButton.setScaleX(getScaledValue(BASE_BUTTON_SCALING));
            actorButton.setScaleY(getScaledValue(BASE_BUTTON_SCALING));
            actorButton.setLayoutX(actorPosition.getX() - actorButton.getWidth() / 2);
            actorButton.setLayoutY(actorPosition.getY() - actorButton.getHeight() / 2);
            actorButton.setRotate(playerLayerModel.orientation.get());
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

    @FXML
    protected void onMouseDragged(MouseEvent e) {
        playerLayerModel.position.set(playingSurfaceLayerController.getSizeRelativeMousePosition(true));
    }

    @FXML
    protected void onMouseReleased(MouseEvent e) {
        playService.updatePlayerActorPositionDirect(playModel.getUUID(), frameModel.time.get(), playerLayerModel.getUUID(), playerLayerModel.position.get());
    }

}
