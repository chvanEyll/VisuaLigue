package ca.ulaval.glo2004.visualigue.ui.controllers;

import ca.ulaval.glo2004.visualigue.GuiceFXMLLoader;
import ca.ulaval.glo2004.visualigue.ui.models.SportModel;
import ca.ulaval.glo2004.visualigue.utils.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class SportSelectorItemController extends Controller {

    public static final String VIEW_NAME = "/views/sport-selector-item.fxml";

    @FXML VBox rootNode;
    @FXML Label sportNameLabel;
    private SportModel sportModel;
    public EventHandler<SportModel> onClick = new EventHandler();

    public SportModel getSportModel() {
        return sportModel;
    }

    public void setSportModel(SportModel sportModel) {
        this.sportModel = sportModel;
        sportNameLabel.textProperty().bind(sportModel.name);
        setSportImage(sportModel.builtInIconFileName.get());
    }

    private void setSportImage(String sportImageFileName) {
        FXMLLoader fxmlLoader = GuiceFXMLLoader.load(SvgPaneController.VIEW_NAME);
        SvgPaneController controller = (SvgPaneController) fxmlLoader.getController();
        controller.setSvg(sportImageFileName);
        rootNode.getChildren().add(0, controller.getRootNode());
    }
    
    @FXML
    private void onClick() {
        onClick.fire(this, sportModel);
    }

}
