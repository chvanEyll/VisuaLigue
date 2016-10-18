package ca.ulaval.glo2004.visualigue.ui.controllers.sportmanagement;

import ca.ulaval.glo2004.visualigue.ui.models.SportListItemModel;
import ca.ulaval.glo2004.visualigue.utils.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

public class SportListItemController {

    public static final String VIEW_NAME = "/views/sport-management/sport-list-item.fxml";

    @FXML private VBox rootNode;
    @FXML private Label sportNameLabel;
    private SportListItemModel model;
    public EventHandler<SportListItemModel> onClick = new EventHandler();

    public void init(SportListItemModel model) {
        this.model = model;
        sportNameLabel.textProperty().bindBidirectional(model.name);
        setSportImage(model.builtInIconPathName.get());
    }

    private void setSportImage(String sportImagePathName) {
        ImageView imageView = new ImageView();
        Image image = new Image(sportImagePathName);
        imageView.setImage(new Image(sportImagePathName));
        imageView.setFitWidth(image.getWidth());
        imageView.setFitHeight(image.getHeight());
        rootNode.getChildren().add(0, imageView);
    }

    @FXML
    public void onClick() {
        onClick.fire(this, model);
    }

}
