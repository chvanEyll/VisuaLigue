package ca.ulaval.glo2004.visualigue.ui.controllers.playeditor.actorpane;

import ca.ulaval.glo2004.visualigue.domain.play.actor.TeamSide;
import ca.ulaval.glo2004.visualigue.domain.sport.Sport;
import ca.ulaval.glo2004.visualigue.domain.sport.playercategory.PlayerCategory;
import ca.ulaval.glo2004.visualigue.services.sport.SportService;
import ca.ulaval.glo2004.visualigue.ui.InjectableFXMLLoader;
import ca.ulaval.glo2004.visualigue.ui.View;
import ca.ulaval.glo2004.visualigue.ui.controllers.ControllerBase;
import ca.ulaval.glo2004.visualigue.ui.controllers.playeditor.scene.SceneController;
import ca.ulaval.glo2004.visualigue.ui.controllers.playeditor.actorcreation.PlayerCreationController;
import ca.ulaval.glo2004.visualigue.ui.converters.PlayerCategoryModelConverter;
import ca.ulaval.glo2004.visualigue.ui.models.PlayModel;
import ca.ulaval.glo2004.visualigue.ui.models.PlayerCategoryModel;
import java.util.ArrayList;
import java.util.List;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.layout.TilePane;
import javax.inject.Inject;
import javax.swing.SortOrder;

public class PlayerCategoryListController extends ControllerBase {

    @Inject private SportService sportService;
    @Inject private PlayerCategoryModelConverter playerCategoryModelConverter;
    @Inject private PlayerCreationController playerCreationController;
    @FXML private TilePane allyPlayerCategoryPane;
    @FXML private TilePane opponentPlayerCategoryPane;
    private List<PlayerCategoryListItemController> itemControllers = new ArrayList();
    private PlayModel playModel;
    private SceneController sceneController;

    public void init(PlayModel playModel, SceneController sceneController) {
        this.playModel = playModel;
        this.sceneController = sceneController;
        playerCreationController.onDisabled.addHandler(this::onPlayerCreationModeExited);
        sportService.onSportUpdated.addHandler(this::onSportChanged);
        fillPlayerCategoryList();
    }

    @Override
    public void clean() {
        sportService.onSportUpdated.removeHandler(this::onSportChanged);
    }

    private void onSportChanged(Object sender, Sport sport) {
        Platform.runLater(() -> fillPlayerCategoryList());
    }

    private void fillPlayerCategoryList() {
        allyPlayerCategoryPane.getChildren().clear();
        opponentPlayerCategoryPane.getChildren().clear();
        List<PlayerCategory> playerCategories = sportService.getPlayerCategories(playModel.sportUUID.get(), PlayerCategory::getName, SortOrder.ASCENDING);
        playerCategories.forEach(playerCategory -> {
            initCategoryItem(playerCategoryModelConverter.convert(playerCategory));
        });
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
        playerCreationController.init(model, TeamSide.ALLIES);
        sceneController.enterCreationMode(playerCreationController);
    }

    private void onOpponentPlayerItemClicked(Object sender, PlayerCategoryModel model) {
        unselectAll();
        ((PlayerCategoryListItemController) sender).select();
        playerCreationController.init(model, TeamSide.OPPONENTS);
        sceneController.enterCreationMode(playerCreationController);
    }

    private void onPlayerCreationModeExited(Object sender, Object param) {
        unselectAll();
    }

    private void unselectAll() {
        itemControllers.forEach(itemController -> itemController.unselect());
    }
}