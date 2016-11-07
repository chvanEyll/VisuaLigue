package ca.ulaval.glo2004.visualigue.ui.controllers.playeditor.actorpane;

import ca.ulaval.glo2004.visualigue.domain.obstacle.Obstacle;
import ca.ulaval.glo2004.visualigue.domain.sport.Sport;
import ca.ulaval.glo2004.visualigue.domain.sport.ball.Ball;
import ca.ulaval.glo2004.visualigue.services.obstacle.ObstacleService;
import ca.ulaval.glo2004.visualigue.services.sport.SportService;
import ca.ulaval.glo2004.visualigue.ui.InjectableFXMLLoader;
import ca.ulaval.glo2004.visualigue.ui.View;
import ca.ulaval.glo2004.visualigue.ui.controllers.ControllerBase;
import ca.ulaval.glo2004.visualigue.ui.controllers.playeditor.scene.SceneController;
import ca.ulaval.glo2004.visualigue.ui.controllers.playeditor.actorcreation.BallCreationController;
import ca.ulaval.glo2004.visualigue.ui.controllers.playeditor.actorcreation.ObstacleCreationController;
import ca.ulaval.glo2004.visualigue.ui.converters.BallModelConverter;
import ca.ulaval.glo2004.visualigue.ui.converters.ObstacleModelConverter;
import ca.ulaval.glo2004.visualigue.ui.models.BallModel;
import ca.ulaval.glo2004.visualigue.ui.models.ModelBase;
import ca.ulaval.glo2004.visualigue.ui.models.ObstacleModel;
import ca.ulaval.glo2004.visualigue.ui.models.PlayModel;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.layout.TilePane;
import javax.inject.Inject;
import javax.swing.SortOrder;

public class ObjectListController extends ControllerBase {

    @Inject private SportService sportService;
    @Inject private ObstacleService obstacleService;
    @Inject private ObstacleModelConverter obstacleModelConverter;
    @Inject private ObstacleCreationController obstacleCreationController;
    @Inject private BallModelConverter ballModelConverter;
    @Inject private BallCreationController ballCreationController;
    @FXML private TilePane tilePane;
    private List<ObjectListItemController> itemControllers = new ArrayList();
    private PlayModel playModel;
    private SceneController sceneController;
    private BiConsumer<Object, Sport> onSportUpdated = this::onSportUpdated;
    private BiConsumer<Object, Obstacle> onObstacleChanged = this::onObstacleChanged;

    public void init(PlayModel playModel, SceneController sceneController) {
        this.playModel = playModel;
        this.sceneController = sceneController;
        obstacleCreationController.onDisabled.addHandler(this::onObstacleCreationModeExited);
        ballCreationController.onDisabled.addHandler(this::onBallCreationModeExited);
        sportService.onSportUpdated.addHandler(onSportUpdated);
        obstacleService.onObstacleCreated.addHandler(onObstacleChanged);
        obstacleService.onObstacleUpdated.addHandler(onObstacleChanged);
        obstacleService.onObstacleDeleted.addHandler(onObstacleChanged);
        fillObjectList();
    }

    @Override
    public void clean() {
        sportService.onSportUpdated.removeHandler(onSportUpdated);
        obstacleService.onObstacleCreated.removeHandler(onObstacleChanged);
        obstacleService.onObstacleUpdated.removeHandler(onObstacleChanged);
        obstacleService.onObstacleDeleted.removeHandler(onObstacleChanged);
    }

    private void onSportUpdated(Object sender, Sport sport) {
        Platform.runLater(() -> fillObjectList());
    }

    private void onObstacleChanged(Object sender, Obstacle obstacle) {
        Platform.runLater(() -> fillObjectList());
    }

    private void fillObjectList() {
        tilePane.getChildren().clear();
        fillBallList();
        fillObstacleList();
    }

    private void fillBallList() {
        Ball ball = sportService.getSport(playModel.sportUUID.get()).getBall();
        initObjectItem(ballModelConverter.convert(ball));
    }

    private void fillObstacleList() {
        List<Obstacle> obstacles = obstacleService.getObstacles(Obstacle::getName, SortOrder.ASCENDING);
        obstacles.forEach(obstacle -> {
            initObjectItem(obstacleModelConverter.convert(obstacle));
        });
    }

    private void initObjectItem(ModelBase model) {
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

    private void onObjectItemClicked(Object sender, ModelBase model) {
        unselectAll();
        ((ObjectListItemController) sender).select();
        if (model instanceof ObstacleModel) {
            obstacleCreationController.init((ObstacleModel) model);
        } else if (model instanceof BallModel) {
            ballCreationController.init((BallModel) model);
        }
    }

    private void onObstacleCreationModeExited(Object sender, Object param) {
        itemControllers.stream().filter(i -> i.getModel() instanceof ObstacleModel).forEach(i -> i.unselect());
    }

    private void onBallCreationModeExited(Object sender, Object param) {
        itemControllers.stream().filter(i -> i.getModel() instanceof BallModel).forEach(i -> i.unselect());
    }

    private void unselectAll() {
        itemControllers.forEach(itemController -> itemController.unselect());
    }
}
