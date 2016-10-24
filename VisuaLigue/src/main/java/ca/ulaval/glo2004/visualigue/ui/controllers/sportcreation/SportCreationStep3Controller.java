package ca.ulaval.glo2004.visualigue.ui.controllers.sportcreation;

import ca.ulaval.glo2004.visualigue.ui.controllers.common.EditableListController;
import ca.ulaval.glo2004.visualigue.ui.models.PlayerCategoryModel;
import ca.ulaval.glo2004.visualigue.ui.models.SportCreationModel;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class SportCreationStep3Controller extends SportCreationStepController {

    @FXML private TextField sportNameField;
    @FXML private EditableListController playerCategoryListController;

    @Override
    public void init(SportCreationModel sportCreationModel) {
        this.model = sportCreationModel;
        playerCategoryListController.init(model.playerCategoryModels, PlayerCategoryModel.class,
                PlayerCategoryListItemController.VIEW_NAME, PlayerCategoryListItemEditionController.VIEW_NAME);
        clearErrors();
    }

    @FXML
    protected void onCategoryCreationLinkButtonClicked(MouseEvent e) {
        playerCategoryListController.newItem();
    }
}
