package ca.ulaval.glo2004.visualigue.ui.controllers.playeditor.itempane;

import ca.ulaval.glo2004.visualigue.ui.customcontrols.SvgImage;
import ca.ulaval.glo2004.visualigue.ui.models.BallModel;
import ca.ulaval.glo2004.visualigue.ui.models.Model;
import ca.ulaval.glo2004.visualigue.ui.models.ObstacleModel;
import ca.ulaval.glo2004.visualigue.utils.EventHandler;
import ca.ulaval.glo2004.visualigue.utils.FilenameUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;

public class ObjectListItemController {

    public static final String VIEW_NAME = "/views/playeditor/itempane/object-list-item.fxml";

    public EventHandler<Model> onClick = new EventHandler();
    @FXML private Button rootNode;
    @FXML private SvgImage objectIcon;
    @FXML private Tooltip tooltip;
    private Model model;

    public void init(BallModel model) {
        this.model = model;
        objectIcon.setUrl(model.builtInImagePathName.get());
        tooltip.textProperty().bindBidirectional(model.name);
    }

    public void init(ObstacleModel model) {
        this.model = model;
        if (model.currentImagePathName.isNotEmpty().get()) {
            objectIcon.setUrl(FilenameUtils.getURIString(model.currentImagePathName.get()));
        } else {
            objectIcon.setUrl(model.builtInImagePathName.get());
        }
        tooltip.textProperty().bindBidirectional(model.name);
    }

    public void select() {
        rootNode.getStyleClass().add("selected");
    }

    public void unselect() {
        rootNode.getStyleClass().remove("selected");
    }

    @FXML
    public void onAction(ActionEvent e) {
        onClick.fire(this, model);
    }

}
