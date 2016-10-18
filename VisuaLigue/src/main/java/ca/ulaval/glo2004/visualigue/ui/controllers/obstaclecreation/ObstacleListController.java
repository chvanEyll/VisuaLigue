package ca.ulaval.glo2004.visualigue.ui.controllers.obstaclecreation;

import ca.ulaval.glo2004.visualigue.domain.obstacle.Obstacle;
import ca.ulaval.glo2004.visualigue.domain.obstacle.ObstacleNotFoundException;
import ca.ulaval.glo2004.visualigue.services.ObstacleService;
import ca.ulaval.glo2004.visualigue.ui.InjectableFXMLLoader;
import ca.ulaval.glo2004.visualigue.ui.converters.ObstacleCreationModelConverter;
import ca.ulaval.glo2004.visualigue.ui.models.ObstacleCreationModel;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.VBox;
import javax.inject.Inject;
import javax.swing.SortOrder;

public class ObstacleListController implements Initializable {

    public static final String VIEW_NAME = "/views/obstacle-creation/obstacle-list.fxml";
    @Inject private ObstacleService obstacleService;
    @Inject private ObstacleCreationModelConverter obstacleCreationModelConverter;
    private ObservableList<ObstacleCreationModel> obstacleCreationModels = FXCollections.observableArrayList();
    private ObstacleListItemEditionController listItemEditionController;
    @FXML private VBox gridContent;
    private Integer itemCount = 0;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        List<Obstacle> obstacles = obstacleService.getObstacles(Obstacle::getName, SortOrder.ASCENDING);
        obstacles.forEach(obstacle -> {
            ObstacleCreationModel obstacleCreationModel = obstacleCreationModelConverter.convert(obstacle);
            obstacleCreationModels.add(obstacleCreationModel);
            Platform.runLater(() -> {
                insertItem(obstacleCreationModel, itemCount);
            });
        });
    }

    private void insertItem(ObstacleCreationModel obstacleCreationModel, Integer rowIndex) {
        FXMLLoader fxmlLoader = InjectableFXMLLoader.load(ObstacleListItemController.VIEW_NAME);
        ObstacleListItemController listItemController = (ObstacleListItemController) fxmlLoader.getController();
        listItemController.init(obstacleCreationModel);
        listItemController.onEditRequested.setHandler(this::onItemEditRequested);
        listItemController.onDeleteRequested.setHandler(this::onItemDeleteRequest);
        gridContent.getChildren().add(rowIndex, fxmlLoader.getRoot());
        itemCount += 1;
    }

    private void onItemEditRequested(Object sender, ObstacleCreationModel model) {
        enterItemEditionMode(model);
    }

    private void enterItemEditionMode(ObstacleCreationModel model) {
        closeItemEdition();
        FXMLLoader fxmlLoader = InjectableFXMLLoader.load(ObstacleListItemEditionController.VIEW_NAME);
        listItemEditionController = (ObstacleListItemEditionController) fxmlLoader.getController();
        listItemEditionController.init(model);
        listItemEditionController.onCloseRequested.setHandler(this::onItemEditionCloseRequested);
        int rowIndex = obstacleCreationModels.indexOf(model);
        gridContent.getChildren().remove(rowIndex);
        gridContent.getChildren().add(rowIndex, fxmlLoader.getRoot());
    }

    private void onItemEditionCloseRequested(Object sender, ObstacleCreationModel model) {
        closeItemEdition();
    }

    private void closeItemEdition() {
        if (listItemEditionController != null) {
            int rowIndex = obstacleCreationModels.indexOf(listItemEditionController.getModel());
            gridContent.getChildren().remove(rowIndex);
            ObstacleCreationModel obstacleModel = listItemEditionController.getModel();
            insertItem(obstacleModel, rowIndex);
            obstacleModel.makeDirty();
            listItemEditionController = null;
        }
    }

    public void newObstacle() {
        closeItemEdition();
        ObstacleCreationModel obstacleModel = new ObstacleCreationModel();
        obstacleCreationModels.add(0, obstacleModel);
        insertItem(obstacleModel, 0);
        enterItemEditionMode(obstacleModel);
    }

    private void onItemDeleteRequest(Object sender, ObstacleCreationModel model) {
        deleteItem(model);
    }

    private void deleteItem(ObstacleCreationModel model) {
        try {
            int rowIndex = obstacleCreationModels.indexOf(model);
            obstacleService.deleteObstacle(model.getUUID());
            obstacleCreationModels.remove(model);
            gridContent.getChildren().remove(rowIndex);
        } catch (ObstacleNotFoundException ex) {
            Logger.getLogger(ObstacleListController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
