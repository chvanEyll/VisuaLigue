package ca.ulaval.glo2004.visualigue.ui.controllers.sportcreation;

import ca.ulaval.glo2004.visualigue.ui.controllers.common.ListItemController;
import ca.ulaval.glo2004.visualigue.ui.models.Model;
import ca.ulaval.glo2004.visualigue.ui.models.PlayerCategoryModel;
import ca.ulaval.glo2004.visualigue.utils.FXUtils;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.SwipeEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

public class PlayerCategoryListItemController extends ListItemController {

    public static final String VIEW_NAME = "/views/sportcreation/player-category-list-item.fxml";
    @FXML private Label nameLabel;
    @FXML private Label abbreviationLabel;
    @FXML private Pane allyColorPane;
    @FXML private Pane opponentColorPane;
    @FXML private HBox deleteConfirmButtonContainer;
    @FXML private Button deleteConfirmButton;
    private PlayerCategoryModel model;

    @Override
    public void init(Model model) {
        this.model = (PlayerCategoryModel) model;
        nameLabel.textProperty().bind(this.model.name);
        abbreviationLabel.textProperty().bind(this.model.abbreviation);
        this.model.allyPlayerColor.addListener((observable, oldValue, newValue) -> {
            updateAllyColorBackground();
        });
        updateAllyColorBackground();
        this.model.opponentPlayerColor.addListener((observable, oldValue, newValue) -> {
            updateOpponentColorBackground();
        });
        updateOpponentColorBackground();
        FXUtils.setDisplay(deleteConfirmButtonContainer, false);
        super.init(model);
    }

    private void updateAllyColorBackground() {
        allyColorPane.setBackground(new Background(new BackgroundFill(model.allyPlayerColor.get(), null, null)));
    }

    private void updateOpponentColorBackground() {
        opponentColorPane.setBackground(new Background(new BackgroundFill(model.opponentPlayerColor.get(), null, null)));
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
        deleteConfirmButton.requestFocus();
        deleteConfirmButton.focusedProperty().addListener(this::onDeleteConfirmButtonFocuschanged);
    }

    public void onDeleteConfirmButtonFocuschanged(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue) {
        if (!newPropertyValue) {
            FXUtils.setDisplay(deleteConfirmButtonContainer, false);
        }
    }

    @FXML
    protected void onDeleteConfirmButtonAction(ActionEvent e) {
        onDeleteRequested.fire(this, model);
    }

    @FXML
    protected void onMouseClicked(MouseEvent e) {
        if (!deleteConfirmButtonContainer.isVisible()) {
            onEditRequested.fire(this, model);
        }
    }
}
