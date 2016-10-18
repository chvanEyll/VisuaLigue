package ca.ulaval.glo2004.visualigue.ui.controllers.obstaclecreation;

import ca.ulaval.glo2004.visualigue.ui.InjectableFXMLLoader;
import ca.ulaval.glo2004.visualigue.ui.controllers.Controller;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.VBox;

public class ObstacleCreationController extends Controller {

    public static final String VIEW_NAME = "/views/obstacle-creation/obstacle-creation.fxml";
    public static final String VIEW_TITLE = "Liste des obstacles";
    @FXML VBox obstacleList;
    private ObstacleListController obstacleListController;

    @Override
    public StringProperty getTitle() {
        return new SimpleStringProperty(VIEW_TITLE);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        FXMLLoader fxmlLoader = InjectableFXMLLoader.load(ObstacleListController.VIEW_NAME);
        obstacleListController = (ObstacleListController) fxmlLoader.getController();
        obstacleListController.init();
        obstacleList.getChildren().add(fxmlLoader.getRoot());
    }

    @FXML
    public void onObstacleCreationLinkButtonClicked() {
        obstacleListController.newObstacle();
    }
}
