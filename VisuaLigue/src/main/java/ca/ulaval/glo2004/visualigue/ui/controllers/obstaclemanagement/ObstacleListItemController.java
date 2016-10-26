package ca.ulaval.glo2004.visualigue.ui.controllers.obstaclemanagement;

import ca.ulaval.glo2004.visualigue.ui.animation.PredefinedAnimations;
import ca.ulaval.glo2004.visualigue.ui.controllers.common.editablelist.ListItemController;
import ca.ulaval.glo2004.visualigue.ui.models.ModelBase;
import ca.ulaval.glo2004.visualigue.ui.models.ObstacleModel;
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
    private ObstacleModel model;

    @Override
    public void init(ModelBase model) {
        this.model = (ObstacleModel) model;
        updateImage();
        nameLabel.textProperty().bind(this.model.name);
        deleteConfirmButton.focusedProperty().addListener(this::onDeleteConfirmButtonFocusChanged);
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
    protected void onEditButtonAction(ActionEvent e) {
        onEditRequested.fire(this, model);
    }

    @FXML
    protected void onSwipeLeft(SwipeEvent e) {
        displayDeleteConfirmationButton();
    }

    @FXML
    protected void onDeleteButtonAction(ActionEvent e) {
        displayDeleteConfirmationButton();
    }

    private void displayDeleteConfirmationButton() {
        FXUtils.setDisplay(deleteConfirmButtonContainer, true);
        PredefinedAnimations.regionRevealLeft(deleteConfirmButton);
        deleteConfirmButton.requestFocus();
    }

    public void onDeleteConfirmButtonFocusChanged(ObservableValue<? extends Boolean> value, Boolean oldPropertyValue, Boolean newPropertyValue) {
        if (!newPropertyValue) {
            FXUtils.setDisplay(deleteConfirmButtonContainer, false);
        }
    }

    @FXML
    protected void onDeleteConfirmButtonAction(ActionEvent e) {
        onDeleteRequested.fire(this, model);
    }

    @FXML
    protected void onMouseClicked() {
        if (!deleteConfirmButtonContainer.isVisible()) {
            onEditRequested.fire(this, model);
        }
    }
}
