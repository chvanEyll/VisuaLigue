package ca.ulaval.glo2004.visualigue.ui.controllers.playeditor.scene.scene2d.layers;

import ca.ulaval.glo2004.visualigue.ui.controllers.common.SvgImage;
import ca.ulaval.glo2004.visualigue.ui.controllers.playeditor.scene.Zoom;
import ca.ulaval.glo2004.visualigue.ui.models.ActorModel;
import ca.ulaval.glo2004.visualigue.utils.geometry.Vector2;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;

public class PlayerLayerController extends ActorLayerController {

    public static final String VIEW_NAME = "/views/playeditor/scene2d/layers/player-layer.fxml";
    public static final Double BASE_BUTTON_SCALING = 1.5;
    public static final Double LABEL_OFFSET_Y = 15.0;
    @FXML private Button actorButton;
    @FXML private Label categoryLabel;
    @FXML private SvgImage svgImage;
    @FXML private Tooltip tooltip;
    private Boolean showLabel = false;

    @Override
    public void init(ActorModel actorModel, PlayingSurfaceLayerController playingLayerSurfaceController) {
        super.init(actorModel, playingLayerSurfaceController);
        svgImage.setUrl(actorModel.svgImagePathName.get());
        tooltip.textProperty().bind(actorModel.hoverText);
        categoryLabel.textProperty().bind(actorModel.label);
        actorModel.x.addListener(this::onActorPositionChanged);
        actorModel.y.addListener(this::onActorPositionChanged);
        actorModel.orientation.addListener(this::onActorPositionChanged);
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
        Vector2 actorLocation = new Vector2(actorModel.x.get(), actorModel.y.get());
        Vector2 surfacePoint = playingSurfaceLayerController.relativeToSurfacePoint(actorLocation);
        Platform.runLater(() -> {
            actorButton.setScaleX(getScaledValue(1.0) * BASE_BUTTON_SCALING);
            actorButton.setScaleY(getScaledValue(1.0) * BASE_BUTTON_SCALING);
            actorButton.setLayoutX(surfacePoint.getX() - actorButton.getWidth() / 2);
            actorButton.setLayoutY(surfacePoint.getY() - actorButton.getHeight() / 2);
            actorButton.setRotate(actorModel.orientation.get());
            categoryLabel.setVisible(showLabel);
            categoryLabel.setScaleX(getScaledValue(1.0));
            categoryLabel.setScaleY(getScaledValue(1.0));
            categoryLabel.setLayoutX(surfacePoint.getX() - categoryLabel.getWidth() / 2);
            categoryLabel.setLayoutY(surfacePoint.getY() + getScaledValue(LABEL_OFFSET_Y) + getScaledValue(categoryLabel.getHeight()) / 2);
        });
    }

    private Double getScaledValue(Double value) {
        return value * zoom.getValue();
    }

    @Override
    public void updateZoom(Zoom zoom) {
        this.zoom = zoom;
        update();
    }

    @Override
    public void setPlayerCategoryLabelDisplayEnabled(Boolean enabled) {
        showLabel = enabled;
        update();
    }

}
