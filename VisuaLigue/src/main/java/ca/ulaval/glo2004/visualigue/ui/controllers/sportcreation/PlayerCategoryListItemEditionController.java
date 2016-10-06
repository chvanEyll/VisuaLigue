package ca.ulaval.glo2004.visualigue.ui.controllers.sportcreation;

import ca.ulaval.glo2004.visualigue.ui.models.PlayerCategoryModel;
import ca.ulaval.glo2004.visualigue.utils.EventHandler;
import java.util.List;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory.IntegerSpinnerValueFactory;

public class PlayerCategoryListItemEditionController {

    public static final String VIEW_NAME = "/views/player-category-list-item-edition.fxml";
    public static final Integer MIN_DEFAULT_NUMBER_OF_PLAYERS_VALUE = 0;
    public static final Integer MAX_DEFAULT_NUMBER_OF_PLAYERS_VALUE = 30;
    public static final Integer INITIAL_DEFAULT_NUMBER_OF_PLAYERS_VALUE = 0;
    public static final Integer STEP_DEFAULT_NUMBER_OF_PLAYERS_VALUE = 1;
    public EventHandler<PlayerCategoryModel> onEditionValidationRequested = new EventHandler<>();
    @FXML private Group rootNode;
    @FXML private ColorPicker allyColorPicker;
    @FXML private ColorPicker opponentColorPicker;
    @FXML private Spinner defaultNumberOfPlayersSpinner;
    private PlayerCategoryModel model;

    public void init(PlayerCategoryModel model) {
        this.model = model;
        allyColorPicker.valueProperty().bind(model.allyPlayerColor);
        opponentColorPicker.valueProperty().bind(model.opponentPlayerColor);
        defaultNumberOfPlayersSpinner.setValueFactory(new IntegerSpinnerValueFactory(MIN_DEFAULT_NUMBER_OF_PLAYERS_VALUE, MAX_DEFAULT_NUMBER_OF_PLAYERS_VALUE,
                INITIAL_DEFAULT_NUMBER_OF_PLAYERS_VALUE, STEP_DEFAULT_NUMBER_OF_PLAYERS_VALUE));
    }

    public PlayerCategoryModel getModel() {
        return model;
    }

    @FXML
    public void onValidateButtonAction() {
        onEditionValidationRequested.fire(this, model);
    }

    public List<Node> getChildren() {
        return rootNode.getChildren();
    }
}
