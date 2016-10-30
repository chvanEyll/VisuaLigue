package ca.ulaval.glo2004.visualigue.ui.controllers.sportcreation;

import ca.ulaval.glo2004.visualigue.VisuaLigue;
import ca.ulaval.glo2004.visualigue.domain.sport.playingsurface.PlayingSurfaceUnit;
import ca.ulaval.glo2004.visualigue.ui.controllers.common.ExtendedImageView;
import ca.ulaval.glo2004.visualigue.ui.models.SportCreationModel;
import ca.ulaval.glo2004.visualigue.utils.FilenameUtils;
import ca.ulaval.glo2004.visualigue.utils.javafx.BindingUtils;
import ca.ulaval.glo2004.visualigue.utils.javafx.FXUtils;
import java.io.File;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory.DoubleSpinnerValueFactory;
import javafx.scene.image.Image;

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
    @FXML private ExtendedImageView imageView;
    @FXML private Label imageErrorLabel;

    public SportCreationModel getSportModel() {
        return model;
    }

    @Override
    public void init(SportCreationModel sportCreationModel) {
        model = sportCreationModel;
        widthSpinner.setValueFactory(new DoubleSpinnerValueFactory(MIN_SIZE_VALUE, MAX_SIZE_VALUE, INITIAL_WIDTH_VALUE, STEP_SIZE_VALUE));
        BindingUtils.cleanBindBidirectional(widthSpinner.getValueFactory().valueProperty(), model.playingSurfaceWidth);
        lengthSpinner.setValueFactory(new DoubleSpinnerValueFactory(MIN_SIZE_VALUE, MAX_SIZE_VALUE, INITIAL_LENGTH_VALUE, STEP_SIZE_VALUE));
        BindingUtils.cleanBindBidirectional(lengthSpinner.getValueFactory().valueProperty(), model.playingSurfaceLength);
        widthUnitComboBox.setItems(FXCollections.observableArrayList(PlayingSurfaceUnit.values()));
        widthUnitComboBox.getSelectionModel().select(model.playingSurfaceWidthUnits.get());
        lengthUnitComboBox.setItems(FXCollections.observableArrayList(PlayingSurfaceUnit.values()));
        lengthUnitComboBox.getSelectionModel().select(model.playingSurfaceLengthUnits.get());
        clearErrors();
        updateImage();

        FXUtils.requestFocusDelayed(widthSpinner);
    }

    @FXML
    protected void onBrowseImageButtonAction(ActionEvent e) {
        File selectedFile = FXUtils.chooseImage(VisuaLigue.getMainStage());
        if (selectedFile != null) {
            this.model.newPlayingSurfaceImagePathName.set(selectedFile.getPath());
            updateImage();
        }
    }

    private void updateImage() {
        if (model.newPlayingSurfaceImagePathName.isNotEmpty().get()) {
            displayImage(FilenameUtils.getURIString(model.newPlayingSurfaceImagePathName.get()));
        } else if (model.currentPlayingSurfaceImagePathName.isNotEmpty().get()) {
            displayImage(FilenameUtils.getURIString(model.currentPlayingSurfaceImagePathName.get()));
        } else if (model.builtInPlayingSurfaceImagePathName.isNotEmpty().get()) {
            displayImage(model.builtInPlayingSurfaceImagePathName.get());
        } else {
            clearImage();
        }
    }

    private void displayImage(String imageURL) {
        try {
            imageView.setImage(new Image(imageURL));
        } catch (Exception ex) {
            clearErrors();
            imageErrorLabel.setText("L'image spécifiée n'a pu être chargée.");
            FXUtils.setDisplay(imageErrorLabel, true);
        }
    }

    private void clearImage() {
        imageView.setImage(null);
    }

    @Override
    public void clearErrors() {
        FXUtils.setDisplay(imageErrorLabel, false);
    }
}
