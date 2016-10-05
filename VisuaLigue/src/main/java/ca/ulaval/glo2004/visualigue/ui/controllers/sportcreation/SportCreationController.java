package ca.ulaval.glo2004.visualigue.ui.controllers.sportcreation;

import ca.ulaval.glo2004.visualigue.GuiceFXMLLoader;
import ca.ulaval.glo2004.visualigue.domain.Sport;
import ca.ulaval.glo2004.visualigue.services.SportService;
import ca.ulaval.glo2004.visualigue.ui.controllers.BreadcrumbNavController;
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
    @FXML BreadcrumbNavController breadcrumbNavController;

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
    private int currentStepIndex;
    @Inject private SportService sportService;
    @Inject private SportCreationModelConverter sportCreationModelConverter;

    @Override
    public StringProperty getTitle() {
        return model.name;
    }

    public void init(SportCreationModel model) {
        this.model = model;
        breadcrumbNavController.addItem("Général");
        breadcrumbNavController.addItem("Terrain");
        breadcrumbNavController.addItem("Joueurs");
        breadcrumbNavController.onItemClicked.addHandler(this::onBreadcrumNavItemClickedHandler);
        setStep(INITIAL_STEP);
    }

    private void onBreadcrumNavItemClickedHandler(Object sender, Integer index) {
        setStep(index);
    }

    private void setStep(int stepIndex) {
        FXMLLoader fxmlLoader = GuiceFXMLLoader.load(STEPS_VIEW_NAMES[stepIndex]);
        SportCreationStepController controller = (SportCreationStepController) fxmlLoader.getController();
        controller.onFileSelectionRequested.setHandler(this::onFileSelectRequestedHandler);
        controller.init(model);
        stepContent.getChildren().clear();
        stepContent.getChildren().add(fxmlLoader.getRoot());
        FXUtils.setDisplay(continueButton, stepIndex < NUMBER_OF_STEPS - 1);
        FXUtils.setDisplay(finishButton, stepIndex == NUMBER_OF_STEPS - 1);
        breadcrumbNavController.setActiveItem(stepIndex);
        currentStepIndex = stepIndex;
    }

    public void onContinueButtonClick() {
        if (currentStepIndex < NUMBER_OF_STEPS - 1) {
            setStep(currentStepIndex + 1);
        } else {
            createSport();
        }
    }

    public void onCancelButtonClick() {
        onViewCloseRequested.fire(this, null);
    }

    private void onFileSelectRequestedHandler(Object sender, FileSelectionEventArgs eventArgs) {
        onFileSelectionRequested.fire(sender, eventArgs);
    }

    public void createSport() {
        Sport sport = sportCreationModelConverter.Convert(model);
        sportService.createSport(sport);
        onViewCloseRequested.fire(this, null);
    }
}
