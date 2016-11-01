package ca.ulaval.glo2004.visualigue.ui.controllers.playeditor.scene.scene2d.layers;

import ca.ulaval.glo2004.visualigue.ui.controllers.common.SvgImage;
import ca.ulaval.glo2004.visualigue.ui.models.ActorModel;
import ca.ulaval.glo2004.visualigue.utils.geometry.Vector2;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;

public class PlayerLayerController extends ActorLayerController {

    public static final String VIEW_NAME = "/views/playeditor/scene2d/layers/player-layer.fxml";
    @FXML private Button actorButton;
    @FXML private SvgImage svgImage;
    @FXML private Tooltip tooltip;

    @Override
    public void init(ActorModel actorModel, PlayingSurfaceLayerController playingLayerSurfaceController) {
        super.init(actorModel, playingLayerSurfaceController);
        svgImage.setUrl(actorModel.svgImagePathName.get());
        tooltip.textProperty().bind(actorModel.hoverText);
        actorModel.x.addListener(this::onActorPositionChanged);
        actorModel.y.addListener(this::onActorPositionChanged);
        actorModel.orientation.addListener(this::onActorPositionChanged);
        update();
    }

    private void onActorPositionChanged(final ObservableValue<? extends Number> value, final Number oldPropertyValue, final Number newPropertyValue) {
        update();
    }

    @Override
    public void update() {
        Vector2 actorLocation = new Vector2(actorModel.x.get(), actorModel.y.get());
        Vector2 surfacePoint = playingSurfaceLayerController.relativeToSurfacePoint(actorLocation);
        actorButton.setLayoutX(surfacePoint.getX());
        actorButton.setLayoutY(surfacePoint.getY());
    }

    @Override
    public void setPlayerCategoryLabelDisplayEnabled(Boolean enabled) {

    }

}
