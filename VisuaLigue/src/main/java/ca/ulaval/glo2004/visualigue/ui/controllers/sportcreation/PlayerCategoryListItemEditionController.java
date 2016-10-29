package ca.ulaval.glo2004.visualigue.ui.controllers.sportcreation;

import ca.ulaval.glo2004.visualigue.ui.controllers.common.editablelist.ListItemEditionController;
import ca.ulaval.glo2004.visualigue.ui.models.ModelBase;
import ca.ulaval.glo2004.visualigue.ui.models.PlayerCategoryModel;
import ca.ulaval.glo2004.visualigue.utils.javafx.FXUtils;
import ca.ulaval.glo2004.visualigue.utils.StringUtils;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.SpinnerValueFactory.IntegerSpinnerValueFactory;
import javafx.scene.control.*;

public class PlayerCategoryListItemEditionController extends ListItemEditionController {

    public static final String VIEW_NAME = "/views/sportcreation/player-category-list-item-edition.fxml";
    public static final Integer MIN_DEFAULT_NUMBER_OF_PLAYERS_VALUE = 0;
    public static final Integer MAX_DEFAULT_NUMBER_OF_PLAYERS_VALUE = 30;
    public static final Integer INITIAL_DEFAULT_NUMBER_OF_PLAYERS_VALUE = 0;
    public static final Integer STEP_DEFAULT_NUMBER_OF_PLAYERS_VALUE = 1;

    private PlayerCategoryModel model;
    @FXML private TextField nameTextField;
    @FXML private TextField abbreviationTextField;
    @FXML private ColorPicker allyColorPicker;
    @FXML private ColorPicker opponentColorPicker;
    @FXML private Spinner defaultNumberOfPlayersSpinner;
    @FXML private Button revertButton;
    @FXML private Label nameErrorLabel;

    @Override
    public void init(ModelBase model) {
        clearErrors();
        this.model = (PlayerCategoryModel) model;
        nameTextField.textProperty().set(this.model.name.get());
        nameTextField.textProperty().addListener(this::onCategoryNameTextChanged);
        abbreviationTextField.textProperty().set(this.model.abbreviation.get());
        allyColorPicker.valueProperty().set(this.model.allyPlayerColor.get());
        opponentColorPicker.valueProperty().set(this.model.opponentPlayerColor.get());
        defaultNumberOfPlayersSpinner.setValueFactory(new IntegerSpinnerValueFactory(MIN_DEFAULT_NUMBER_OF_PLAYERS_VALUE, MAX_DEFAULT_NUMBER_OF_PLAYERS_VALUE,
                this.model.defaultNumberOfPlayers.get(), STEP_DEFAULT_NUMBER_OF_PLAYERS_VALUE));
        FXUtils.requestFocusDelayed(nameTextField);
    }

    @Override
    public ModelBase getModel() {
        return model;
    }

    @FXML
    protected void onValidateButtonAction(ActionEvent e) {
        if (validate()) {
            model.name.set(StringUtils.trim(nameTextField.textProperty().get()));
            model.abbreviation.set(StringUtils.trim(abbreviationTextField.getText()));
            model.allyPlayerColor.set(allyColorPicker.getValue());
            model.opponentPlayerColor.set(opponentColorPicker.getValue());
            model.defaultNumberOfPlayers.set((int) defaultNumberOfPlayersSpinner.getValue());
            model.makeDirty();
            onCloseRequested.fire(this, model);
        }
    }

    @FXML
    protected void onRevertButtonAction(ActionEvent e) {
        onCloseRequested.fire(this, model);
    }

    private void onCategoryNameTextChanged(final ObservableValue<? extends String> value, final String oldPropertyValue, final String newPropertyValue) {
        String oldAutoAbbreviation = StringUtils.getFirstLetterOfEachWord(oldPropertyValue);
        if (oldAutoAbbreviation.compareTo(model.abbreviation.get()) == 0) {
            model.abbreviation.set(StringUtils.getFirstLetterOfEachWord(newPropertyValue));
        }
    }

    private Boolean validate() {
        clearErrors();
        if (StringUtils.isBlank(nameTextField.getText())) {
            nameErrorLabel.setText("Le nom de la catégorie doit être spécifié.");
            FXUtils.setDisplay(nameErrorLabel, true);
        } else {
            return true;
        }
        return false;
    }

    private void clearErrors() {
        FXUtils.setDisplay(nameErrorLabel, false);
    }
}
