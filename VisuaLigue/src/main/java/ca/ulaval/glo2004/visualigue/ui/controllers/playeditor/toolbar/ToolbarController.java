package ca.ulaval.glo2004.visualigue.ui.controllers.playeditor.toolbar;

import ca.ulaval.glo2004.visualigue.domain.play.Play;
import ca.ulaval.glo2004.visualigue.domain.sport.playingsurface.PlayingSurfaceUnit;
import ca.ulaval.glo2004.visualigue.services.play.PlayService;
import ca.ulaval.glo2004.visualigue.ui.controllers.ViewController;
import ca.ulaval.glo2004.visualigue.ui.controllers.playeditor.itempane.ItemPaneController;
import ca.ulaval.glo2004.visualigue.ui.controllers.playeditor.scene.SceneController;
import ca.ulaval.glo2004.visualigue.ui.controllers.playeditor.scene.Zoom;
import ca.ulaval.glo2004.visualigue.ui.customcontrols.ExtendedButton;
import ca.ulaval.glo2004.visualigue.ui.models.MousePositionModel;
import ca.ulaval.glo2004.visualigue.ui.models.PlayModel;
import ca.ulaval.glo2004.visualigue.utils.EventHandler;
import ca.ulaval.glo2004.visualigue.utils.geometry.Vector2;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javax.inject.Inject;

public class ToolbarController extends ViewController {

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
    @FXML private ExtendedButton panButton;
    @FXML private Button zoomInButton;
    @FXML private ComboBox zoomComboBox;
    @FXML private Button zoomOutButton;
    @FXML private ExtendedButton labelDisplayToggleButton;
    @FXML private Label coordinateLabel;
    @Inject private PlayService playService;
    private PlayModel playModel;
    private SceneController sceneController;
    private ItemPaneController itemPaneController;
    private Boolean ignoreZoomComboBoxAction = false;

    public void init(PlayModel playModel, SceneController sceneController, ItemPaneController itemPaneController) {
        this.playModel = playModel;
        this.sceneController = sceneController;
        this.itemPaneController = itemPaneController;
        sceneController.onMousePositionChanged.addHandler(this::onSceneMousePositionChanged);
        sceneController.onZoomChanged.addHandler(this::onSceneZoomChanged);
        sceneController.onPlayerCategoryLabelDisplayEnableChanged.addHandler(this::onPlayerCategoryLabelDisplayEnableChanged);
        sceneController.onNavigationModeEntered.addHandler(this::onNavigationModeEntered);
        sceneController.onNavigationModeExited.addHandler(this::onNavigationModeExited);
        playService.onUndoAvailabilityChanged.addHandler(this::onUndoAvailabilityChanged);
        playService.onRedoAvailabilityChanged.addHandler(this::onRedoAvailabilityChanged);
        playService.onPlayDirtyFlagChanged.addHandler(this::onPlayDirtyFlagChanged);
        saveButton.setDisable(true);
        undoButton.setDisable(true);
        redoButton.setDisable(true);
        zoomComboBox.setItems(FXCollections.observableArrayList(SceneController.PREDEFINED_ZOOMS));
        zoomComboBox.focusedProperty().addListener(this::onZoomComboBoxFocusedPropertyChanged);
        updateZoom();
    }

    private void onZoomComboBoxFocusedPropertyChanged(ObservableValue<? extends Boolean> value, Boolean oldPropertyValue, Boolean newPropertyValue) {
        if (newPropertyValue) {
            Platform.runLater(() -> {
                zoomComboBox.getEditor().selectAll();
            });
        }
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
    protected void onPanButtonAction(ActionEvent e) {
        sceneController.enterNavigationMode();
    }

    @FXML
    protected void onZoomInButtonAction(ActionEvent e) {
        sceneController.zoomIn();
    }

    @FXML
    protected void onZoomComboBoxAction(ActionEvent e) {
        if (!ignoreZoomComboBoxAction) {
            try {
                Zoom zoom = Zoom.percentParse(zoomComboBox.getEditor().getText());
                sceneController.setZoom(zoom);
            } catch (IllegalArgumentException ex) {
                updateZoom();
            }
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
    protected void onLabelDisplayToggleButtonAction(ActionEvent e) {
        sceneController.setPlayerCategoryLabelDisplayEnabled(!sceneController.isPlayerCategoryLabelDisplayEnabled());
    }

    @FXML
    protected void onItemPaneDisplayToggleButtonAction(ActionEvent e) {
        itemPaneController.toggleExpand();
    }

    private void onSceneMousePositionChanged(Object sender, MousePositionModel mousePositionModel) {
        Vector2 mousePosition = mousePositionModel.position.get();
        PlayingSurfaceUnit playingSurfaceUnit = mousePositionModel.playingSurfaceUnit.get();
        coordinateLabel.setText(String.format("(%.2f %s, %.2f %s)", mousePosition.getX(), playingSurfaceUnit.getAbbreviation(),
                mousePosition.getY(), playingSurfaceUnit.getAbbreviation()));
    }

    private void onSceneZoomChanged(Object sender, Zoom zoom) {
        updateZoom();
    }

    private void updateZoom() {
        Zoom currentZoom = sceneController.getZoom();
        ignoreZoomComboBoxAction = true;
        zoomComboBox.setValue(currentZoom);
        ignoreZoomComboBoxAction = false;
        zoomInButton.setDisable(currentZoom.compareTo(sceneController.getMaxZoom()) >= 0);
        zoomOutButton.setDisable(currentZoom.compareTo(sceneController.getMinZoom()) <= 0);
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
        if (play.getUUID().equals(playModel.getUUID())) {
            saveButton.setDisable(!play.isDirty());
        }
    }

    private void onNavigationModeEntered(Object sender, Object param) {
        panButton.setSelected(true);
    }

    private void onNavigationModeExited(Object sender, Object param) {
        panButton.setSelected(false);
    }

}
