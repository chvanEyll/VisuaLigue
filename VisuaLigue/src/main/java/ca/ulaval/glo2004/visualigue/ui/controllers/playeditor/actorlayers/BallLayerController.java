package ca.ulaval.glo2004.visualigue.ui.controllers.playeditor.actorlayers;

import ca.ulaval.glo2004.visualigue.ui.controllers.playeditor.scene.scene2d.ActorLayerController;
import ca.ulaval.glo2004.visualigue.ui.models.actors.ActorModel;
import ca.ulaval.glo2004.visualigue.ui.models.actors.BallActorModel;
import ca.ulaval.glo2004.visualigue.utils.FilenameUtils;
import ca.ulaval.glo2004.visualigue.utils.geometry.Vector2;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class BallLayerController extends ActorLayerController {

    public static final String VIEW_NAME = "/views/playeditor/actorlayers/ball-layer.fxml";
    @FXML private ImageView imageView;
    private BallActorModel ballActorModel;
    private ChangeListener<Object> onChange = this::onChange;

    @Override
    public void init(ActorModel actorModel) {
        this.ballActorModel = (BallActorModel) actorModel;
        setImage();
        addListeners();
        update();
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
        resizeActorsOnZoomProperty.addListener(onChange);
        zoomProperty.addListener(onChange);
        actorButton.layoutReadyProperty().addListener(onChange);
    }

    @Override
    public void clean() {
        resizeActorsOnZoomProperty.removeListener(onChange);
        zoomProperty.removeListener(onChange);
    }

    private void onChange(final ObservableValue<? extends Object> value, final Object oldPropertyValue, final Object newPropertyValue) {
        update();
    }

    @Override
    public void update() {
        Vector2 actorPosition;
        if (ballActorModel.position.isNotNull().get()) {
            actorPosition = playingSurfaceLayerController.sizeRelativeToSurfacePoint(ballActorModel.position.get());
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

}
