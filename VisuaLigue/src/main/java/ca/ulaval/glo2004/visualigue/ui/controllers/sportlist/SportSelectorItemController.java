package ca.ulaval.glo2004.visualigue.ui.controllers.sportlist;

import ca.ulaval.glo2004.visualigue.GuiceFXMLLoader;
import ca.ulaval.glo2004.visualigue.ui.controllers.SvgPaneController;
import ca.ulaval.glo2004.visualigue.ui.models.SportCreationModel;
import ca.ulaval.glo2004.visualigue.utils.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class SportSelectorItemController {

    public static final String VIEW_NAME = "/views/sport-selector-item.fxml";

    @FXML VBox rootNode;
    @FXML Label sportNameLabel;
    private SportCreationModel model;
    public EventHandler<SportCreationModel> onClick = new EventHandler();

    public SportCreationModel getModel() {
        return model;
    }

    public void init(SportCreationModel model) {
        this.model = model;
        sportNameLabel.textProperty().bind(model.name);
        setSportImage(model.builtInIconFileName.get());
    }

    private void setSportImage(String sportImageFileName) {
        FXMLLoader fxmlLoader = GuiceFXMLLoader.load(SvgPaneController.VIEW_NAME);
        SvgPaneController controller = (SvgPaneController) fxmlLoader.getController();
        controller.init(sportImageFileName);
        rootNode.getChildren().add(0, controller.getRootNode());
    }

    @FXML
    private void onClick() {
        onClick.fire(this, model);
    }

}
