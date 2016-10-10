package ca.ulaval.glo2004.visualigue.ui.controllers.sportcreation;

import ca.ulaval.glo2004.visualigue.domain.sport.Sport;
import ca.ulaval.glo2004.visualigue.domain.sport.SportAlreadyExistsException;
import ca.ulaval.glo2004.visualigue.domain.sport.SportNotFoundException;
import ca.ulaval.glo2004.visualigue.services.SportService;
import ca.ulaval.glo2004.visualigue.ui.InjectableFXMLLoader;
import ca.ulaval.glo2004.visualigue.ui.controllers.Controller;
import ca.ulaval.glo2004.visualigue.ui.controllers.FileSelectionEventArgs;
import ca.ulaval.glo2004.visualigue.ui.controllers.common.BreadcrumbController;
import ca.ulaval.glo2004.visualigue.ui.converters.SportCreationModelConverter;
import ca.ulaval.glo2004.visualigue.ui.models.PlayerCategoryModel;
import ca.ulaval.glo2004.visualigue.ui.models.SportCreationModel;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javax.inject.Inject;

public class SportCreationController extends Controller {

    @FXML VBox stepContent;
    @FXML Button defaultButton;
    @FXML Button cancelButton;
    @FXML BreadcrumbController breadcrumbController;

    public static final String VIEW_TITLE = "Création d'un sport";
    public static final String VIEW_NAME = "/views/sport-creation/sport-creation.fxml";
    private static final int NUMBER_OF_STEPS = 3;
    private static final String STEPS_VIEW_NAMES[] = {
        "/views/sport-creation/sport-creation-step-1.fxml",
        "/views/sport-creation/sport-creation-step-2.fxml",
        "/views/sport-creation/sport-creation-step-3.fxml"
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
        model = new SportCreationModel();
        initView();
    }

    public void init(UUID sportUUID) throws SportNotFoundException {
        Sport sport = sportService.getSport(sportUUID);
        model = sportCreationModelConverter.convert(sport);
        initView();
    }

    private void initView() {
        breadcrumbController.addItem("Général");
        breadcrumbController.addItem("Terrain");
        breadcrumbController.addItem("Joueurs");
        breadcrumbController.onItemClicked.setHandler(this::onBreadcrumNavItemClickedHandler);
        setStep(GENERAL_STEP_INDEX);
    }

    private void onBreadcrumNavItemClickedHandler(Object sender, Integer index) {
        setStep(index);
    }

    private void setStep(int stepIndex) {
        if (currentStepIndex != stepIndex) {
            FXMLLoader fxmlLoader = InjectableFXMLLoader.load(STEPS_VIEW_NAMES[stepIndex]);
            currentStepController = (SportCreationStepController) fxmlLoader.getController();
            currentStepController.onFileSelectionRequested.setHandler(this::onFileSelectRequestedHandler);
            currentStepController.init(model);
            stepContent.getChildren().clear();
            stepContent.getChildren().add(fxmlLoader.getRoot());
            if (model.isNew() && stepIndex < NUMBER_OF_STEPS - 1) {
                defaultButton.setText("Continuer");
            } else if (model.isNew() && stepIndex == NUMBER_OF_STEPS - 1) {
                defaultButton.setText("Terminer");
            } else if (!model.isNew()) {
                defaultButton.setText("Sauvegarder");
            }
            breadcrumbController.setActiveItem(stepIndex);
            currentStepIndex = stepIndex;
        }
    }

    @FXML
    public void onDefaultButtonAction() {
        if (model.isNew() && currentStepIndex < NUMBER_OF_STEPS - 1) {
            setStep(currentStepIndex + 1);
        } else if (model.isNew() && currentStepIndex == NUMBER_OF_STEPS - 1) {
            trySaveChanges();
        } else if (!model.isNew()) {
            trySaveChanges();
        }
    }

    @FXML
    public void onCancelButtonAction() {
        onViewCloseRequested.fire(this, null);
    }

    private void onFileSelectRequestedHandler(Object sender, FileSelectionEventArgs eventArgs) {
        onFileSelectionRequested.fire(sender, eventArgs);
    }

    private void trySaveChanges() {
        currentStepController.clearErrors();
        try {
            saveChanges();
        } catch (SportAlreadyExistsException ex) {
            setStep(GENERAL_STEP_INDEX);
            currentStepController.showError(ex);
        } catch (Exception ex) {
            Logger.getLogger(SportCreationController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void saveChanges() throws Exception {
        UUID sportUuid;
        if (model.isNew()) {
            sportUuid = sportService.createSport(model.name.get());
        } else {
            sportUuid = model.getUUID();
            sportService.updateSport(sportUuid, model.name.get());
        }
        applyPlayingSurfaceChanges(sportUuid);
        applyCategoryChanges(sportUuid);
        onViewCloseRequested.fire(this, null);
    }

    private void applyPlayingSurfaceChanges(UUID sportUuid) throws Exception {
        sportService.updatePlayingSurface(sportUuid, model.playingSurfaceWidth.get(), model.playingSurfaceLength.get(), model.playingSurfaceWidthUnits.get(), model.playingSurfaceLengthUnits.get());
        if (model.newPlayingSurfaceImagePathName.isNotNull().get()) {
            sportService.updatePlayingSurfaceImage(sportUuid, model.newPlayingSurfaceImagePathName.get());
        }
    }

    private void applyCategoryChanges(UUID sportUuid) throws Exception {
        ObservableList<PlayerCategoryModel> playerCategoryModels = model.playerCategoryModels;
        for (PlayerCategoryModel playerCategoryModel : model.playerCategoryModels) {
            if (playerCategoryModel.isNew()) {
                sportService.addPlayerCategory(sportUuid, playerCategoryModel.name.get(), playerCategoryModel.allyPlayerColor.get(), playerCategoryModel.opponentPlayerColor.get(), playerCategoryModel.defaultNumberOfPlayers.get());
            } else if (playerCategoryModel.isDirty()) {
                sportService.updatePlayerCategory(sportUuid, playerCategoryModel.getUUID(), playerCategoryModel.name.get(), playerCategoryModel.allyPlayerColor.get(), playerCategoryModel.opponentPlayerColor.get(), playerCategoryModel.defaultNumberOfPlayers.get());
            } else if (playerCategoryModel.isDeleted()) {
                sportService.removePlayerCategory(sportUuid, playerCategoryModel.getUUID());
            }
        }
    }
}
