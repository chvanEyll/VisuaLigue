package ca.ulaval.glo2004.visualigue.ui.controllers.sportcreation;

import ca.ulaval.glo2004.visualigue.GuiceFXMLLoader;
import ca.ulaval.glo2004.visualigue.domain.sport.Sport;
import ca.ulaval.glo2004.visualigue.domain.sport.SportNameAlreadyInUseException;
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
    @FXML Button saveButton;
    @FXML BreadcrumbNavController breadcrumbNavController;

    public static final String VIEW_TITLE = "Création d'un sport";
    public static final String VIEW_NAME = "/views/sport-creation.fxml";
    private static final int NUMBER_OF_STEPS = 3;
    private static final String STEPS_VIEW_NAMES[] = {
        "/views/sport-creation-step-1.fxml",
        "/views/sport-creation-step-2.fxml",
        "/views/sport-creation-step-3.fxml"
    };
    private static final int GENERAL_STEP_INDEX = 0;
    private static final int PLAYING_SURFACE_STEP_INDEX = 1;
    private static final int PLAYERS_STEP_INDEX = 2;
    private SportCreationModel model;
    private int currentStepIndex = -1;
    private SportCreationStepController currentStepController;
    @Inject private SportService sportService;
    @Inject private SportCreationModelConverter sportCreationModelConverter;

    @Override
    public StringProperty getTitle() {
        return model.name;
    }

    public void init() {
        model = new SportCreationModel("Nouveau sport");
        initView();
    }

    public void init(Sport sport) {
        model = sportCreationModelConverter.convert(sport);
        initView();
    }

    private void initView() {
        breadcrumbNavController.addItem("Général");
        breadcrumbNavController.addItem("Terrain");
        breadcrumbNavController.addItem("Joueurs");
        breadcrumbNavController.onItemClicked.addHandler(this::onBreadcrumNavItemClickedHandler);
        setStep(GENERAL_STEP_INDEX);
    }

    private void onBreadcrumNavItemClickedHandler(Object sender, Integer index) {
        setStep(index);
    }

    private void setStep(int stepIndex) {
        if (currentStepIndex != stepIndex) {
            FXMLLoader fxmlLoader = GuiceFXMLLoader.load(STEPS_VIEW_NAMES[stepIndex]);
            currentStepController = (SportCreationStepController) fxmlLoader.getController();
            currentStepController.onFileSelectionRequested.setHandler(this::onFileSelectRequestedHandler);
            currentStepController.init(model);
            stepContent.getChildren().clear();
            stepContent.getChildren().add(fxmlLoader.getRoot());
            FXUtils.setDisplay(continueButton, stepIndex < NUMBER_OF_STEPS - 1 && !model.hasAssociatedSport());
            FXUtils.setDisplay(finishButton, stepIndex == NUMBER_OF_STEPS - 1 && !model.hasAssociatedSport());
            FXUtils.setDisplay(saveButton, model.hasAssociatedSport());
            breadcrumbNavController.setActiveItem(stepIndex);
            currentStepIndex = stepIndex;
        }
    }

    public void onContinueButtonClick() {
        setStep(currentStepIndex + 1);
    }

    public void onFinishButtonClick() {
        tryApplyChanges();
    }

    public void onSaveButtonClick() {
        tryApplyChanges();
    }

    public void onCancelButtonClick() {
        onViewCloseRequested.fire(this, null);
    }

    private void onFileSelectRequestedHandler(Object sender, FileSelectionEventArgs eventArgs) {
        onFileSelectionRequested.fire(sender, eventArgs);
    }

    public void tryApplyChanges() {
        currentStepController.clearErrors();
        try {
            applyChanges();
        } catch (SportNameAlreadyInUseException ex) {
            setStep(GENERAL_STEP_INDEX);
            currentStepController.showError(ex);
        }
    }

    public void applyChanges() throws SportNameAlreadyInUseException {
        if (model.hasAssociatedSport()) {
            sportService.updateSport(model.associatedSport, model.name.get());
        } else {
            model.associatedSport = sportService.createSport(model.name.get());
        }
        sportService.updateSportPlayingSurface(model.associatedSport, model.playingSurfaceWidth.get(), model.playingSurfaceLength.get(), model.playingSurfaceWidthUnits.get(),
                model.playingSurfaceLengthUnits.get(), model.playingSurfaceImageFileName.get());
        onViewCloseRequested.fire(this, null);
    }
}
