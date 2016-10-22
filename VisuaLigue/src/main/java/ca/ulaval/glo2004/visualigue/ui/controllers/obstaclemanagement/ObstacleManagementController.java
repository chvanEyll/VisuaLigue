package ca.ulaval.glo2004.visualigue.ui.controllers.obstaclemanagement;

import ca.ulaval.glo2004.visualigue.domain.obstacle.Obstacle;
import ca.ulaval.glo2004.visualigue.services.obstacle.ObstacleService;
import ca.ulaval.glo2004.visualigue.ui.controllers.ControllerBase;
import ca.ulaval.glo2004.visualigue.ui.converters.ObstacleModelConverter;
import ca.ulaval.glo2004.visualigue.ui.models.ObstacleModel;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javax.inject.Inject;
import javax.swing.SortOrder;

public class ObstacleManagementController extends ControllerBase {

    public static final String VIEW_NAME = "/views/obstaclemanagement/obstacle-management.fxml";
    public static final String VIEW_TITLE = "Obstacles";
    @FXML private ObstacleListController obstacleListController;
    @Inject private ObstacleService obstacleService;
    @Inject private ObstacleModelConverter obstacleModelConverter;

    @Override
    public StringProperty getTitle() {
        return new SimpleStringProperty(VIEW_TITLE);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        List<Obstacle> obstacles = obstacleService.getObstacles(Obstacle::getName, SortOrder.ASCENDING);
        ObservableList<ObstacleModel> obstacleModels = FXCollections.observableArrayList();
        obstacles.forEach(obstacle -> {
            ObstacleModel obstacleModel = obstacleModelConverter.convert(obstacle);
            obstacleModels.add(obstacleModel);
        });
        obstacleListController.init(obstacleModels, ObstacleModel.class, ObstacleListItemController.VIEW_NAME, ObstacleListItemEditionController.VIEW_NAME);
    }
}
