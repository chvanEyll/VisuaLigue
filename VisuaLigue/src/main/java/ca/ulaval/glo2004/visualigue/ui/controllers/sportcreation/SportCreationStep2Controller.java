package ca.ulaval.glo2004.visualigue.ui.controllers.sportcreation;

import ca.ulaval.glo2004.visualigue.domain.PlayingSurfaceUnit;
import ca.ulaval.glo2004.visualigue.ui.controllers.FileSelectionEventArgs;
import ca.ulaval.glo2004.visualigue.ui.models.SportCreationModel;
import ca.ulaval.glo2004.visualigue.utils.FXUtils;
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

    public SportCreationModel getSportModel() {
        return model;
    }

    @Override
    public void init(SportCreationModel sportCreation) {
        this.model = sportCreation;
        widthSpinner.setValueFactory(new DoubleSpinnerValueFactory(MIN_SIZE_VALUE, MAX_SIZE_VALUE, INITIAL_WIDTH_VALUE, STEP_SIZE_VALUE));
        widthSpinner.getValueFactory().valueProperty().bindBidirectional(model.playingSurfaceWidth);
        lengthSpinner.setValueFactory(new DoubleSpinnerValueFactory(MIN_SIZE_VALUE, MAX_SIZE_VALUE, INITIAL_LENGTH_VALUE, STEP_SIZE_VALUE));
        lengthSpinner.getValueFactory().valueProperty().bindBidirectional(model.playingSurfaceLength);
        widthUnitComboBox.setItems(FXCollections.observableArrayList(PlayingSurfaceUnit.values()));
        widthUnitComboBox.getSelectionModel().select(model.playingSurfaceWidthUnits);
        lengthUnitComboBox.setItems(FXCollections.observableArrayList(PlayingSurfaceUnit.values()));
        lengthUnitComboBox.getSelectionModel().select(model.playingSurfaceLengthUnits);
        updateImage(model.playingSurfaceImageFileName.getValue());
    }

    private void updateImage(String imageFileName) {
        if (imageFileName == null) {
            clearImage();
        } else {
            this.model.playingSurfaceImageFileName.set(imageFileName);
            imagePathLabel.textProperty().bindBidirectional(this.model.playingSurfaceImageFileName);
            imageView.setImage(new Image(imageFileName));
        }
    }

    @FXML
    private void onBrowseImageButtonClick() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Image File");
        fileChooser.getExtensionFilters().addAll(new ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.bmp", "*.gif", "*.tiff"));

        FileSelectionEventArgs fileSelectionEventArgs = new FileSelectionEventArgs(fileChooser);
        onFileSelectionRequest.fire(this, fileSelectionEventArgs);
        if (fileSelectionEventArgs.selectedFile != null) {
            updateImage(fileSelectionEventArgs.selectedFile.getPath());
        }
    }

    private void clearImage() {
        FXUtils.setDisplay(imageView, false);
        imagePathLabel.textProperty().unbind();
        imagePathLabel.setText("Aucune image sélectionnée");
    }
}
