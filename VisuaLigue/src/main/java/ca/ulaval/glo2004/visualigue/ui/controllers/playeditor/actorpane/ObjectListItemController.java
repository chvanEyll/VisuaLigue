package ca.ulaval.glo2004.visualigue.ui.controllers.playeditor.actorpane;

import ca.ulaval.glo2004.visualigue.ui.controllers.ControllerBase;
import ca.ulaval.glo2004.visualigue.ui.controllers.common.ExtendedButton;
import ca.ulaval.glo2004.visualigue.ui.models.BallModel;
import ca.ulaval.glo2004.visualigue.ui.models.ModelBase;
import ca.ulaval.glo2004.visualigue.ui.models.ObstacleModel;
import ca.ulaval.glo2004.visualigue.utils.EventHandler;
import ca.ulaval.glo2004.visualigue.utils.FilenameUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ObjectListItemController extends ControllerBase {

    public static final String VIEW_NAME = "/views/playeditor/actorpane/object-list-item.fxml";

    public EventHandler<ModelBase> onClick = new EventHandler();
    @FXML private ExtendedButton rootNode;
    @FXML private ImageView imageView;
    @FXML private Tooltip tooltip;
    private ModelBase model;

    public void init(BallModel model) {
        this.model = model;
        if (model.imagePathName.isNotEmpty().get()) {
            setImage(FilenameUtils.getURIString(model.imagePathName.get()));
        } else {
            setImage(model.builtInImagePathName.get());
        }
        tooltip.textProperty().bind(model.name);
    }

    public void init(ObstacleModel model) {
        this.model = model;
        if (model.currentImagePathName.isNotEmpty().get()) {
            setImage(FilenameUtils.getURIString(model.currentImagePathName.get()));
        } else {
            setImage(model.builtInImagePathName.get());
        }
        tooltip.textProperty().bind(model.name);
    }

    public ModelBase getModel() {
        return model;
    }

    private void setImage(String url) {
        Image image = new Image(url);
        imageView.setImage(image);
    }

    public void select() {
        rootNode.setSelected(true);
    }

    public void unselect() {
        rootNode.setSelected(false);
    }

    public Boolean isSelected() {
        return rootNode.isSelected();
    }

    @FXML
    protected void onAction(ActionEvent e) {
        onClick.fire(this, model);
    }

}
