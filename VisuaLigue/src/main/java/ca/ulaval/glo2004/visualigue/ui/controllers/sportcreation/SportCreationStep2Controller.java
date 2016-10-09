package ca.ulaval.glo2004.visualigue.ui.controllers.sportcreation;

import ca.ulaval.glo2004.visualigue.domain.playingsurface.PlayingSurfaceUnit;
import ca.ulaval.glo2004.visualigue.ui.controllers.FileSelectionEventArgs;
import ca.ulaval.glo2004.visualigue.ui.models.SportCreationModel;
import ca.ulaval.glo2004.visualigue.utils.FXUtils;
import ca.ulaval.glo2004.visualigue.utils.FilenameUtils;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory.DoubleSpinnerValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

public class SportCreationStep2Controller extends SportCreationStepController {

    private static final Double MIN_SIZE_VALUE = 1.0;
    private static final Double MAX_SIZE_VALUE = 100000.0;
    private static final Double INITIAL_WIDTH_VALUE = 100.0;
    private static final Double INITIAL_LENGTH_VALUE = 200.0;
    private static final Double STEP_SIZE_VALUE = 1.0;

    @FXML Spinner widthSpinner;
    @FXML Spinner lengthSpinner;
    @FXML ComboBox widthUnitComboBox;
    @FXML ComboBox lengthUnitComboBox;
    @FXML Label imagePathLabel;
    @FXML ImageView imageView;
    @FXML Label imageErrorLabel;

    public SportCreationModel getSportModel() {
        return model;
    }

    @Override
    public void init(SportCreationModel sportCreation) {
        model = sportCreation;
        widthSpinner.setValueFactory(new DoubleSpinnerValueFactory(MIN_SIZE_VALUE, MAX_SIZE_VALUE, INITIAL_WIDTH_VALUE, STEP_SIZE_VALUE));
        widthSpinner.getValueFactory().valueProperty().bindBidirectional(model.playingSurfaceWidth);
        lengthSpinner.setValueFactory(new DoubleSpinnerValueFactory(MIN_SIZE_VALUE, MAX_SIZE_VALUE, INITIAL_LENGTH_VALUE, STEP_SIZE_VALUE));
        lengthSpinner.getValueFactory().valueProperty().bindBidirectional(model.playingSurfaceLength);
        widthUnitComboBox.setItems(FXCollections.observableArrayList(PlayingSurfaceUnit.values()));
        widthUnitComboBox.getSelectionModel().select(model.playingSurfaceWidthUnits.get());
        lengthUnitComboBox.setItems(FXCollections.observableArrayList(PlayingSurfaceUnit.values()));
        lengthUnitComboBox.getSelectionModel().select(model.playingSurfaceLengthUnits.get());
        displayImage(model.currentPlayingSurfaceImage.get());
        FXUtils.requestFocusDelayed(widthSpinner);
    }

    private void displayImage(Image image) {
        if (image == null) {
            clearImage();
        } else {
            imageView.setImage(image);
            FXUtils.setDisplay(imageView, true);
            imagePathLabel.textProperty().bind(this.model.newPlayingSurfaceImagePathName);
        }
    }

    private void setImage(String imagePathName) {
        try {
            this.model.newPlayingSurfaceImage.set(new Image(FilenameUtils.getURIString(imagePathName)));
        } catch (Exception ex) {
            clearErrors();
            imageErrorLabel.setText("The selected image could not be loaded.");
            FXUtils.setDisplay(imageErrorLabel, true);
        }
        this.model.newPlayingSurfaceImagePathName.set(imagePathName);
        displayImage(new Image(FilenameUtils.getURIString(imagePathName)));
    }

    @FXML
    public void onBrowseImageButtonAction() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Image File");
        fileChooser.getExtensionFilters().addAll(new ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.bmp", "*.gif", "*.tiff"));

        FileSelectionEventArgs fileSelectionEventArgs = new FileSelectionEventArgs(fileChooser);
        onFileSelectionRequested.fire(this, fileSelectionEventArgs);
        if (fileSelectionEventArgs.selectedFile != null) {
            setImage(fileSelectionEventArgs.selectedFile.getPath());
        }
    }

    private void clearImage() {
        FXUtils.setDisplay(imageView, false);
        imagePathLabel.textProperty().unbind();
        imagePathLabel.setText("Aucune image sélectionnée");
    }

    @Override
    public void clearErrors() {
        FXUtils.setDisplay(imageErrorLabel, false);
    }
}
