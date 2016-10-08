package ca.ulaval.glo2004.visualigue.ui.controllers.sportcreation;

import ca.ulaval.glo2004.visualigue.ui.models.PlayerCategoryModel;
import ca.ulaval.glo2004.visualigue.utils.EventHandler;
import ca.ulaval.glo2004.visualigue.utils.FXUtils;
import java.util.Arrays;
import java.util.List;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;

public class PlayerCategoryListItemController {

    public static final String VIEW_NAME = "/views/player-category-list-item.fxml";
    @FXML private Label nameLabel;
    @FXML private Pane allyColorPane;
    @FXML private Pane opponentColorPane;
    @FXML private Pane spacerPane;
    @FXML private Button editButton;
    @FXML private Button deleteButton;
    private PlayerCategoryModel model;
    public EventHandler<PlayerCategoryModel> onEditRequested = new EventHandler<>();
    public EventHandler<PlayerCategoryModel> onDeleteRequested = new EventHandler<>();

    public void init(PlayerCategoryModel model) {
        this.model = model;
        nameLabel.textProperty().bindBidirectional(model.name);
        model.allyPlayerColor.addListener((observable, oldValue, newValue) -> {
            updateAllyColorBackground();
        });
        updateAllyColorBackground();
        model.opponentPlayerColor.addListener((observable, oldValue, newValue) -> {
            updateOpponentColorBackground();
        });
        updateOpponentColorBackground();
    }

    private void updateAllyColorBackground() {
        allyColorPane.setBackground(new Background(new BackgroundFill(model.allyPlayerColor.get(), null, null)));
    }

    private void updateOpponentColorBackground() {
        opponentColorPane.setBackground(new Background(new BackgroundFill(model.opponentPlayerColor.get(), null, null)));
    }

    public List<Node> getChildren() {
        return Arrays.asList(nameLabel, allyColorPane, opponentColorPane, spacerPane, editButton, deleteButton);
    }

    @FXML
    public void onEditButtonAction() {
        onEditRequested.fire(this, model);
    }

    @FXML
    public void onDeleteButtonAction() {
        onDeleteRequested.fire(this, model);
    }

    @FXML
    public void onMouseEntered() {
        FXUtils.addStyleClass(getChildren(), "hovered");
    }

    @FXML
    public void onMouseExited() {
        FXUtils.addStyleClass(getChildren(), "hovered");
    }

    @FXML
    public void onMouseClicked() {
        onEditRequested.fire(this, model);
    }
}
