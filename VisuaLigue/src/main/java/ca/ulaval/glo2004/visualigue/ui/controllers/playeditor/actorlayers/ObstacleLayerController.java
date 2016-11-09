package ca.ulaval.glo2004.visualigue.ui.controllers.playeditor.actorlayers;

import ca.ulaval.glo2004.visualigue.ui.controllers.playeditor.scene.scene2d.ActorLayerController;
import ca.ulaval.glo2004.visualigue.ui.models.layers.ActorLayerModel;
import ca.ulaval.glo2004.visualigue.ui.models.layers.ObstacleLayerModel;
import ca.ulaval.glo2004.visualigue.utils.FilenameUtils;
import ca.ulaval.glo2004.visualigue.utils.geometry.Vector2;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class ObstacleLayerController extends ActorLayerController {

    public static final String VIEW_NAME = "/views/playeditor/actorlayers/obstacle-layer.fxml";
    @FXML private ImageView imageView;
    private ObstacleLayerModel obstacleLayerModel;
    private ChangeListener<Object> onChange = this::onChange;

    @Override
    public void init(ActorLayerModel layerModel) {
        this.obstacleLayerModel = (ObstacleLayerModel) layerModel;
        setImage();
        addListeners();
        update();
    }

    private void setImage() {
        if (obstacleLayerModel.imagePathName.isNotEmpty().get()) {
            imageView.setImage(new Image(FilenameUtils.getURIString(obstacleLayerModel.imagePathName.get())));
        } else if (obstacleLayerModel.builtInImagePathName.isNotEmpty().get()) {
            imageView.setImage(new Image(obstacleLayerModel.builtInImagePathName.get()));
        }
    }

    private void addListeners() {
        obstacleLayerModel.position.addListener(onChange);
        settings.resizeActorsOnZoomProperty.addListener(onChange);
        zoomProperty.addListener(onChange);
        actorButton.layoutReadyProperty().addListener(this::onChange);
    }

    @Override
    public void clean() {
        settings.resizeActorsOnZoomProperty.removeListener(onChange);
        zoomProperty.removeListener(onChange);
    }

    private void onChange(final ObservableValue<? extends Object> value, final Object oldPropertyValue, final Object newPropertyValue) {
        update();
    }

    @Override
    public void update() {
        Vector2 actorPosition;
        if (obstacleLayerModel.position.isNotNull().get()) {
            actorPosition = playingSurfaceLayerController.sizeRelativeToSurfacePoint(obstacleLayerModel.position.get());
        } else {
            actorPosition = null;
        }
        Platform.runLater(() -> {
            updateActor(actorPosition);
        });
    }

    private void updateActor(Vector2 actorPosition) {
        if (actorPosition != null) {
            actorButton.setScaleX(getScaledValue(1.0));
            actorButton.setScaleY(getScaledValue(1.0));
            actorButton.setLayoutX(actorPosition.getX() - actorButton.getWidth() / 2);
            actorButton.setLayoutY(actorPosition.getY() - actorButton.getHeight() / 2);
        }
        actorButton.setVisible(actorPosition != null);
    }

    @FXML
    protected void onMouseDragged(MouseEvent e) {
        obstacleLayerModel.position.set(playingSurfaceLayerController.getSizeRelativeMousePosition(true));
    }

    @FXML
    protected void onMouseReleased(MouseEvent e) {
        playService.updateObstacleActorPosition(playModel.getUUID(), frameModel.time.get(), obstacleLayerModel.getUUID(), obstacleLayerModel.position.get());
    }

}
