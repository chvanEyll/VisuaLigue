package ca.ulaval.glo2004.visualigue.ui.controllers.sportlist;

import ca.ulaval.glo2004.visualigue.InjectableFXMLLoader;
import ca.ulaval.glo2004.visualigue.ui.controllers.common.SvgPaneController;
import ca.ulaval.glo2004.visualigue.ui.models.SportListItemModel;
import ca.ulaval.glo2004.visualigue.utils.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class SportSelectorItemController {

    public static final String VIEW_NAME = "/views/sport-selector-item.fxml";

    @FXML VBox rootNode;
    @FXML Label sportNameLabel;
    private SportListItemModel model;
    public EventHandler<SportListItemModel> onClick = new EventHandler();

    public SportListItemModel getModel() {
        return model;
    }

    public void init(SportListItemModel model) {
        this.model = model;
        sportNameLabel.textProperty().bindBidirectional(model.name);
        setSportImage(model.builtInIconFileName.get());
    }

    private void setSportImage(String sportImageFileName) {
        FXMLLoader fxmlLoader = InjectableFXMLLoader.load(SvgPaneController.VIEW_NAME);
        SvgPaneController controller = (SvgPaneController) fxmlLoader.getController();
        controller.init(sportImageFileName);
        rootNode.getChildren().add(0, controller.getRootNode());
    }

    @FXML
    public void onClick() {
        onClick.fire(this, model);
    }

}
