package ca.ulaval.glo2004.visualigue.ui.controllers.playeditor.scene;

import ca.ulaval.glo2004.visualigue.ui.controllers.ControllerBase;
import ca.ulaval.glo2004.visualigue.ui.customcontrols.SvgImage;
import ca.ulaval.glo2004.visualigue.ui.models.ActorModel;
import ca.ulaval.glo2004.visualigue.utils.FilenameUtils;
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
    private Double baseLayerWidth;
    private Double baseLayerHeight;

    public void init(ActorModel actorModel, Double baseLayerWidth, Double baseLayerHeight) {
        this.actorModel = actorModel;
        this.baseLayerWidth = baseLayerWidth;
        this.baseLayerHeight = baseLayerHeight;
        if (actorModel.svgImagePathName.isNotEmpty().get()) {
            svgImage.setUrl(actorModel.svgImagePathName.get());
        } else if (actorModel.imagePathName.isNotEmpty().get()) {
            imageView.setImage(new Image(FilenameUtils.getURIString(actorModel.imagePathName.get())));
        } else {
            imageView.setImage(new Image(actorModel.builtInImagePathName.get()));
        }
        tooltip.textProperty().bind(actorModel.hoverText);
        setZoom(1.0);
    }

    public void setZoom(Double zoom) {
        actorButton.setLayoutX(actorModel.x.get() * baseLayerWidth * zoom);
        actorButton.setLayoutY(actorModel.y.get() * baseLayerHeight * zoom);
    }

    public void setPlayerCategoryLabelDisplayEnabled(Boolean enabled) {

    }

}
