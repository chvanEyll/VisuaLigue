package ca.ulaval.glo2004.visualigue.ui.controllers.playeditor.scene.scene2d.layers;

import ca.ulaval.glo2004.visualigue.ui.controllers.playeditor.scene.Zoom;
import ca.ulaval.glo2004.visualigue.ui.models.ActorModel;
import ca.ulaval.glo2004.visualigue.utils.FilenameUtils;
import ca.ulaval.glo2004.visualigue.utils.geometry.Vector2;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ObstacleLayerController extends ActorLayerController {

    public static final String VIEW_NAME = "/views/playeditor/scene2d/layers/obstacle-layer.fxml";
    @FXML private ImageView imageView;

    @Override
    public void init(ActorModel actorModel, PlayingSurfaceLayerController playingLayerSurfaceController) {
        super.init(actorModel, playingLayerSurfaceController);
        if (actorModel.imagePathName.isNotEmpty().get()) {
            imageView.setImage(new Image(FilenameUtils.getURIString(actorModel.imagePathName.get())));
        } else if (actorModel.builtInImagePathName.isNotEmpty().get()) {
            imageView.setImage(new Image(actorModel.builtInImagePathName.get()));
        }
        actorModel.x.addListener(this::onActorPositionChanged);
        actorModel.y.addListener(this::onActorPositionChanged);
        actorModel.label.addListener(this::onActorDescriptionChanged);
        update();
    }

    private void onActorPositionChanged(final ObservableValue<? extends Number> value, final Number oldPropertyValue, final Number newPropertyValue) {
        update();
    }

    private void onActorDescriptionChanged(final ObservableValue<? extends String> value, final String oldPropertyValue, final String newPropertyValue) {
        update();
    }

    @Override
    public void update() {
        Platform.runLater(() -> {
            Vector2 actorLocation = new Vector2(actorModel.x.get(), actorModel.y.get());
            Vector2 surfacePoint = playingSurfaceLayerController.relativeToSurfacePoint(actorLocation);
            updateActor(surfacePoint);
        });
    }

    private void updateActor(Vector2 surfacePoint) {
        actorButton.setScaleX(getScaledValue(1.0));
        actorButton.setScaleY(getScaledValue(1.0));
        actorButton.setLayoutX(surfacePoint.getX() - actorButton.getWidth() / 2);
        actorButton.setLayoutY(surfacePoint.getY() - actorButton.getHeight() / 2);
    }

    @Override
    public void updateZoom(Zoom zoom) {
        this.zoom = zoom;
        update();
    }

}
