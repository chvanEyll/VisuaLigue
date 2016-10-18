package ca.ulaval.glo2004.visualigue.ui.controllers.sportcreation;

import ca.ulaval.glo2004.visualigue.ui.models.PlayerCategoryModel;
import ca.ulaval.glo2004.visualigue.utils.EventHandler;
import ca.ulaval.glo2004.visualigue.utils.FXUtils;
import ca.ulaval.glo2004.visualigue.utils.StringUtils;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory.IntegerSpinnerValueFactory;
import javafx.scene.control.TextField;

public class PlayerCategoryListItemEditionController {

    public static final String VIEW_NAME = "/views/sport-creation/player-category-list-item-edition.fxml";
    public static final Integer MIN_DEFAULT_NUMBER_OF_PLAYERS_VALUE = 0;
    public static final Integer MAX_DEFAULT_NUMBER_OF_PLAYERS_VALUE = 30;
    public static final Integer INITIAL_DEFAULT_NUMBER_OF_PLAYERS_VALUE = 0;
    public static final Integer STEP_DEFAULT_NUMBER_OF_PLAYERS_VALUE = 1;
    public EventHandler<PlayerCategoryModel> onCloseRequested = new EventHandler<>();

    @FXML private TextField nameTextField;
    @FXML private TextField abbreviationTextField;
    @FXML private ColorPicker allyColorPicker;
    @FXML private ColorPicker opponentColorPicker;
    @FXML private Spinner defaultNumberOfPlayersSpinner;
    private PlayerCategoryModel model;

    public void init(PlayerCategoryModel model) {
        this.model = model;
        nameTextField.textProperty().set(model.name.get());
        nameTextField.textProperty().addListener(this::onCategoryNameTextChanged);
        abbreviationTextField.textProperty().set(model.abbreviation.get());
        allyColorPicker.valueProperty().set(model.allyPlayerColor.get());
        opponentColorPicker.valueProperty().set(model.opponentPlayerColor.get());
        defaultNumberOfPlayersSpinner.setValueFactory(new IntegerSpinnerValueFactory(MIN_DEFAULT_NUMBER_OF_PLAYERS_VALUE, MAX_DEFAULT_NUMBER_OF_PLAYERS_VALUE,
                model.defaultNumberOfPlayers.get(), STEP_DEFAULT_NUMBER_OF_PLAYERS_VALUE));
        FXUtils.requestFocusDelayed(nameTextField);
    }

    public PlayerCategoryModel getModel() {
        return model;
    }

    @FXML
    public void onValidateButtonAction() {
        model.name.set(nameTextField.textProperty().get());
        model.abbreviation.set(abbreviationTextField.getText());
        model.allyPlayerColor.set(allyColorPicker.getValue());
        model.opponentPlayerColor.set(opponentColorPicker.getValue());
        model.defaultNumberOfPlayers.set((int) defaultNumberOfPlayersSpinner.getValue());
        model.makeDirty();
        onCloseRequested.fire(this, model);
    }

    @FXML
    public void onRevertButtonAction() {
        onCloseRequested.fire(this, model);
    }

    private void onCategoryNameTextChanged(final ObservableValue<? extends String> ov, final String oldValue, final String newValue) {
        String oldAutoAbbreviation = StringUtils.getFirstLetterOfEachWord(oldValue);
        if (oldAutoAbbreviation.compareTo(model.abbreviation.get()) == 0) {
            model.abbreviation.set(StringUtils.getFirstLetterOfEachWord(newValue));
        }
    }
}
