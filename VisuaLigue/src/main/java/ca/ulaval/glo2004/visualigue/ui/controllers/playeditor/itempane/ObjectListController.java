package ca.ulaval.glo2004.visualigue.ui.controllers.playeditor.itempane;

import ca.ulaval.glo2004.visualigue.domain.obstacle.Obstacle;
import ca.ulaval.glo2004.visualigue.domain.sport.SportNotFoundException;
import ca.ulaval.glo2004.visualigue.domain.sport.ball.Ball;
import ca.ulaval.glo2004.visualigue.services.obstacle.ObstacleService;
import ca.ulaval.glo2004.visualigue.services.sport.SportService;
import ca.ulaval.glo2004.visualigue.ui.InjectableFXMLLoader;
import ca.ulaval.glo2004.visualigue.ui.View;
import ca.ulaval.glo2004.visualigue.ui.controllers.ControllerBase;
import ca.ulaval.glo2004.visualigue.ui.controllers.playeditor.scene.SceneController;
import ca.ulaval.glo2004.visualigue.ui.converters.BallModelConverter;
import ca.ulaval.glo2004.visualigue.ui.converters.ObstacleModelConverter;
import ca.ulaval.glo2004.visualigue.ui.models.BallModel;
import ca.ulaval.glo2004.visualigue.ui.models.Model;
import ca.ulaval.glo2004.visualigue.ui.models.ObstacleModel;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.layout.TilePane;
import javax.inject.Inject;
import javax.swing.SortOrder;

public class ObjectListController extends ControllerBase {

    @Inject private SportService sportService;
    @Inject private ObstacleService obstacleService;
    @Inject private ObstacleModelConverter obstacleModelConverter;
    @Inject private BallModelConverter ballModelConverter;
    @FXML private TilePane tilePane;
    private List<ObjectListItemController> itemControllers = new ArrayList();
    private UUID sportUUID;
    private SceneController sceneController;

    public void init(UUID sportUUID, SceneController sceneController) {
        this.sportUUID = sportUUID;
        this.sceneController = sceneController;
        sportService.onSportUpdated.setHandler(this::onObjectListChanged);
        obstacleService.onObstacleCreated.setHandler(this::onObjectListChanged);
        obstacleService.onObstacleUpdated.setHandler(this::onObjectListChanged);
        obstacleService.onObstacleDeleted.setHandler(this::onObjectListChanged);
        fillObjectList();
    }

    private void onObjectListChanged(Object sender, Object model) {
        Platform.runLater(() -> fillObjectList());
    }

    private void fillObjectList() {
        tilePane.getChildren().clear();
        fillBallList();
        fillObstacleList();
    }

    private void fillBallList() {
        try {
            Ball ball = sportService.getSport(sportUUID).getBall();
            initObjectItem(ballModelConverter.convert(ball));
        } catch (SportNotFoundException ex) {
            Logger.getLogger(ObjectListController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void fillObstacleList() {
        List<Obstacle> obstacles = obstacleService.getObstacles(Obstacle::getName, SortOrder.ASCENDING);
        obstacles.forEach(obstacle -> {
            initObjectItem(obstacleModelConverter.convert(obstacle));
        });
    }

    private void initObjectItem(Model model) {
        View view = InjectableFXMLLoader.loadView(ObjectListItemController.VIEW_NAME);
        ObjectListItemController controller = (ObjectListItemController) view.getController();
        if (model instanceof ObstacleModel) {
            controller.init((ObstacleModel) model);
        } else if (model instanceof BallModel) {
            controller.init((BallModel) model);
        }
        controller.onClick.setHandler(this::onObjectItemClicked);
        tilePane.getChildren().add(view.getRoot());
        itemControllers.add(controller);
    }

    private void onObjectItemClicked(Object sender, Model model) {
        unselectAll();
        ((ObjectListItemController) sender).select();
        if (model instanceof ObstacleModel) {
            sceneController.enterObstacleCreationMode((ObstacleModel) model);
        } else if (model instanceof BallModel) {
            sceneController.enterBallCreationMode((BallModel) model);
        }
    }

    private void unselectAll() {
        itemControllers.forEach(itemController -> itemController.unselect());
    }
}
