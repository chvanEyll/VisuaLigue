package ca.ulaval.glo2004.visualigue.ui.controllers.sportmanagement;

import ca.ulaval.glo2004.visualigue.ui.controllers.ControllerBase;
import ca.ulaval.glo2004.visualigue.ui.models.SportListItemModel;
import ca.ulaval.glo2004.visualigue.utils.EventHandler;
import ca.ulaval.glo2004.visualigue.utils.FilenameUtils;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

public class SportListItemController extends ControllerBase {

    public static final String VIEW_NAME = "/views/sportmanagement/sport-list-item.fxml";

    @FXML private VBox rootNode;
    @FXML private Label sportNameLabel;
    private SportListItemModel model;
    public EventHandler<SportListItemModel> onClick = new EventHandler();

    public void init(SportListItemModel model) {
        this.model = model;
        sportNameLabel.textProperty().bind(model.name);
        if (model.customIconPathName.isNotEmpty().get()) {
            setSportImage(FilenameUtils.getURIString(model.customIconPathName.get()));
        } else if (model.builtInIconPathName.isNotEmpty().get()) {
            setSportImage(model.builtInIconPathName.get());
        }
    }

    private void setSportImage(String sportImagePathName) {
        ImageView imageView = new ImageView();
        Image image = new Image(sportImagePathName);
        imageView.setPreserveRatio(true);
        imageView.setImage(new Image(sportImagePathName));
        imageView.setFitWidth(100);
        imageView.setFitHeight(85);
        rootNode.getChildren().add(0, imageView);
    }

    @FXML
    protected void onMouseClicked(MouseEvent e) {
        onClick.fire(this, model);
    }

}
