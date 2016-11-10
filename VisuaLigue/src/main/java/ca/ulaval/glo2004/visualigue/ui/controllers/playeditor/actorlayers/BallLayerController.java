package ca.ulaval.glo2004.visualigue.ui.controllers.playeditor.actorlayers;

import ca.ulaval.glo2004.visualigue.ui.controllers.playeditor.scene.scene2d.ActorLayerController;
import ca.ulaval.glo2004.visualigue.ui.models.layers.ActorLayerModel;
import ca.ulaval.glo2004.visualigue.ui.models.layers.BallLayerModel;
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

public class BallLayerController extends ActorLayerController {

    public static final String VIEW_NAME = "/views/playeditor/actorlayers/ball-layer.fxml";
    @FXML private ImageView imageView;
    private BallLayerModel ballLayerModel;
    private ChangeListener<Object> onChange = this::onChange;

    @Override
    public void init(ActorLayerModel layerModel) {
        this.ballLayerModel = (BallLayerModel) layerModel;
        setImage();
        addListeners();
        update();
    }

    private void setImage() {
        if (ballLayerModel.imagePathName.isNotEmpty().get()) {
            imageView.setImage(new Image(FilenameUtils.getURIString(ballLayerModel.imagePathName.get())));
        } else if (ballLayerModel.builtInImagePathName.isNotEmpty().get()) {
            imageView.setImage(new Image(ballLayerModel.builtInImagePathName.get()));
        }
    }

    private void addListeners() {
        ballLayerModel.position.addListener(onChange);
        settings.resizeActorsOnZoomProperty.addListener(onChange);
        zoomProperty.addListener(onChange);
        actorButton.layoutReadyProperty().addListener(onChange);
    }

    @Override
    public void clean() {
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
        if (ballLayerModel.position.isNotNull().get()) {
            actorPosition = playingSurfaceLayerController.sizeRelativeToSurfacePoint(ballLayerModel.position.get());
        } else {
            actorPosition = null;
        }
        Platform.runLater(() -> {
            updateActor(actorPosition);
        });
    }

    private void updateActor(Vector2 actorPosition) {
        Boolean showActor = actorPosition != null;
        if (showActor) {
            actorButton.setScaleX(getScaledValue(1.0));
            actorButton.setScaleY(getScaledValue(1.0));
            actorButton.setLayoutX(actorPosition.getX() - actorButton.getWidth() / 2);
            actorButton.setLayoutY(actorPosition.getY() - actorButton.getHeight() / 2);
        }
        actorButton.setVisible(showActor);
        actorButton.setCursor(layerModel.isLocked.get() ? Cursor.DEFAULT : Cursor.MOVE);
    }

    @FXML
    protected void onDragDetected(MouseEvent e) {
        actorButton.setMouseTransparent(true);
        DragUtils.setSource(ballLayerModel);
        actorButton.startFullDrag();
    }

    @FXML
    protected void onMouseDragged(MouseEvent e) {
        ballLayerModel.position.set(playingSurfaceLayerController.getSizeRelativeMousePosition(true));
    }

    @FXML
    protected void onMouseReleased(MouseEvent e) {
        DragUtils.clearSource();
        actorButton.setMouseTransparent(false);
        playService.updateBallActorPosition(playModel.getUUID(), frameModel.time.get(), ballLayerModel.getUUID(), ballLayerModel.position.get());
    }

}
