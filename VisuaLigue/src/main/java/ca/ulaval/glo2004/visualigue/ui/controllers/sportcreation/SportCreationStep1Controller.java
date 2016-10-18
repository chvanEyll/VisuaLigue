package ca.ulaval.glo2004.visualigue.ui.controllers.sportcreation;

import ca.ulaval.glo2004.visualigue.domain.sport.SportAlreadyExistsException;
import ca.ulaval.glo2004.visualigue.ui.models.SportCreationModel;
import ca.ulaval.glo2004.visualigue.utils.FXUtils;
import ca.ulaval.glo2004.visualigue.utils.StringUtils;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class SportCreationStep1Controller extends SportCreationStepController {

    @FXML private TextField sportNameField;
    @FXML private Label sportNameErrorLabel;

    @Override
    public void init(SportCreationModel sportCreation) {
        model = sportCreation;
        sportNameField.textProperty().bindBidirectional(sportCreation.name);
        FXUtils.requestFocusDelayed(sportNameField);
        super.init();
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

    @Override
    public Boolean validate() {
        clearErrors();
        if (StringUtils.isBlank(sportNameField.getText())) {
            sportNameErrorLabel.setText("Le nom du sport doit être spécifié.");
            FXUtils.setDisplay(sportNameErrorLabel, true);
        } else {
            return true;
        }
        return false;
    }
}
