package ca.ulaval.glo2004.visualigue.ui.controllers.sportcreation;

import ca.ulaval.glo2004.visualigue.GuiceFXMLLoader;
import ca.ulaval.glo2004.visualigue.domain.Sport;
import ca.ulaval.glo2004.visualigue.services.SportService;
import ca.ulaval.glo2004.visualigue.ui.controllers.Controller;
import ca.ulaval.glo2004.visualigue.ui.controllers.FileSelectionEventArgs;
import ca.ulaval.glo2004.visualigue.ui.converters.SportCreationModelConverter;
import ca.ulaval.glo2004.visualigue.ui.models.SportCreationModel;
import ca.ulaval.glo2004.visualigue.utils.FXUtils;
import javafx.beans.property.StringProperty;
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

    public static final String VIEW_TITLE = "Création d'un sport";
    public static final String VIEW_NAME = "/views/sport-creation.fxml";
    private static final int NUMBER_OF_STEPS = 3;
    private static final String STEPS_VIEW_NAMES[] = {
        "/views/sport-creation-step-1.fxml",
        "/views/sport-creation-step-2.fxml",
        "/views/sport-creation-step-3.fxml"
    };
    private static final int INITIAL_STEP = 0;
    private SportCreationModel model;
    private int currentStep;
    @Inject private SportService sportService;
    @Inject private SportCreationModelConverter sportModelConverter;

    @Override
    public StringProperty getTitle() {
        return model.name;
    }

    public void init(SportCreationModel model) {
        this.model = model;
        setStep(INITIAL_STEP);
    }

    private void setStep(int step) {
        FXMLLoader fxmlLoader = GuiceFXMLLoader.load(STEPS_VIEW_NAMES[step]);
        SportCreationStepController controller = (SportCreationStepController) fxmlLoader.getController();
        controller.onFileSelectionRequest.setHandler(this::onFileSelectRequestHandler);
        controller.init(model);
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

    private void onFileSelectRequestHandler(Object sender, FileSelectionEventArgs eventArgs) {
        onFileSelectionRequest.fire(sender, eventArgs);
    }

    public void createSport() {
        Sport sport = sportModelConverter.Convert(model);
        sportService.createSport(sport);
        onViewCloseRequest.fire(this, null);
    }
}
