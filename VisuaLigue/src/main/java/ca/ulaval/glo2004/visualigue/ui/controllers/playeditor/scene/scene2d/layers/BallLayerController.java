package ca.ulaval.glo2004.visualigue.ui.controllers.playeditor.scene.scene2d.layers;

import ca.ulaval.glo2004.visualigue.ui.controllers.playeditor.scene.Zoom;
import ca.ulaval.glo2004.visualigue.ui.models.ActorModel;
import ca.ulaval.glo2004.visualigue.utils.FilenameUtils;
import ca.ulaval.glo2004.visualigue.utils.geometry.Vector2;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class BallLayerController extends ActorLayerController {

    public static final String VIEW_NAME = "/views/playeditor/scene2d/layers/ball-layer.fxml";
    @FXML private ImageView imageView;
    private ChangeListener<Object> onChange = this::onChange;

    @Override
    public void init(ActorModel actorModel, PlayingSurfaceLayerController playingLayerSurfaceController, ObjectProperty<Zoom> zoomProperty, BooleanProperty showActorLabelsProperty, BooleanProperty showMovementArrowsProperty, BooleanProperty resizeActorsOnZoomProperty) {
        super.init(actorModel, playingLayerSurfaceController, zoomProperty, showActorLabelsProperty, showMovementArrowsProperty, resizeActorsOnZoomProperty);
        if (actorModel.imagePathName.isNotEmpty().get()) {
            imageView.setImage(new Image(FilenameUtils.getURIString(actorModel.imagePathName.get())));
        } else if (actorModel.builtInImagePathName.isNotEmpty().get()) {
            imageView.setImage(new Image(actorModel.builtInImagePathName.get()));
        }
        actorModel.position.addListener(onChange);
        resizeActorsOnZoomProperty.addListener(onChange);
        zoomProperty.addListener(onChange);
        actorButton.layoutReadyProperty().addListener(this::onChange);
        update();
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
        if (actorModel.position.isNotNull().get()) {
            actorPosition = playingSurfaceLayerController.relativeToSurfacePoint(actorModel.position.get());
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
