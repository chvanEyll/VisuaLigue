package ca.ulaval.glo2004.visualigue.ui.controllers.sportcreation;

import ca.ulaval.glo2004.visualigue.ui.InjectableFXMLLoader;
import ca.ulaval.glo2004.visualigue.ui.models.PlayerCategoryModel;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.VBox;

public class PlayerCategoryListController {

    public static final String VIEW_NAME = "/views/sport-creation/player-category-list.fxml";
    @FXML private VBox rootNode;
    private ObservableList<PlayerCategoryModel> playerCategoryModels;
    private PlayerCategoryListItemEditionController listItemEditionController;
    private Integer itemCount = 0;

    public void init(ObservableList<PlayerCategoryModel> playerCategoryModels) {
        this.playerCategoryModels = playerCategoryModels;
        playerCategoryModels.sorted().forEach(playerCategoryModel -> {
            insertItem(playerCategoryModel, itemCount);
        });
    }

    private void insertItem(PlayerCategoryModel playerCategoryModel, Integer rowIndex) {
        FXMLLoader fxmlLoader = InjectableFXMLLoader.load(PlayerCategoryListItemController.VIEW_NAME);
        PlayerCategoryListItemController listItemController = (PlayerCategoryListItemController) fxmlLoader.getController();
        listItemController.init(playerCategoryModel);
        listItemController.onEditRequested.setHandler(this::onItemEditRequestedHandler);
        listItemController.onDeleteRequested.setHandler(this::onItemDeleteRequestHandler);
        rootNode.getChildren().add(rowIndex, fxmlLoader.getRoot());
        itemCount += 1;
    }

    private void onItemEditRequestedHandler(Object sender, PlayerCategoryModel model) {
        enterItemEditionMode(model);
    }

    private void enterItemEditionMode(PlayerCategoryModel model) {
        validateItemEdition();
        FXMLLoader fxmlLoader = InjectableFXMLLoader.load(PlayerCategoryListItemEditionController.VIEW_NAME);
        listItemEditionController = (PlayerCategoryListItemEditionController) fxmlLoader.getController();
        listItemEditionController.init(model);
        listItemEditionController.onEditionValidationRequested.setHandler(this::onEditionValidationRequestedHandler);
        int rowIndex = playerCategoryModels.indexOf(model);
        rootNode.getChildren().remove(rowIndex);
        rootNode.getChildren().add(rowIndex, fxmlLoader.getRoot());
    }

    public void onEditionValidationRequestedHandler(Object sender, PlayerCategoryModel model) {
        validateItemEdition();
    }

    private void validateItemEdition() {
        if (listItemEditionController != null) {
            int rowIndex = playerCategoryModels.indexOf(listItemEditionController.getModel());
            rootNode.getChildren().remove(rowIndex);
            PlayerCategoryModel playerCategoryModel = listItemEditionController.getModel();
            insertItem(playerCategoryModel, rowIndex);
            playerCategoryModel.markDirty();
            listItemEditionController = null;
        }
    }

    public void newCategory() {
        validateItemEdition();
        PlayerCategoryModel playerCategoryModel = new PlayerCategoryModel();
        playerCategoryModels.add(0, playerCategoryModel);
        insertItem(playerCategoryModel, 0);
        enterItemEditionMode(playerCategoryModel);
    }

    private void onItemDeleteRequestHandler(Object sender, PlayerCategoryModel model) {
        deleteItem((PlayerCategoryListItemController) sender, model);
    }

    private void deleteItem(PlayerCategoryListItemController listItemController, PlayerCategoryModel model) {
        listItemController.hide();
        model.delete();
    }
}
