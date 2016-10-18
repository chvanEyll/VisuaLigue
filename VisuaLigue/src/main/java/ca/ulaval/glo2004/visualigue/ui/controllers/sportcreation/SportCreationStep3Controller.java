package ca.ulaval.glo2004.visualigue.ui.controllers.sportcreation;

import ca.ulaval.glo2004.visualigue.ui.models.SportCreationModel;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class SportCreationStep3Controller extends SportCreationStepController {

    @FXML TextField sportNameField;
    @FXML PlayerCategoryListController playerCategoryListController;

    @Override
    public void init(SportCreationModel sportCreation) {
        this.model = sportCreation;
        playerCategoryListController.init(model.playerCategoryModels);
        super.init();
    }

    @FXML
    public void onCategoryCreationLinkButtonClicked() {
        playerCategoryListController.newCategory();
    }
}
