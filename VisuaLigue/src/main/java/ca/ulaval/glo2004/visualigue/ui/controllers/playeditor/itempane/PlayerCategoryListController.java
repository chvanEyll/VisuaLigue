package ca.ulaval.glo2004.visualigue.ui.controllers.playeditor.itempane;

import ca.ulaval.glo2004.visualigue.domain.play.actorinstance.TeamSide;
import ca.ulaval.glo2004.visualigue.domain.sport.Sport;
import ca.ulaval.glo2004.visualigue.domain.sport.SportNotFoundException;
import ca.ulaval.glo2004.visualigue.domain.sport.playercategory.PlayerCategory;
import ca.ulaval.glo2004.visualigue.services.sport.SportService;
import ca.ulaval.glo2004.visualigue.ui.InjectableFXMLLoader;
import ca.ulaval.glo2004.visualigue.ui.View;
import ca.ulaval.glo2004.visualigue.ui.controllers.ControllerBase;
import ca.ulaval.glo2004.visualigue.ui.controllers.playeditor.scene.SceneController;
import ca.ulaval.glo2004.visualigue.ui.controllers.sportcreation.SportCreationController;
import ca.ulaval.glo2004.visualigue.ui.converters.PlayerCategoryModelConverter;
import ca.ulaval.glo2004.visualigue.ui.models.PlayerCategoryModel;
import java.net.URL;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.TilePane;
import javax.inject.Inject;

public class PlayerCategoryListController extends ControllerBase {

    @Inject private SportService sportService;
    @Inject private PlayerCategoryModelConverter playerCategoryModelConverter;
    @FXML private TilePane allyPlayerCategoryPane;
    @FXML private TilePane opponentPlayerCategoryPane;
    private List<PlayerCategoryListItemController> itemControllers = new ArrayList();
    private UUID sportUUID;
    private SceneController sceneController;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        sportService.onSportUpdated.setHandler(this::onSportChanged);
        fillPlayerCategoryList();
    }

    public void init(UUID sportUUID, SceneController sceneController) {
        this.sportUUID = sportUUID;
        this.sceneController = sceneController;
    }

    private void onSportChanged(Object sender, Sport sport) {
        Platform.runLater(() -> fillPlayerCategoryList());
    }

    private void fillPlayerCategoryList() {
        allyPlayerCategoryPane.getChildren().clear();
        opponentPlayerCategoryPane.getChildren().clear();
        try {
            Set<PlayerCategory> playerCategories = sportService.getPlayerCategories(sportUUID);
            playerCategories.forEach(playerCategory -> {
                initCategoryItem(playerCategoryModelConverter.convert(playerCategory));
            });
        } catch (SportNotFoundException ex) {
            Logger.getLogger(PlayerCategoryListController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void initCategoryItem(PlayerCategoryModel model) {
        View view = InjectableFXMLLoader.loadView(PlayerCategoryListItemController.VIEW_NAME);
        PlayerCategoryListItemController controller = (PlayerCategoryListItemController) view.getController();
        controller.init(model, TeamSide.ALLIES);
        controller.onClick.setHandler(this::onAllyPlayerItemClicked);
        allyPlayerCategoryPane.getChildren().add(view.getRoot());
        itemControllers.add(controller);

        view = InjectableFXMLLoader.loadView(PlayerCategoryListItemController.VIEW_NAME);
        controller = (PlayerCategoryListItemController) view.getController();
        controller.init(model, TeamSide.OPPONENTS);
        controller.onClick.setHandler(this::onOpponentPlayerItemClicked);
        opponentPlayerCategoryPane.getChildren().add(view.getRoot());
        itemControllers.add(controller);
    }

    private void onAllyPlayerItemClicked(Object sender, PlayerCategoryModel model) {
        unselectAll();
        ((PlayerCategoryListItemController) sender).select();
        sceneController.enterPlayerCreationMode(model, TeamSide.ALLIES);
    }

    private void onOpponentPlayerItemClicked(Object sender, PlayerCategoryModel model) {
        unselectAll();
        ((PlayerCategoryListItemController) sender).select();
        sceneController.enterPlayerCreationMode(model, TeamSide.OPPONENTS);
    }

    private void unselectAll() {
        itemControllers.forEach(itemController -> itemController.unselect());
    }

    @FXML
    public void onNewSportButtonClicked(MouseEvent e) {
        View view = InjectableFXMLLoader.loadView(SportCreationController.VIEW_NAME);
        SportCreationController controller = (SportCreationController) view.getController();
        controller.init();
        onViewChangeRequested.fire(this, view);
    }
}
