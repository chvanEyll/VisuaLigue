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
        actorModel.position.addListener(this::onActorPositionChanged);
        update();
    }

    private void onActorPositionChanged(final ObservableValue<? extends Vector2> value, final Vector2 oldPropertyValue, final Vector2 newPropertyValue) {
        update();
    }

    @Override
    public void update() {
        Vector2 actorPosition = playingSurfaceLayerController.relativeToSurfacePoint(actorModel.position.get());
        Platform.runLater(() -> {
            updateActor(actorPosition);
        });
    }

    private void updateActor(Vector2 actorPosition) {
        actorButton.setScaleX(getScaledValue(1.0));
        actorButton.setScaleY(getScaledValue(1.0));
        actorButton.setLayoutX(actorPosition.getX() - actorButton.getWidth() / 2);
        actorButton.setLayoutY(actorPosition.getY() - actorButton.getHeight() / 2);
    }

    @Override
    public void updateZoom(Zoom zoom) {
        this.zoom = zoom;
        update();
    }

}
