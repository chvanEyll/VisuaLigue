package ca.ulaval.glo2004.visualigue.ui.controllers.obstaclemanagement;

import ca.ulaval.glo2004.visualigue.ui.animation.PredefinedAnimations;
import ca.ulaval.glo2004.visualigue.ui.controllers.common.ListItemController;
import ca.ulaval.glo2004.visualigue.ui.models.Model;
import ca.ulaval.glo2004.visualigue.ui.models.ObstacleCreationModel;
import ca.ulaval.glo2004.visualigue.utils.FXUtils;
import ca.ulaval.glo2004.visualigue.utils.FilenameUtils;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.SwipeEvent;
import javafx.scene.layout.HBox;

public class ObstacleListItemController extends ListItemController {

    public static final String VIEW_NAME = "/views/obstaclemanagement/obstacle-list-item.fxml";
    @FXML private ImageView imageView;
    @FXML private Label nameLabel;
    @FXML private HBox deleteConfirmButtonContainer;
    @FXML private Button deleteConfirmButton;
    private ObstacleCreationModel model;

    @Override
    public void init(Model model) {
        this.model = (ObstacleCreationModel) model;
        updateImage();
        nameLabel.textProperty().bind(this.model.name);
        FXUtils.setDisplay(deleteConfirmButtonContainer, false);
        super.init(model);
    }

    private void updateImage() {
        Image image = null;
        if (model.currentImagePathName.isNotEmpty().get()) {
            image = new Image(FilenameUtils.getURIString(model.currentImagePathName.get()));
        } else if (model.builtInImagePathName.isNotEmpty().get()) {
            image = new Image(model.builtInImagePathName.get());
        }
        imageView.setImage(image);
        imageView.setFitWidth(45);
        imageView.setFitHeight(45);
    }

    @FXML
    public void onEditButtonAction(ActionEvent e) {
        onEditRequested.fire(this, model);
    }

    @FXML
    public void onSwipeLeft(SwipeEvent e) {
        displayDeleteConfirmationButton();
    }

    @FXML
    public void onDeleteButtonAction(ActionEvent e) {
        displayDeleteConfirmationButton();
    }

    private void displayDeleteConfirmationButton() {
        FXUtils.setDisplay(deleteConfirmButtonContainer, true);
        PredefinedAnimations.regionRevealLeft(deleteConfirmButton);
        deleteConfirmButton.requestFocus();
        deleteConfirmButton.focusedProperty().addListener(this::onDeleteConfirmButtonFocuschanged);
    }

    public void onDeleteConfirmButtonFocuschanged(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue) {
        if (!newPropertyValue) {
            FXUtils.setDisplay(deleteConfirmButtonContainer, false);
        }
    }

    @FXML
    public void onDeleteConfirmButtonAction(ActionEvent e) {
        onDeleteRequested.fire(this, model);
    }

    @FXML
    public void onMouseClicked() {
        if (!deleteConfirmButtonContainer.isVisible()) {
            onEditRequested.fire(this, model);
        }
    }
}
