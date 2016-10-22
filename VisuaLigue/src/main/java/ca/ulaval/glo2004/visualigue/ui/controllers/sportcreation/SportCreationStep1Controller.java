package ca.ulaval.glo2004.visualigue.ui.controllers.sportcreation;

import ca.ulaval.glo2004.visualigue.VisuaLigue;
import ca.ulaval.glo2004.visualigue.domain.sport.SportAlreadyExistsException;
import ca.ulaval.glo2004.visualigue.ui.customcontrols.ResizableImageView;
import ca.ulaval.glo2004.visualigue.ui.models.SportCreationModel;
import ca.ulaval.glo2004.visualigue.utils.FXUtils;
import ca.ulaval.glo2004.visualigue.utils.FilenameUtils;
import ca.ulaval.glo2004.visualigue.utils.StringUtils;
import java.io.File;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;

public class SportCreationStep1Controller extends SportCreationStepController {

    @FXML private TextField sportNameField;
    @FXML private Label sportNameErrorLabel;
    @FXML private TextField ballNameField;
    @FXML private Label ballNameErrorLabel;
    @FXML private Label imagePathLabel;
    @FXML private ResizableImageView imageView;
    @FXML private Label imageErrorLabel;

    @Override
    public void init(SportCreationModel sportCreation) {
        model = sportCreation;
        sportNameField.textProperty().bindBidirectional(sportCreation.name);
        ballNameField.textProperty().bindBidirectional(sportCreation.ballName);
        updateImage();
        FXUtils.requestFocusDelayed(sportNameField);
        super.init();
    }

    @FXML
    public void onBrowseImageButtonAction(ActionEvent e) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Image File");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.bmp", "*.gif", "*.tiff"));
        File selectedFile = fileChooser.showOpenDialog(VisuaLigue.getMainStage());
        if (selectedFile != null) {
            this.model.newBallImagePathName.set(selectedFile.getPath());
            updateImage();
        }
    }

    private void updateImage() {
        if (model.newBallImagePathName.isNotEmpty().get()) {
            displayImage(FilenameUtils.getURIString(model.newBallImagePathName.get()));
        } else if (model.currentBallImagePathName.isNotEmpty().get()) {
            displayImage(FilenameUtils.getURIString(model.currentBallImagePathName.get()));
        } else if (model.builtInBallImagePathName.isNotEmpty().get()) {
            displayImage(model.builtInBallImagePathName.get());
        } else {
            clearImage();
        }
    }

    private void displayImage(String imageURL) {
        try {
            imageView.setImage(new Image(imageURL));
            imagePathLabel.textProperty().bind(this.model.newPlayingSurfaceImagePathName);
        } catch (Exception ex) {
            clearErrors();
            imageErrorLabel.setText("L'image sélectionnée n'a pu être chargée.");
            FXUtils.setDisplay(imageErrorLabel, true);
        }
    }

    private void clearImage() {
        imageView.setImage(null);
        imagePathLabel.textProperty().unbind();
        imagePathLabel.setText("Aucune image sélectionnée");
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
        } else if (StringUtils.isBlank(ballNameField.getText())) {
            ballNameErrorLabel.setText("Le nom de l'objet d'échange doit être spécifié.");
            FXUtils.setDisplay(ballNameErrorLabel, true);

        } else {
            return true;
        }
        return false;
    }
}
