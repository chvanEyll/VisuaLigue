package ca.ulaval.glo2004.visualigue.ui.controllers.obstaclemanagement;

import ca.ulaval.glo2004.visualigue.VisuaLigue;
import ca.ulaval.glo2004.visualigue.domain.obstacle.ObstacleNotFoundException;
import ca.ulaval.glo2004.visualigue.services.obstacle.ObstacleService;
import ca.ulaval.glo2004.visualigue.ui.controllers.common.ListItemEditionController;
import ca.ulaval.glo2004.visualigue.ui.models.ModelBase;
import ca.ulaval.glo2004.visualigue.ui.models.ObstacleModel;
import ca.ulaval.glo2004.visualigue.utils.FXUtils;
import ca.ulaval.glo2004.visualigue.utils.FilenameUtils;
import java.io.File;
import java.util.UUID;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javax.inject.Inject;
import org.apache.commons.lang3.StringUtils;

public class ObstacleListItemEditionController extends ListItemEditionController {

    public static final String VIEW_NAME = "/views/obstaclemanagement/obstacle-list-item-edition.fxml";
    public static final Integer MIN_DEFAULT_NUMBER_OF_PLAYERS_VALUE = 0;
    public static final Integer MAX_DEFAULT_NUMBER_OF_PLAYERS_VALUE = 30;
    public static final Integer INITIAL_DEFAULT_NUMBER_OF_PLAYERS_VALUE = 0;
    public static final Integer STEP_DEFAULT_NUMBER_OF_PLAYERS_VALUE = 1;

    @Inject private ObstacleService obstacleService;
    @FXML private TextField nameTextField;
    @FXML private ImageView imageView;
    @FXML private Button revertButton;
    @FXML private Label nameErrorLabel;
    @FXML private Label imageErrorLabel;

    private ObstacleModel model;

    @Override
    public void init(ModelBase model) {
        this.model = (ObstacleModel) model;
        clearErrors();
        nameTextField.textProperty().set(this.model.name.get());
        updateImage();
        if (model.isNew()) {
            FXUtils.setDisplay(revertButton, false);
        }
        FXUtils.requestFocusDelayed(nameTextField);
    }

    @Override
    public ObstacleModel getModel() {
        return model;
    }

    @FXML
    protected void onValidateButtonAction(ActionEvent e) throws ObstacleNotFoundException {
        if (validate()) {
            if (model.isNew()) {
                UUID obstacleInstanceUUID = obstacleService.createObstacle(StringUtils.trim(model.name.get()));
                model.setUUID(obstacleInstanceUUID);
                model.setIsNew(false);
            } else {
                obstacleService.updateObstacle(model.getUUID(), StringUtils.trim(model.name.get()));
            }
            if (model.newImagePathName.isNotEmpty().get()) {
                obstacleService.updateObstacleImage(model.getUUID(), model.newImagePathName.get());
                model.currentImagePathName.set(model.newImagePathName.get());
                model.newImagePathName.set(null);
            }
            model.name.set(nameTextField.getText());
            onCloseRequested.fire(this, model);
        }
    }

    @FXML
    protected void onRevertButtonAction(ActionEvent e) {
        model.newImagePathName.set(null);
        onCloseRequested.fire(this, model);
    }

    @FXML
    protected void onBrowseImageButtonAction(ActionEvent e) {
        File selectedFile = FXUtils.chooseImage(VisuaLigue.getMainStage());
        if (selectedFile != null) {
            this.model.newImagePathName.set(selectedFile.getPath());
            updateImage();
        }
    }

    private void updateImage() {
        if (model.newImagePathName.isNotEmpty().get()) {
            displayImage(FilenameUtils.getURIString(model.newImagePathName.get()));
        } else if (model.currentImagePathName.isNotEmpty().get()) {
            displayImage(FilenameUtils.getURIString(model.currentImagePathName.get()));
        } else if (model.builtInImagePathName.isNotEmpty().get()) {
            displayImage(model.builtInImagePathName.get());
        } else {
            clearImage();
        }
    }

    private void displayImage(String imageURL) {
        try {
            imageView.setImage(new Image(imageURL));
            imageView.setFitWidth(45);
            imageView.setFitHeight(45);
        } catch (Exception ex) {
            clearErrors();
            imageErrorLabel.setText("L'image sélectionnée n'a pu être chargée.");
            FXUtils.setDisplay(imageErrorLabel, true);
        }
    }

    private void clearImage() {
        imageView.setImage(null);
    }

    private Boolean validate() {
        clearErrors();
        if (ca.ulaval.glo2004.visualigue.utils.StringUtils.isBlank(nameTextField.getText())) {
            nameErrorLabel.setText("Le nom de l'obstacle doit être spécifié.");
            FXUtils.setDisplay(nameErrorLabel, true);
        } else {
            return true;
        }
        return false;
    }

    private void clearErrors() {
        FXUtils.setDisplay(nameErrorLabel, false);
        FXUtils.setDisplay(imageErrorLabel, false);
    }
}
