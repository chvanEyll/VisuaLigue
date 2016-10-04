package ca.ulaval.glo2004.visualigue.ui.controllers;

import ca.ulaval.glo2004.visualigue.ui.models.SportModel;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class SportCreationStep1Controller extends SportCreationStepController {

    @FXML TextField sportNameField;

    public SportModel getSportModel() {
        return sportModel;
    }

    @Override
    public void setSportModel(SportModel sportModel) {
        this.sportModel = sportModel;
        sportNameField.textProperty().bindBidirectional(sportModel.name);
    }
}
