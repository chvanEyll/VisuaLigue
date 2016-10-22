package ca.ulaval.glo2004.visualigue.ui.controllers.playeditor.toolbar;

import ca.ulaval.glo2004.visualigue.domain.play.Play;
import ca.ulaval.glo2004.visualigue.domain.play.position.Position;
import ca.ulaval.glo2004.visualigue.domain.sport.playingsurface.PlayingSurfaceUnit;
import ca.ulaval.glo2004.visualigue.services.play.PlayService;
import ca.ulaval.glo2004.visualigue.ui.controllers.ControllerBase;
import ca.ulaval.glo2004.visualigue.ui.controllers.playeditor.scene.SceneController;
import ca.ulaval.glo2004.visualigue.ui.customcontrols.ExtendedButton;
import ca.ulaval.glo2004.visualigue.ui.models.MousePositionModel;
import ca.ulaval.glo2004.visualigue.utils.EventHandler;
import java.util.UUID;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javax.inject.Inject;

public class ToolbarController extends ControllerBase {

    public EventHandler<Object> onSaveButtonAction = new EventHandler();
    public EventHandler<Object> onCloseButtonAction = new EventHandler();
    public EventHandler<Object> onExportButtonAction = new EventHandler();
    public EventHandler<Object> onUndoButtonAction = new EventHandler();
    public EventHandler<Object> onRedoButtonAction = new EventHandler();
    public EventHandler<Object> onBestFitButtonAction = new EventHandler();
    public EventHandler<Object> onSwitch3DButtonAction = new EventHandler();
    @FXML private Button saveButton;
    @FXML private Button undoButton;
    @FXML private Button redoButton;
    @FXML private Button zoomInButton;
    @FXML private TextField zoomTextField;
    @FXML private Button zoomOutButton;
    @FXML private ExtendedButton labelDisplayToggleButton;
    @FXML private Label coordinateLabel;
    @Inject private PlayService playService;
    private UUID playUUID;
    private SceneController sceneController;

    public void init(UUID playUUID, SceneController sceneController) {
        this.playUUID = playUUID;
        this.sceneController = sceneController;
        sceneController.onMousePositionChanged.addHandler(this::onSceneMousePositionChanged);
        sceneController.onZoomChanged.addHandler(this::onSceneZoomChanged);
        sceneController.onPlayerCategoryLabelDisplayEnableChanged.addHandler(this::onPlayerCategoryLabelDisplayEnableChanged);
        playService.onUndoAvailabilityChanged.addHandler(this::onUndoAvailabilityChanged);
        playService.onRedoAvailabilityChanged.addHandler(this::onRedoAvailabilityChanged);
        playService.onPlayDirtyFlagChanged.addHandler(this::onPlayDirtyFlagChanged);
        saveButton.setDisable(true);
        undoButton.setDisable(true);
        redoButton.setDisable(true);
        updateZoom();
    }

    @FXML
    protected void onSaveButtonAction(ActionEvent e) {
        onSaveButtonAction.fire(this, null);
    }

    @FXML
    protected void onCloseButtonAction(ActionEvent e) {
        onCloseButtonAction.fire(this, null);
    }

    @FXML
    protected void onExportButtonAction(ActionEvent e) {
        onExportButtonAction.fire(this, null);
    }

    @FXML
    protected void onUndoButtonAction(ActionEvent e) {
        onUndoButtonAction.fire(this, null);
    }

    @FXML
    protected void onRedoButtonAction(ActionEvent e) {
        onRedoButtonAction.fire(this, null);
    }

    @FXML
    protected void onZoomInButtonAction(ActionEvent e) {
        sceneController.zoomIn();
    }

    @FXML
    protected void onZoomTextFieldAction(ActionEvent e) {
        try {
            Integer zoom = Integer.parseInt(zoomTextField.getText());
            sceneController.setZoom(zoom / 100.0);
        } catch (NumberFormatException ex) {
            updateZoom();
        }
    }

    @FXML
    protected void onZoomOutButtonAction(ActionEvent e) {
        sceneController.zoomOut();
    }

    @FXML
    protected void onBestFitButtonAction(ActionEvent e) {
        sceneController.autoFit();
    }

    @FXML
    protected void onSwitch3DButtonAction(ActionEvent e) {
        onSwitch3DButtonAction.fire(this, null);
    }

    @FXML
    protected void onCategoryLabelDisplayToggleButtonAction(ActionEvent e) {
        sceneController.setPlayerCategoryLabelDisplayEnabled(!sceneController.isPlayerCategoryLabelDisplayEnabled());
    }

    private void onSceneMousePositionChanged(Object sender, MousePositionModel mousePositionModel) {
        Position mousePosition = mousePositionModel.position.get();
        PlayingSurfaceUnit playingSurfaceUnit = mousePositionModel.playingSurfaceUnit.get();
        coordinateLabel.setText(String.format("(%.2f %s, %.2f %s)", mousePosition.getX(), playingSurfaceUnit.getAbbreviation(),
                mousePosition.getY(), playingSurfaceUnit.getAbbreviation()));
    }

    private void onSceneZoomChanged(Object sender, Double zoom) {
        updateZoom();
    }

    private void updateZoom() {
        Double currentZoom = sceneController.getZoom();
        zoomTextField.setText(String.format("%.0f", sceneController.getZoom() * 100));
        zoomInButton.setDisable(currentZoom < sceneController.getMaxZoom());
        zoomOutButton.setDisable(currentZoom > sceneController.getMinZoom());
    }

    private void onPlayerCategoryLabelDisplayEnableChanged(Object sender, Boolean displayEnabled) {
        labelDisplayToggleButton.setSelected(displayEnabled);
    }

    private void onUndoAvailabilityChanged(Object sender, Boolean undoAvailable) {
        undoButton.setDisable(!undoAvailable);
    }

    private void onRedoAvailabilityChanged(Object sender, Boolean undoAvailable) {
        redoButton.setDisable(!undoAvailable);
    }

    private void onPlayDirtyFlagChanged(Object sender, Play play) {
        if (play.getUUID().equals(playUUID)) {
            saveButton.setDisable(!play.isDirty());
        }
    }

}
