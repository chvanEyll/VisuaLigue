package ca.ulaval.glo2004.visualigue.ui.controllers.sportcreation;

import ca.ulaval.glo2004.visualigue.InjectableFXMLLoader;
import ca.ulaval.glo2004.visualigue.ui.models.PlayerCategoryModel;
import ca.ulaval.glo2004.visualigue.utils.FXUtils;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.GridPane;

public class PlayerCategoryListController {

    public static final String VIEW_NAME = "/views/player-category-list.fxml";
    @FXML private GridPane playerCategoryGridPane;
    private ObservableList<PlayerCategoryModel> playerCategoryModels;
    private PlayerCategoryListItemEditionController listItemEditionController;
    private Integer itemCount = 0;

    public void init(ObservableList<PlayerCategoryModel> playerCategoryModels) {
        this.playerCategoryModels = playerCategoryModels;
        playerCategoryModels.forEach(playerCategoryModel -> {
            insertItem(playerCategoryModel, itemCount);
        });
    }

    private void insertItem(PlayerCategoryModel playerCategoryModel, Integer rowIndex) {
        FXMLLoader fxmlLoader = InjectableFXMLLoader.load(PlayerCategoryListItemController.VIEW_NAME, playerCategoryGridPane);
        PlayerCategoryListItemController listItemController = (PlayerCategoryListItemController) fxmlLoader.getController();
        listItemController.init(playerCategoryModel);
        listItemController.onEditRequested.setHandler(this::onItemEditRequestedHandler);
        listItemController.onDeleteRequested.setHandler(this::onItemDeleteRequestHandler);
        FXUtils.insertGridPaneRow(playerCategoryGridPane, rowIndex, listItemController.getChildren());
        itemCount += 1;
    }

    private void onItemEditRequestedHandler(Object sender, PlayerCategoryModel model) {
        enterItemEditionMode(model);
    }

    private void enterItemEditionMode(PlayerCategoryModel model) {
        int rowIndex = playerCategoryModels.indexOf(model);
        FXUtils.removeGridPaneRow(playerCategoryGridPane, rowIndex);
        validateItemEdition();
        FXMLLoader fxmlLoader = InjectableFXMLLoader.load(PlayerCategoryListItemEditionController.VIEW_NAME, playerCategoryGridPane);
        listItemEditionController = (PlayerCategoryListItemEditionController) fxmlLoader.getController();
        listItemEditionController.init(model);
        listItemEditionController.onEditionValidationRequested.setHandler(this::onEditionValidationRequestedHandler);
        FXUtils.insertGridPaneRow(playerCategoryGridPane, rowIndex, listItemEditionController.getChildren());
    }

    public void onEditionValidationRequestedHandler(Object sender, PlayerCategoryModel model) {
        validateItemEdition();
    }

    private void validateItemEdition() {
        if (listItemEditionController != null) {
            int itemIndex = playerCategoryModels.indexOf(listItemEditionController.getModel());
            FXUtils.removeGridPaneRow(playerCategoryGridPane, itemIndex);
            PlayerCategoryModel playerCategoryModel = listItemEditionController.getModel();
            insertItem(playerCategoryModel, itemIndex);
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
        deleteItem(model);
    }

    private void deleteItem(PlayerCategoryModel model) {
        int rowIndex = playerCategoryModels.indexOf(model);
        FXUtils.removeChildrenFromRow(playerCategoryGridPane, rowIndex);
        model.delete();
    }
}
