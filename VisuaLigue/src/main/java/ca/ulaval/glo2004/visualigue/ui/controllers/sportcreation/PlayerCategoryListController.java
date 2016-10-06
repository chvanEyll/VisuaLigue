package ca.ulaval.glo2004.visualigue.ui.controllers.sportcreation;

import ca.ulaval.glo2004.visualigue.GuiceFXMLLoader;
import ca.ulaval.glo2004.visualigue.ui.models.PlayerCategoryModel;
import java.util.List;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;

public class PlayerCategoryListController {

    public static final String VIEW_NAME = "/views/player-category-list.fxml";
    @FXML private GridPane playerCategoryGridPane;
    private ObservableList<PlayerCategoryModel> playerCategoryModels;
    private List<PlayerCategoryListItemController> listItemControllers;
    private PlayerCategoryListItemEditionController listItemEditionController;
    private Boolean editMode = false;

    public void init(ObservableList<PlayerCategoryModel> playerCategoryModels) {
        this.playerCategoryModels = playerCategoryModels;
        playerCategoryModels.forEach(playerCategoryModel -> {
            addItem(playerCategoryModel);
        });
    }

    private void addItem(PlayerCategoryModel playerCategoryModel) {
        FXMLLoader fxmlLoader = GuiceFXMLLoader.load(PlayerCategoryListItemController.VIEW_NAME);
        PlayerCategoryListItemController controller = (PlayerCategoryListItemController) fxmlLoader.getController();
        controller.init(playerCategoryModel);
        controller.onEditRequested.setHandler(this::onItemEditRequestedHandler);
        playerCategoryGridPane.addRow(0, controller.getChildren().stream().toArray(Node[]::new));
        listItemControllers.add(controller);
    }

    private void onItemEditRequestedHandler(Object sender, PlayerCategoryModel model) {
        FXMLLoader fxmlLoader = GuiceFXMLLoader.load(PlayerCategoryListItemEditionController.VIEW_NAME);
        listItemEditionController = (PlayerCategoryListItemEditionController) fxmlLoader.getController();
        listItemEditionController.init(model);
        listItemEditionController.onEditionValidationRequested.setHandler(this::onEditionValidationRequestedHandler);
        PlayerCategoryListItemController categoryListItemController = (PlayerCategoryListItemController) sender;
        categoryListItemController.getChildren().stream().forEach(node -> {
            playerCategoryGridPane.getChildren().remove(node);
        });
        int itemIndex = playerCategoryModels.indexOf(categoryListItemController);
        playerCategoryGridPane.addRow(itemIndex, listItemEditionController.getChildren().stream().toArray(Node[]::new));
        editMode = true;
    }

    public void onEditionValidationRequestedHandler(Object sender, PlayerCategoryModel model) {
        validateItemEdition();
    }

    private void validateItemEdition() {
        if (!editMode) {
            return;
        }
        listItemEditionController.getChildren().stream().forEach(node -> {
            playerCategoryGridPane.getChildren().remove(node);
        });
        int itemIndex = playerCategoryModels.indexOf(listItemEditionController.getModel());
        playerCategoryGridPane.addRow(itemIndex, listItemControllers.get(itemIndex).getChildren().stream().toArray(Node[]::new));
        editMode = false;
    }

    public void newCategory() {
        validateItemEdition();
        PlayerCategoryModel playerCategoryModel = new PlayerCategoryModel();
        playerCategoryModels.add(playerCategoryModel);
        addItem(playerCategoryModel);
    }
}
