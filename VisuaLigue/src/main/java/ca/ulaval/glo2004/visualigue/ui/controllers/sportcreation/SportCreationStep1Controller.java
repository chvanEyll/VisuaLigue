package ca.ulaval.glo2004.visualigue.ui.controllers.sportcreation;

import ca.ulaval.glo2004.visualigue.domain.sport.SportAlreadyExistsException;
import ca.ulaval.glo2004.visualigue.ui.models.SportCreationModel;
import ca.ulaval.glo2004.visualigue.utils.FXUtils;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class SportCreationStep1Controller extends SportCreationStepController {

    @FXML TextField sportNameField;
    @FXML Label sportNameErrorLabel;

    public SportCreationModel getModel() {
        return model;
    }

    @Override
    public void init(SportCreationModel sportCreation) {
        model = sportCreation;
        sportNameField.textProperty().bindBidirectional(sportCreation.name);
        clearErrors();
        FXUtils.requestFocusDelayed(sportNameField);
    }

    @Override
    public void showError(Exception ex) {
        clearErrors();
        if (ex instanceof SportAlreadyExistsException) {
            FXUtils.setDisplay(sportNameErrorLabel, true);
        }
    }

    @Override
    public void clearErrors() {
        FXUtils.setDisplay(sportNameErrorLabel, false);
    }
}
