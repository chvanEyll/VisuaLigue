package ca.ulaval.glo2004.visualigue.ui.controllers.playeditor.actor;

import ca.ulaval.glo2004.visualigue.ui.controllers.playeditor.scene.scene2d.ActorController;
import ca.ulaval.glo2004.visualigue.ui.models.actors.ActorModel;
import ca.ulaval.glo2004.visualigue.ui.models.actors.ObstacleActorModel;
import ca.ulaval.glo2004.visualigue.utils.FilenameUtils;
import ca.ulaval.glo2004.visualigue.utils.geometry.Vector2;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class ObstacleController extends ActorController {

    public static final String VIEW_NAME = "/views/playeditor/actor/obstacle-actor.fxml";
    @FXML private ImageView imageView;
    private ObstacleActorModel obstacleActorModel;
    private ChangeListener<Object> onChange = this::onChange;

    @Override
    public void init(ActorModel actorModel) {
        this.obstacleActorModel = (ObstacleActorModel) actorModel;
        setImage();
        addListeners();
        render();
    }

    private void setImage() {
        if (obstacleActorModel.imagePathName.isNotEmpty().get()) {
            imageView.setImage(new Image(FilenameUtils.getURIString(obstacleActorModel.imagePathName.get())));
        } else if (obstacleActorModel.builtInImagePathName.isNotEmpty().get()) {
            imageView.setImage(new Image(obstacleActorModel.builtInImagePathName.get()));
        }
    }

    private void addListeners() {
        obstacleActorModel.position.addListener(onChange);
        sceneController.settings.resizeActorsOnZoomProperty.addListener(onChange);
        sceneController.zoomProperty().addListener(onChange);
        actorButton.layoutReadyProperty().addListener(this::onChange);
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
        actorButton.setScaleX(getScaledValue(1.0));
        actorButton.setScaleY(getScaledValue(1.0));
        actorButton.setLayoutX(actorPixelPosition.getX() - actorButton.getWidth() / 2);
        actorButton.setLayoutY(actorPixelPosition.getY() - actorButton.getHeight() / 2);
        actorButton.setCursor(actorModel.isLocked.get() ? Cursor.DEFAULT : Cursor.MOVE);
        actorButton.setVisible(true);
    }

    @FXML
    protected void onMouseDragged(MouseEvent e) {
        obstacleActorModel.position.set(sceneController.getMouseWorldPosition(true));
    }

    @FXML
    protected void onMouseReleased(MouseEvent e) {
        playService.beginUpdate(sceneController.getPlayUUID());
        playService.updateObstaclPosition(sceneController.getPlayUUID(), sceneController.getTime(), obstacleActorModel.getUUID(), obstacleActorModel.position.get());
        playService.endUpdate(sceneController.getPlayUUID());
    }

}
