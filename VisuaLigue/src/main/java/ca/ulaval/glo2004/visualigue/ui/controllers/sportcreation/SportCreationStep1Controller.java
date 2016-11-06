package ca.ulaval.glo2004.visualigue.ui.controllers.sportcreation;

import ca.ulaval.glo2004.visualigue.VisuaLigueFX;
import ca.ulaval.glo2004.visualigue.domain.sport.SportAlreadyExistsException;
import ca.ulaval.glo2004.visualigue.ui.models.SportCreationModel;
import ca.ulaval.glo2004.visualigue.utils.FilenameUtils;
import ca.ulaval.glo2004.visualigue.utils.StringUtils;
import ca.ulaval.glo2004.visualigue.utils.javafx.FXUtils;
import java.io.File;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class SportCreationStep1Controller extends SportCreationStepController {

    @FXML private TextField sportNameField;
    @FXML private Label sportNameErrorLabel;
    @FXML private ImageView iconImageView;
    @FXML private Label iconImageErrorLabel;
    @FXML private TextField ballNameField;
    @FXML private Label ballNameErrorLabel;
    @FXML private ImageView ballImageView;
    @FXML private Label ballImageErrorLabel;

    @Override
    public void init(SportCreationModel sportCreation) {
        model = sportCreation;
        sportNameField.textProperty().bindBidirectional(sportCreation.name);
        ballNameField.textProperty().bindBidirectional(sportCreation.ballName);
        clearErrors();
        updateIconImage();
        updateBallImage();
        FXUtils.requestFocusDelayed(sportNameField);
    }

    @FXML
    protected void onBrowseBallImageButtonAction(ActionEvent e) {
        File selectedFile = FXUtils.chooseImage(VisuaLigueFX.getMainStage());
        if (selectedFile != null) {
            this.model.newBallImagePathName.set(selectedFile.getPath());
            updateBallImage();
        }
    }

    @FXML
    protected void onBrowseIconImageButtonAction(ActionEvent e) {
        File selectedFile = FXUtils.chooseImage(VisuaLigueFX.getMainStage());
        if (selectedFile != null) {
            this.model.newIconPathName.set(selectedFile.getPath());
            updateIconImage();
        }
    }

    private void updateBallImage() {
        if (model.newBallImagePathName.isNotEmpty().get()) {
            displayImage(ballImageView, ballImageErrorLabel, FilenameUtils.getURIString(model.newBallImagePathName.get()));
        } else if (model.currentBallImagePathName.isNotEmpty().get()) {
            displayImage(ballImageView, ballImageErrorLabel, FilenameUtils.getURIString(model.currentBallImagePathName.get()));
        } else if (model.builtInBallImagePathName.isNotEmpty().get()) {
            displayImage(ballImageView, ballImageErrorLabel, model.builtInBallImagePathName.get());
        } else {
            clearImage(ballImageView);
        }
    }

    private void updateIconImage() {
        if (model.newIconPathName.isNotEmpty().get()) {
            displayImage(iconImageView, iconImageErrorLabel, FilenameUtils.getURIString(model.newIconPathName.get()));
        } else if (model.currentIconPathName.isNotEmpty().get()) {
            displayImage(iconImageView, iconImageErrorLabel, FilenameUtils.getURIString(model.currentIconPathName.get()));
        } else if (model.builtInIconPathName.isNotEmpty().get()) {
            displayImage(iconImageView, iconImageErrorLabel, model.builtInIconPathName.get());
        } else {
            clearImage(iconImageView);
        }
    }

    private void displayImage(ImageView imageView, Label imageErrorLabel, String imageURL) {
        try {
            imageView.setImage(new Image(imageURL));
        } catch (Exception ex) {
            clearErrors();
            imageErrorLabel.setText("L'image spécifiée n'a pu être chargée.");
            FXUtils.setDisplay(imageErrorLabel, true);
        }
    }

    private void clearImage(ImageView imageView) {
        imageView.setImage(null);
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
        FXUtils.setDisplay(iconImageErrorLabel, false);
        FXUtils.setDisplay(ballNameErrorLabel, false);
        FXUtils.setDisplay(ballImageErrorLabel, false);
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
