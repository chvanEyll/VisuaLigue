package ca.ulaval.glo2004.visualigue.ui.controllers.playeditor.toolbar;

import ca.ulaval.glo2004.visualigue.domain.play.Play;
import ca.ulaval.glo2004.visualigue.services.play.PlayService;
import ca.ulaval.glo2004.visualigue.ui.controllers.ControllerBase;
import ca.ulaval.glo2004.visualigue.ui.controllers.common.ExtendedButton;
import ca.ulaval.glo2004.visualigue.ui.controllers.playeditor.itempane.ItemPaneController;
import ca.ulaval.glo2004.visualigue.ui.controllers.playeditor.scene.SceneController;
import ca.ulaval.glo2004.visualigue.ui.controllers.playeditor.scene.Zoom;
import ca.ulaval.glo2004.visualigue.ui.controllers.playeditor.sequencecontrol.SequencePaneController;
import ca.ulaval.glo2004.visualigue.ui.models.PlayModel;
import ca.ulaval.glo2004.visualigue.utils.EventHandler;
import ca.ulaval.glo2004.visualigue.utils.geometry.Vector2;
import ca.ulaval.glo2004.visualigue.utils.math.MathUtils;
import java.util.function.BiConsumer;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javax.inject.Inject;

public class ToolbarController extends ControllerBase {

    public EventHandler onSaveButtonAction = new EventHandler();
    public EventHandler onCloseButtonAction = new EventHandler();
    public EventHandler onExportButtonAction = new EventHandler();
    public EventHandler onUndoButtonAction = new EventHandler();
    public EventHandler onRedoButtonAction = new EventHandler();
    public EventHandler onSwitch3DButtonAction = new EventHandler();
    @FXML private Button saveButton;
    @FXML private Button undoButton;
    @FXML private Button redoButton;
    @FXML private ExtendedButton realTimeButton;
    @FXML private ExtendedButton panButton;
    @FXML private Button zoomInButton;
    @FXML private ComboBox zoomComboBox;
    @FXML private Button zoomOutButton;
    @FXML private ExtendedButton actorLabelDisplayToggleButton;
    @FXML private ExtendedButton itemPaneDisplayButton;
    @FXML private Label coordinateLabel;
    @FXML private CheckMenuItem resizeActorsOnZoomMenuItem;
    @FXML private CheckMenuItem showMovementArrowsMenuItem;
    @FXML private CheckMenuItem smoothMovementsMenuItem;
    @Inject private PlayService playService;
    private BiConsumer<Object, Boolean> onUndoAvailabilityChanged = this::onUndoAvailabilityChanged;
    private BiConsumer<Object, Boolean> onRedoAvailabilityChanged = this::onRedoAvailabilityChanged;
    private BiConsumer<Object, Play> onPlayDirtyFlagChanged = this::onPlayDirtyFlagChanged;
    private PlayModel playModel;
    private SceneController sceneController;
    private ItemPaneController itemPaneController;
    private SequencePaneController sequencePaneController;
    private Boolean ignoreZoomComboBoxAction = false;

    public void init(PlayModel playModel, SceneController sceneController, ItemPaneController itemPaneController, SequencePaneController sequencePaneController) {
        this.playModel = playModel;
        this.sceneController = sceneController;
        this.itemPaneController = itemPaneController;
        this.sequencePaneController = sequencePaneController;
        setHandlers();
        setInitialStates();
        updateZoom();
    }

    private void setHandlers() {
        sceneController.onMousePositionChanged.addHandler(this::onSceneMousePositionChanged);
        sceneController.onZoomChanged.addHandler(this::onSceneZoomChanged);
        sceneController.onNavigationModeEntered.addHandler(this::onNavigationModeEntered);
        sceneController.onNavigationModeExited.addHandler(this::onNavigationModeExited);
        sceneController.onFrameByFrameCreationModeEntered.addHandler(this::onFrameByFrameCreationModeEntered);
        sceneController.onRealTimeCreationModeEntered.addHandler(this::onRealTimeCreationModeEntered);
        playService.onUndoAvailabilityChanged.addHandler(onUndoAvailabilityChanged);
        playService.onRedoAvailabilityChanged.addHandler(onRedoAvailabilityChanged);
        playService.onDirtyFlagChanged.addHandler(onPlayDirtyFlagChanged);
    }

    private void setInitialStates() {
        saveButton.setDisable(true);
        undoButton.setDisable(true);
        redoButton.setDisable(true);
        zoomComboBox.setItems(FXCollections.observableArrayList(SceneController.PREDEFINED_ZOOMS));
        zoomComboBox.focusedProperty().addListener(this::onZoomComboBoxFocusedPropertyChanged);
        itemPaneDisplayButton.setSelected(true);
        actorLabelDisplayToggleButton.setSelected(sceneController.isActorLabelDisplayEnabled());
        resizeActorsOnZoomMenuItem.setSelected(sceneController.isResizeActorsOnZoomEnabled());
        showMovementArrowsMenuItem.setSelected(sceneController.isMovementArrowDisplayEnabled());
        smoothMovementsMenuItem.setSelected(sequencePaneController.isSmoothMovementEnabled());
    }

