package ca.ulaval.glo2004.visualigue.ui.controllers.obstaclecreation;

import ca.ulaval.glo2004.visualigue.ui.controllers.Controller;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;

public class ObstacleCreationController extends Controller {

    public static final String VIEW_NAME = "/views/obstacle-creation/obstacle-creation.fxml";
    public static final String VIEW_TITLE = "Liste des obstacles";
    @FXML private ObstacleListController obstacleListController;

    @Override
    public StringProperty getTitle() {
        return new SimpleStringProperty(VIEW_TITLE);
    }

    @FXML
    public void onObstacleCreationLinkButtonClicked() {
        obstacleListController.newObstacle();
    }
}
