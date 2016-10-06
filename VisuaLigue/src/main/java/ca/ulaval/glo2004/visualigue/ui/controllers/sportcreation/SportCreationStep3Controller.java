package ca.ulaval.glo2004.visualigue.ui.controllers.sportcreation;

import ca.ulaval.glo2004.visualigue.GuiceFXMLLoader;
import ca.ulaval.glo2004.visualigue.ui.models.SportCreationModel;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class SportCreationStep3Controller extends SportCreationStepController {

    @FXML TextField sportNameField;
    @FXML VBox playerCategoryList;
    private PlayerCategoryListController playerCategoryListController;

    public SportCreationModel getModel() {
        return model;
    }

    @Override
    public void init(SportCreationModel sportCreation) {
        this.model = sportCreation;
        FXMLLoader fxmlLoader = GuiceFXMLLoader.load(PlayerCategoryListController.VIEW_NAME);
        playerCategoryListController = (PlayerCategoryListController) fxmlLoader.getController();
        playerCategoryListController.init(model.playerCategories);
        playerCategoryList.getChildren().add(fxmlLoader.getRoot());
    }

    @FXML
    public void onCategoryCreationLinkButtonClicked() {
        playerCategoryListController.newCategory();
    }
}
