package ca.ulaval.glo2004.visualigue.ui.controllers.playeditor.actorlayers;

import ca.ulaval.glo2004.visualigue.ui.controllers.common.Arrow;
import ca.ulaval.glo2004.visualigue.ui.controllers.common.ExtendedLabel;
import ca.ulaval.glo2004.visualigue.ui.controllers.common.PlayerIcon;
import ca.ulaval.glo2004.visualigue.ui.controllers.playeditor.scene.scene2d.ActorLayerController;
import ca.ulaval.glo2004.visualigue.ui.models.actors.ActorModel;
import ca.ulaval.glo2004.visualigue.ui.models.actors.PlayerActorModel;
import ca.ulaval.glo2004.visualigue.utils.geometry.Vector2;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;

public class PlayerLayerController extends ActorLayerController {

    private static final Double LABEL_OFFSET_Y = 30.0;
    public static final String VIEW_NAME = "/views/playeditor/actorlayers/player-layer.fxml";
    public static final Double BASE_BUTTON_SCALING = 1.25;
    public static final Double ARROW_HEAD_SIZE = 15.0;
    public static final Double ARROW_STROKE_DASH_ARRAY_SIZE = 10.0;
    private PlayerActorModel playerActorModel;
    private ChangeListener<Object> onChange = this::onChange;
    @FXML private PlayerIcon playerIcon;
    @FXML private ExtendedLabel label;
    @FXML private Arrow arrow;

    @Override
    public void init(ActorModel actorModel) {
        this.playerActorModel = (PlayerActorModel) actorModel;
        label.textProperty().bind(playerActorModel.label);
        addListeners();
        update();
    }

    private void addListeners() {
        playerActorModel.position.addListener(onChange);
        playerActorModel.nextPosition.addListener(onChange);
        playerActorModel.orientation.addListener(onChange);
        playerActorModel.label.addListener(onChange);
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
    }

    private void onChange(final ObservableValue<? extends Object> value, final Object oldPropertyValue, final Object newPropertyValue) {
        update();
    }

    @Override
    public void update() {
        Vector2 actorPosition;
        if (playerActorModel.position.isNotNull().get()) {
            actorPosition = playingSurfaceLayerController.sizeRelativeToSurfacePoint(playerActorModel.position.get());
        } else {
            actorPosition = null;
        }
        Vector2 nextActorPosition;
        if (playerActorModel.nextPosition.isNotNull().get()) {
            nextActorPosition = playingSurfaceLayerController.sizeRelativeToSurfacePoint(playerActorModel.nextPosition.get());
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
        if (actorPosition != null) {
            actorButton.setScaleX(getScaledValue(BASE_BUTTON_SCALING));
            actorButton.setScaleY(getScaledValue(BASE_BUTTON_SCALING));
            actorButton.setLayoutX(actorPosition.getX() - actorButton.getWidth() / 2);
            actorButton.setLayoutY(actorPosition.getY() - actorButton.getHeight() / 2);
            actorButton.setRotate(playerActorModel.orientation.get());
            playerIcon.setColor(playerActorModel.color.get());
        }
        actorButton.setVisible(actorPosition != null);
    }

    private void updateArrow(Vector2 currentActorPosition, Vector2 nextActorPosition) {
        if (settings.showMovementArrowsProperty.get() == true && nextActorPosition != null) {
            arrow.setStroke(playerActorModel.color.get());
            arrow.setStrokeWidth(getScaledValue(3.0));
            arrow.getStrokeDashArray().setAll(getScaledValue(ARROW_STROKE_DASH_ARRAY_SIZE));
            arrow.setArrowFill(playerActorModel.color.get());
            arrow.setHeadSize(new Vector2(getScaledValue(ARROW_HEAD_SIZE), getScaledValue(ARROW_HEAD_SIZE)));
            arrow.setTailGrow(-actorButton.getWidth() * actorButton.getScaleX());
            arrow.setHeadLocation(nextActorPosition);
            arrow.setTailLocation(currentActorPosition);
        }
        arrow.setVisible(settings.showMovementArrowsProperty.get() == true && nextActorPosition != null);
    }

    private void updateLabel(Vector2 actorPosition) {
        if (actorPosition != null) {
            label.setScaleX(getScaledValue(1.0));
            label.setScaleY(getScaledValue(1.0));
            label.setLayoutX(actorPosition.getX() - label.getWidth() / 2);
            label.setLayoutY(actorPosition.getY() - label.getHeight() / 2 - getScaledValue(LABEL_OFFSET_Y));
        }
        label.setVisible(actorPosition != null && settings.showActorLabelsProperty.get());
    }

    @FXML
    protected void onMouseDragged(MouseEvent e) {
        playerActorModel.position.set(playingSurfaceLayerController.getSizeRelativeMousePosition(true));
    }

    @FXML
    protected void onMouseReleased(MouseEvent e) {
        Vector2 position = playingSurfaceLayerController.getSizeRelativeMousePosition(true);
        playService.updatePlayerActorPositionDirect(playModel.getUUID(), frameModel.time.get(), playerActorModel.getUUID(), position);
    }

}