    @Override
    public void clean() {
        playService.onUndoAvailabilityChanged.removeHandler(onUndoAvailabilityChanged);
        playService.onRedoAvailabilityChanged.removeHandler(onRedoAvailabilityChanged);
        playService.onDirtyFlagChanged.removeHandler(onPlayDirtyFlagChanged);
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
        onSaveButtonAction.fire(this);
    }

    @FXML
    protected void onCloseButtonAction(ActionEvent e) {
        onCloseButtonAction.fire(this);
    }

    @FXML
    protected void onExportButtonAction(ActionEvent e) {
        onExportButtonAction.fire(this);
    }

    @FXML
    protected void onUndoButtonAction(ActionEvent e) {
        onUndoButtonAction.fire(this);
    }

    @FXML
    protected void onRedoButtonAction(ActionEvent e) {
        onRedoButtonAction.fire(this);
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
                ignoreZoomComboBoxAction = true;
                Zoom zoom = Zoom.percentParse(zoomComboBox.getEditor().getText());
                sceneController.setZoom(zoom);
                ignoreZoomComboBoxAction = false;
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
        onSwitch3DButtonAction.fire(this);
    }

    @FXML
    protected void onActorLabelDisplayToggleButtonAction(ActionEvent e) {
        sceneController.setActorLabelDisplay(!sceneController.isActorLabelDisplayEnabled());
        actorLabelDisplayToggleButton.setSelected(sceneController.isActorLabelDisplayEnabled());
    }

    @FXML
    protected void onResizeActorsOnZoomToggleButtonAction(ActionEvent e) {
        sceneController.setResizeActorsOnZoom(!sceneController.isResizeActorsOnZoomEnabled());
        resizeActorsOnZoomMenuItem.setSelected(sceneController.isResizeActorsOnZoomEnabled());
    }

    @FXML
    protected void onMovementArrowsDisplayToggleButtonAction(ActionEvent e) {
        sceneController.setMovementArrowDisplay(!sceneController.isMovementArrowDisplayEnabled());
        showMovementArrowsMenuItem.setSelected(sceneController.isMovementArrowDisplayEnabled());
    }

    @FXML
    protected void onSmoothMovementToggleButtonAction(ActionEvent e) {
        sequencePaneController.setSmoothMovementEnabled(!sequencePaneController.isSmoothMovementEnabled());
        smoothMovementsMenuItem.setSelected(sequencePaneController.isSmoothMovementEnabled());
    }

    @FXML
    protected void onItemPaneDisplayToggleButtonAction(ActionEvent e) {
        itemPaneController.toggleExpand();
        itemPaneDisplayButton.setSelected(!itemPaneController.isCollapsed());
    }

    @FXML
    protected void onRealTimeButtonAction(ActionEvent e) {
        sceneController.toggleRealTimeMode();
    }

    private void onSceneMousePositionChanged(Object sender, Vector2 mousePosition) {
        coordinateLabel.setText(String.format("(%.1f %s, %.1f %s)", mousePosition.getX(),
                playModel.playingSurfaceWidthUnits.get().getAbbreviation(),
                mousePosition.getY(),
                playModel.playingSurfaceLengthUnits.get().getAbbreviation()));
    }

    private void onSceneZoomChanged(Object sender, Zoom zoom) {
        updateZoom();
    }

    private void updateZoom() {
        Zoom currentZoom = sceneController.getZoom();
        ignoreZoomComboBoxAction = true;
        zoomComboBox.setValue(currentZoom);
        ignoreZoomComboBoxAction = false;
        zoomInButton.setDisable(MathUtils.greaterOrEqual(currentZoom, sceneController.getMaxZoom()));
        zoomOutButton.setDisable(MathUtils.lessOrEqual(currentZoom, sceneController.getMinZoom()));
    }

    private void onUndoAvailabilityChanged(Object sender, Boolean undoAvailable) {
        undoButton.setDisable(!undoAvailable);
    }

    private void onRedoAvailabilityChanged(Object sender, Boolean redoAvailable) {
        redoButton.setDisable(!redoAvailable);
    }

    private void onPlayDirtyFlagChanged(Object sender, Play play) {
        if (play.getUUID().equals(playModel.getUUID())) {
            saveButton.setDisable(!play.isDirty());
        }
    }

    private void onNavigationModeEntered(Object sender) {
        panButton.setSelected(true);
    }

    private void onNavigationModeExited(Object sender) {
        panButton.setSelected(false);
    }

    private void onFrameByFrameCreationModeEntered(Object sender) {
        realTimeButton.setSelected(false);
    }

    private void onRealTimeCreationModeEntered(Object sender) {
        realTimeButton.setSelected(true);
    }
}
