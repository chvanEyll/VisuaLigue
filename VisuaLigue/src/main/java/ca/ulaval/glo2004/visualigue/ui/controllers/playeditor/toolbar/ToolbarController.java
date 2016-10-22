package ca.ulaval.glo2004.visualigue.ui.controllers.playeditor.toolbar;

import ca.ulaval.glo2004.visualigue.ui.controllers.ControllerBase;
import ca.ulaval.glo2004.visualigue.ui.controllers.playeditor.scene.SceneController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class ToolbarController extends ControllerBase {

    @FXML Button saveButton;
    @FXML Button closeButton;
    @FXML Button exportButton;
    @FXML Button undoButton;
    @FXML Button redoButton;
    @FXML Button zoomInButton;
    @FXML TextField zoomTextField;
    @FXML Button zoomOutButton;
    @FXML Button bestFitButton;
    @FXML Button switch3DButton;
    @FXML Button labelDisplayToggleButton;
    @FXML Label coordinateLabel;
    private SceneController sceneController;

    protected void init(SceneController sceneController) {
        this.sceneController = sceneController;
    }

    @FXML
    protected void onSaveButtonAction(ActionEvent e) {

    }

    @FXML
    protected void onCloseButtonAction(ActionEvent e) {

    }

    @FXML
    protected void onExportButtonAction(ActionEvent e) {

    }

    @FXML
    protected void onUndoButtonAction(ActionEvent e) {

    }

    @FXML
    protected void onRedoButtonAction(ActionEvent e) {

    }

    @FXML
    protected void onZoomInButtonAction(ActionEvent e) {

    }

    @FXML
    protected void onZoomTextFieldAction(ActionEvent e) {

    }

    @FXML
    protected void onZoomOutButtonAction(ActionEvent e) {

    }

    @FXML
    protected void onBestFitButtonAction(ActionEvent e) {

    }

    @FXML
    protected void onSwitch3DButtonAction(ActionEvent e) {

    }

    @FXML
    protected void onLabelDisplayToggleButtonAction(ActionEvent e) {

    }

}
