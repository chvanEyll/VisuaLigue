package ca.ulaval.glo2004.visualigue.ui.controllers.sportcreation;

import ca.ulaval.glo2004.visualigue.domain.playercategory.PlayerCategory;
import ca.ulaval.glo2004.visualigue.domain.sport.Sport;
import ca.ulaval.glo2004.visualigue.domain.sport.SportAlreadyExistsException;
import ca.ulaval.glo2004.visualigue.services.SportService;
import ca.ulaval.glo2004.visualigue.ui.InjectableFXMLLoader;
import ca.ulaval.glo2004.visualigue.ui.controllers.Controller;
import ca.ulaval.glo2004.visualigue.ui.controllers.FileSelectionEventArgs;
import ca.ulaval.glo2004.visualigue.ui.controllers.common.BreadcrumbController;
import ca.ulaval.glo2004.visualigue.ui.converters.SportCreationModelConverter;
import ca.ulaval.glo2004.visualigue.ui.models.SportCreationModel;
import ca.ulaval.glo2004.visualigue.utils.ColorUtils;
import javafx.beans.property.StringProperty;
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
        model = new SportCreationModel();
        initView();
    }

    public void init(Sport sport) {
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
            if (model.isNew() && currentStepIndex < NUMBER_OF_STEPS - 1) {
                defaultButton.setText("Continuer");
            } else if (model.isNew() && currentStepIndex == NUMBER_OF_STEPS - 1) {
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
        }
    }

    private void saveChanges() throws SportAlreadyExistsException {
        Sport sport;
        if (model.isNew()) {
            sport = sportService.createSport(model.name.get());
        } else {
            sport = (Sport) model.getAssociatedEntity();
            sportService.updateSport(sport, model.name.get());
        }
        sportService.updatePlayingSurface(sport, model.playingSurfaceWidth.get(), model.playingSurfaceLength.get(), model.playingSurfaceWidthUnits.get(), model.playingSurfaceLengthUnits.get(), model.playingSurfaceImageFileName.get());
        applyCategoryChanges(sport);
        onViewCloseRequested.fire(this, null);
    }

    private void applyCategoryChanges(Sport sport) {
        model.playerCategoryModels.forEach(playerCategoryModel -> {
            if (playerCategoryModel.isNew()) {
                sportService.addPlayerCategory(sport, playerCategoryModel.name.get(), ColorUtils.FXColorToAWTColor(playerCategoryModel.allyPlayerColor.get()), ColorUtils.FXColorToAWTColor(playerCategoryModel.opponentPlayerColor.get()), playerCategoryModel.defaultNumberOfPlayers.get());
            } else if (playerCategoryModel.isDirty()) {
                sportService.updatePlayerCategory(sport, (PlayerCategory) playerCategoryModel.getAssociatedEntity(), playerCategoryModel.name.get(), ColorUtils.FXColorToAWTColor(playerCategoryModel.allyPlayerColor.get()), ColorUtils.FXColorToAWTColor(playerCategoryModel.opponentPlayerColor.get()), playerCategoryModel.defaultNumberOfPlayers.get());
            } else if (playerCategoryModel.isDeleted()) {
                sportService.removePlayerCategory(sport, (PlayerCategory) playerCategoryModel.getAssociatedEntity());
            }
        });
    }
}
