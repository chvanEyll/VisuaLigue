package ca.ulaval.glo2004.visualigue.ui.controllers.playeditor.actor;

import ca.ulaval.glo2004.visualigue.ui.controllers.playeditor.scene.scene2d.ActorController;
import ca.ulaval.glo2004.visualigue.ui.models.actors.ActorModel;
import ca.ulaval.glo2004.visualigue.ui.models.actors.BallActorModel;
import ca.ulaval.glo2004.visualigue.utils.FilenameUtils;
import ca.ulaval.glo2004.visualigue.utils.geometry.Vector2;
import ca.ulaval.glo2004.visualigue.utils.javafx.DragUtils;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class BallController extends ActorController {

    public static final String VIEW_NAME = "/views/playeditor/actor/ball-actor.fxml";
    @FXML private ImageView imageView;
    private BallActorModel ballActorModel;
    private ChangeListener<Object> onChange = this::onChange;

    @Override
    public void init(ActorModel actorModel) {
        this.ballActorModel = (BallActorModel) actorModel;
        setImage();
        addListeners();
        render();
    }

    private void setImage() {
        if (ballActorModel.imagePathName.isNotEmpty().get()) {
            imageView.setImage(new Image(FilenameUtils.getURIString(ballActorModel.imagePathName.get())));
        } else if (ballActorModel.builtInImagePathName.isNotEmpty().get()) {
            imageView.setImage(new Image(ballActorModel.builtInImagePathName.get()));
        }
    }

    private void addListeners() {
        ballActorModel.position.addListener(onChange);
        sceneController.settings.resizeActorsOnZoomProperty.addListener(onChange);
        sceneController.zoomProperty().addListener(onChange);
        actorButton.layoutReadyProperty().addListener(onChange);
    }

    @Override
    public void clean() {
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
        });
    }

    private void renderActorButton() {
        Vector2 actorPixelPosition = sceneController.worldToPixelPoint(actorModel.position.get());
        Boolean showActor = actorPixelPosition != null;
        if (showActor) {
            actorButton.setScaleX(getScaledValue(1.0));
            actorButton.setScaleY(getScaledValue(1.0));
            actorButton.setLayoutX(actorPixelPosition.getX() - actorButton.getWidth() / 2);
            actorButton.setLayoutY(actorPixelPosition.getY() - actorButton.getHeight() / 2);
        }
        actorButton.setVisible(showActor);
        actorButton.setCursor(actorModel.isLocked.get() ? Cursor.DEFAULT : Cursor.MOVE);
    }

    @FXML
    protected void onDragDetected(MouseEvent e) {
        actorButton.setMouseTransparent(true);
        DragUtils.setSource(ballActorModel);
        actorButton.startFullDrag();
    }

    @FXML
    protected void onMouseDragged(MouseEvent e) {
        ballActorModel.position.set(sceneController.getMouseWorldPosition(true));
    }

    @FXML
    protected void onMouseReleased(MouseEvent e) {
        DragUtils.clearSource();
        actorButton.setMouseTransparent(false);
        if (!DragUtils.dragSucceeded()) {
            playService.beginUpdate(sceneController.getPlayUUID());
            if (ballActorModel.playerOwnerUUID.isNotNull().get()) {
                playService.unsnapBallFromPlayer(sceneController.getPlayUUID(), sceneController.getTime(), ballActorModel.getUUID(), ballActorModel.playerOwnerUUID.get());
            }
            playService.updateBallPosition(sceneController.getPlayUUID(), sceneController.getTime(), ballActorModel.getUUID(), ballActorModel.position.get());
            playService.endUpdate(sceneController.getPlayUUID());
        }
    }

}
