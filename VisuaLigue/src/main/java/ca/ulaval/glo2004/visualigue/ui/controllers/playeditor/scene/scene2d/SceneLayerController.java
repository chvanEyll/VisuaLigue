package ca.ulaval.glo2004.visualigue.ui.controllers.playeditor.scene.scene2d;

import ca.ulaval.glo2004.visualigue.ui.controllers.ControllerBase;
import ca.ulaval.glo2004.visualigue.ui.controllers.common.SvgImage;
import ca.ulaval.glo2004.visualigue.ui.controllers.playeditor.scene.Zoom;
import ca.ulaval.glo2004.visualigue.ui.models.ActorModel;
import ca.ulaval.glo2004.visualigue.utils.FilenameUtils;
import ca.ulaval.glo2004.visualigue.utils.geometry.Vector2;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class SceneLayerController extends ControllerBase {

    public static final String VIEW_NAME = "/views/playeditor/scene2d/scene-layer.fxml";
    @FXML private Button actorButton;
    @FXML private SvgImage svgImage;
    @FXML private ImageView imageView;
    @FXML private Tooltip tooltip;
    private ActorModel actorModel;
    private Vector2 baseLayerSize;

    public void init(ActorModel actorModel, Vector2 baseLayerSize) {
        this.actorModel = actorModel;
        this.baseLayerSize = baseLayerSize;
        if (actorModel.svgImagePathName.isNotEmpty().get()) {
            svgImage.setUrl(actorModel.svgImagePathName.get());
        } else if (actorModel.imagePathName.isNotEmpty().get()) {
            imageView.setImage(new Image(FilenameUtils.getURIString(actorModel.imagePathName.get())));
        } else {
            imageView.setImage(new Image(actorModel.builtInImagePathName.get()));
        }
        tooltip.textProperty().bind(actorModel.hoverText);
    }

    public void setZoom(Zoom zoom) {
        actorButton.setLayoutX(actorModel.x.get() * baseLayerSize.getX() * zoom.getValue());
        actorButton.setLayoutY(actorModel.y.get() * baseLayerSize.getY() * zoom.getValue());
    }

    public void setPlayerCategoryLabelDisplayEnabled(Boolean enabled) {

    }

}
