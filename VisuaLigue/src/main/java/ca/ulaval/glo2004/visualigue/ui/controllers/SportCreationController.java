package ca.ulaval.glo2004.visualigue.ui.controllers;

import ca.ulaval.glo2004.visualigue.GuiceFXMLLoader;
import ca.ulaval.glo2004.visualigue.domain.Sport;
import ca.ulaval.glo2004.visualigue.services.SportService;
import ca.ulaval.glo2004.visualigue.ui.models.SportModel;
import ca.ulaval.glo2004.visualigue.utils.FXUtils;
import ca.ulaval.visualigue.ui.converters.SportModelConverter;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javax.inject.Inject;

public class SportCreationController extends Controller {

    @FXML VBox stepContent;
    @FXML Button continueButton;
    @FXML Button finishButton;
    @FXML Button cancelButton;

    public static final String VIEW_NAME = "/views/sport-creation.fxml";
    private static final int NUMBER_OF_STEPS = 3;
    private static final String STEPS_VIEW_NAMES[] = {
        "/views/sport-creation-step-1.fxml",
        "/views/sport-creation-step-2.fxml",
        "/views/sport-creation-step-3.fxml"
    };
    private static final int INITIAL_STEP = 0;
    private SportModel sportModel;
    private int currentStep;
    @Inject private SportService sportService;
    @Inject private SportModelConverter sportModelConverter;

    public void setSportModel(SportModel sportModel) {
        this.sportModel = sportModel;
        setStep(INITIAL_STEP);
    }

    private void setStep(int step) {
        FXMLLoader fxmlLoader = GuiceFXMLLoader.load(STEPS_VIEW_NAMES[step]);
        SportCreationStepController controller = (SportCreationStepController) fxmlLoader.getController();
        controller.setModel(sportModel);
        stepContent.getChildren().clear();
        stepContent.getChildren().add(fxmlLoader.getRoot());
        FXUtils.setDisplay(continueButton, step < NUMBER_OF_STEPS - 1);
        FXUtils.setDisplay(finishButton, step == NUMBER_OF_STEPS - 1);
        currentStep = step;
    }

    public void onContinueButtonClick() {
        if (currentStep < NUMBER_OF_STEPS - 1) {
            setStep(currentStep + 1);
        } else {
            createSport();
        }
    }

    public void onCancelButtonClick() {
        onViewCloseRequest.fire(this, null);
    }

    public void createSport() {
        Sport sport = sportModelConverter.Convert(sportModel);
        sportService.createSport(sport);
        onViewCloseRequest.fire(this, null);
    }
}
