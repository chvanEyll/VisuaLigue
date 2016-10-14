package ca.ulaval.glo2004.visualigue.ui.controllers.sportcreation;

import ca.ulaval.glo2004.visualigue.ui.models.PlayerCategoryModel;
import ca.ulaval.glo2004.visualigue.utils.EventHandler;
import ca.ulaval.glo2004.visualigue.utils.FXUtils;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.SwipeEvent;
import javafx.scene.layout.*;

public class PlayerCategoryListItemController {

    public static final String VIEW_NAME = "/views/sport-creation/player-category-list-item.fxml";
    @FXML private GridPane rootNode;
    @FXML private Label nameLabel;
    @FXML private Label abbreviationLabel;
    @FXML private Pane allyColorPane;
    @FXML private Pane opponentColorPane;
    @FXML private HBox deleteConfirmButtonContainer;
    @FXML private Button deleteConfirmButton;
    private PlayerCategoryModel model;
    public EventHandler<PlayerCategoryModel> onEditRequested = new EventHandler<>();
    public EventHandler<PlayerCategoryModel> onDeleteRequested = new EventHandler<>();

    public void init(PlayerCategoryModel model) {
        this.model = model;
        nameLabel.textProperty().bind(model.name);
        abbreviationLabel.textProperty().bind(model.abbreviation);
        model.allyPlayerColor.addListener((observable, oldValue, newValue) -> {
            updateAllyColorBackground();
        });
        updateAllyColorBackground();
        model.opponentPlayerColor.addListener((observable, oldValue, newValue) -> {
            updateOpponentColorBackground();
        });
        updateOpponentColorBackground();
        FXUtils.setDisplay(deleteConfirmButtonContainer, false);
        if (model.isDeleted()) {
            hide();
        }
    }

    private void updateAllyColorBackground() {
        allyColorPane.setBackground(new Background(new BackgroundFill(model.allyPlayerColor.get(), null, null)));
    }

    private void updateOpponentColorBackground() {
        opponentColorPane.setBackground(new Background(new BackgroundFill(model.opponentPlayerColor.get(), null, null)));
    }

    public void hide() {
        rootNode.getChildren().forEach(child -> {
            FXUtils.setDisplay(child, false);
        });
    }

    @FXML
    public void onEditButtonAction() {
        onEditRequested.fire(this, model);
    }

    @FXML
    public void onSwipeLeft(SwipeEvent e) {
        displayDeleteConfirmationButton();
    }

    @FXML
    public void onDeleteButtonAction() {
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
    public void onDeleteConfirmButtonAction() {
        onDeleteRequested.fire(this, model);
    }

    @FXML
    public void onMouseClicked() {
        if (!deleteConfirmButtonContainer.isVisible()) {
            onEditRequested.fire(this, model);
        }
    }
}
