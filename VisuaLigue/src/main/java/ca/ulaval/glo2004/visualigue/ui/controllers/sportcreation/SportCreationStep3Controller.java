package ca.ulaval.glo2004.visualigue.ui.controllers.sportcreation;

import ca.ulaval.glo2004.visualigue.ui.models.SportCreationModel;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class SportCreationStep3Controller extends SportCreationStepController {

    @FXML TextField sportNameField;

    public SportCreationModel getModel() {
        return model;
    }

    @Override
    public void init(SportCreationModel sportCreation) {
        this.model = sportCreation;
    }
}
