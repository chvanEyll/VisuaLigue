package ca.ulaval.glo2004.visualigue.ui.controllers.sportcreation;

import ca.ulaval.glo2004.visualigue.VisuaLigue;
import ca.ulaval.glo2004.visualigue.domain.playingsurface.PlayingSurfaceUnit;
import ca.ulaval.glo2004.visualigue.ui.customcontrols.ResizableImageView;
import ca.ulaval.glo2004.visualigue.ui.models.SportCreationModel;
import ca.ulaval.glo2004.visualigue.utils.FXUtils;
import ca.ulaval.glo2004.visualigue.utils.FilenameUtils;
import java.io.File;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory.DoubleSpinnerValueFactory;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

public class SportCreationStep2Controller extends SportCreationStepController {

    private static final Double MIN_SIZE_VALUE = 1.0;
    private static final Double MAX_SIZE_VALUE = 100000.0;
    private static final Double INITIAL_WIDTH_VALUE = 100.0;
    private static final Double INITIAL_LENGTH_VALUE = 200.0;
    private static final Double STEP_SIZE_VALUE = 1.0;

    @FXML private Spinner widthSpinner;
    @FXML private Spinner lengthSpinner;
    @FXML private ComboBox widthUnitComboBox;
    @FXML private ComboBox lengthUnitComboBox;
    @FXML private Label imagePathLabel;
    @FXML private ResizableImageView imageView;
    @FXML private Label imageErrorLabel;
    @FXML private Label widthErrorLabel;
    @FXML private Label lengthErrorLabel;

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
        updateImage();
        FXUtils.requestFocusDelayed(widthSpinner);
        super.init();
    }

    @FXML
    public void onBrowseImageButtonAction() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Image File");
        fileChooser.getExtensionFilters().addAll(new ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.bmp", "*.gif", "*.tiff"));
        File selectedFile = fileChooser.showOpenDialog(VisuaLigue.getMainStage());
        if (selectedFile != null) {
            this.model.newPlayingSurfaceImagePathName.set(selectedFile.getPath());
            updateImage();
        }
    }

    private void updateImage() {
        if (model.newPlayingSurfaceImagePathName.isNotEmpty().get()) {
            displayImage(FilenameUtils.getURIString(model.newPlayingSurfaceImagePathName.get()));
        } else if (model.currentPlayingSurfacePathName.isNotEmpty().get()) {
            displayImage(FilenameUtils.getURIString(model.currentPlayingSurfacePathName.get()));
        } else if (model.builtInPlayingSurfaceImage.isNotEmpty().get()) {
            displayImage(model.builtInPlayingSurfaceImage.get());
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
    public void clearErrors() {
        FXUtils.setDisplay(imageErrorLabel, false);
    }
}
