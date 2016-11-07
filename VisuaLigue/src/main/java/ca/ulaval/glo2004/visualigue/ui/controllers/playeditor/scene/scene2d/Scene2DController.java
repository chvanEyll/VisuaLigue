package ca.ulaval.glo2004.visualigue.ui.controllers.playeditor.scene.scene2d;

import ca.ulaval.glo2004.visualigue.domain.play.Play;
import ca.ulaval.glo2004.visualigue.services.play.PlayService;
import ca.ulaval.glo2004.visualigue.services.settings.SettingsService;
import ca.ulaval.glo2004.visualigue.ui.InjectableFXMLLoader;
import ca.ulaval.glo2004.visualigue.ui.KeyboardShortcutMapper;
import ca.ulaval.glo2004.visualigue.ui.View;
import ca.ulaval.glo2004.visualigue.ui.controllers.common.ExtendedScrollPane;
import ca.ulaval.glo2004.visualigue.ui.controllers.playeditor.actorlayers.ActorLayerFactory;
import ca.ulaval.glo2004.visualigue.ui.controllers.playeditor.scene.SceneController;
import ca.ulaval.glo2004.visualigue.ui.controllers.playeditor.scene.Zoom;
import ca.ulaval.glo2004.visualigue.ui.converters.FrameModelConverter;
import ca.ulaval.glo2004.visualigue.ui.models.FrameModel;
import ca.ulaval.glo2004.visualigue.ui.models.PlayModel;
import ca.ulaval.glo2004.visualigue.utils.geometry.Vector2;
import java.util.function.BiConsumer;
import javafx.fxml.FXML;
import javafx.scene.input.*;
import javafx.scene.layout.StackPane;
import javax.inject.Inject;

public class Scene2DController extends SceneController {

    @FXML private ExtendedScrollPane scrollPane;
    @FXML private StackPane scrollPaneContent;
    @FXML private StackPane layerStackPane;
    @Inject private SettingsService settingsService;
    @Inject private PlayService playService;
    @Inject private ActorLayerFactory actorLayerFactory;
    @Inject private FrameModelConverter frameModelConverter;
    private BiConsumer<Object, Play> onPlayFrameChanged = this::onPlayFrameChanged;
    private Integer currentTime;
    private PlayingSurfaceLayerController playingSurfaceLayerController;
    private NavigationController navigationController;
    private LayerController layerController;
    private ActorCreationController actorCreationController;
    private FrameModel frameModel = new FrameModel();

    @Override
    public void init(PlayModel playModel) {
        this.playModel = playModel;
        showActorLabelsProperty.set(settingsService.getShowActorLabels());
        showMovementArrowsProperty.set(settingsService.getShowMovementArrows());
        resizeActorsOnZoomProperty.set(settingsService.getResizeActorsOnZoom());
        playService.onFrameChanged.addHandler(onPlayFrameChanged);
        initControllers();
        initKeyboardShortcuts();
    }

    private void initControllers() {
        View playingSurfaceView = InjectableFXMLLoader.loadView(PlayingSurfaceLayerController.VIEW_NAME);
        playingSurfaceLayerController = (PlayingSurfaceLayerController) playingSurfaceView.getController();
        playingSurfaceLayerController.init(playModel, layerStackPane);
        playingSurfaceLayerController.onMouseMoved.addHandler(this::onPlayingSurfaceMouseMoved);
        playingSurfaceLayerController.onMouseClicked.addHandler(this::onPlayingSurfaceMouseClicked);
        navigationController = new NavigationController(scrollPane, scrollPaneContent, playingSurfaceLayerController);
        navigationController.onRealWorldMousePositionChanged.forward(onMousePositionChanged);
        navigationController.onZoomChanged.forward(this.onZoomChanged);
        navigationController.onEnabled.forward(this.onNavigationModeEntered);
        navigationController.onDisabled.forward(this.onNavigationModeExited);
        layerController = new LayerController(frameModel, actorLayerFactory, layerStackPane, playingSurfaceLayerController, navigationController.getZoomProperty(), showActorLabelsProperty, showMovementArrowsProperty, resizeActorsOnZoomProperty);
        layerController.addLayer(playingSurfaceView);
        super.addChild(playingSurfaceLayerController);
        super.addChild(navigationController);
        super.addChild(layerController);
    }

    private void initKeyboardShortcuts() {
        KeyboardShortcutMapper.map(new KeyCodeCombination(KeyCode.ADD, KeyCombination.CONTROL_DOWN), this::zoomIn);
        KeyboardShortcutMapper.map(new KeyCodeCombination(KeyCode.DIGIT0, KeyCombination.CONTROL_DOWN), this::autoFit);
        KeyboardShortcutMapper.map(new KeyCodeCombination(KeyCode.NUMPAD0, KeyCombination.CONTROL_DOWN), this::autoFit);
        KeyboardShortcutMapper.map(new KeyCodeCombination(KeyCode.SUBTRACT, KeyCombination.CONTROL_DOWN), this::zoomOut);
        KeyboardShortcutMapper.map(new KeyCodeCombination(KeyCode.R, KeyCombination.CONTROL_DOWN), this::toggleRealTimeMode);
        KeyboardShortcutMapper.map(new KeyCodeCombination(KeyCode.Q, KeyCombination.CONTROL_DOWN), this::enterNavigationMode);

    }

    @Override
    public void clean() {
        KeyboardShortcutMapper.unmap(new KeyCodeCombination(KeyCode.ADD, KeyCombination.CONTROL_DOWN));
        KeyboardShortcutMapper.unmap(new KeyCodeCombination(KeyCode.DIGIT0, KeyCombination.CONTROL_DOWN));
        KeyboardShortcutMapper.unmap(new KeyCodeCombination(KeyCode.NUMPAD0, KeyCombination.CONTROL_DOWN));
        KeyboardShortcutMapper.unmap(new KeyCodeCombination(KeyCode.SUBTRACT, KeyCombination.CONTROL_DOWN));
        KeyboardShortcutMapper.unmap(new KeyCodeCombination(KeyCode.R, KeyCombination.CONTROL_DOWN));
        KeyboardShortcutMapper.unmap(new KeyCodeCombination(KeyCode.Q, KeyCombination.CONTROL_DOWN));
        playService.onFrameChanged.removeHandler(onPlayFrameChanged);
    }

    private void onPlayFrameChanged(Object sender, Play play) {
        update(currentTime);
    }

    @Override
    public void update(Integer time) {
        currentTime = time;
        frameModelConverter.update(frameModel, playService.getFrame(playModel.getUUID(), time), playModel);
    }

    @Override
    public void enterCreationMode(ActorCreationController actorCreationController) {
        navigationController.disable();
        exitCreationMode();
        this.actorCreationController = actorCreationController;
        actorCreationController.enable(layerController, playModel, playService);
        super.addChild(actorCreationController);
    }

    private void exitCreationMode() {
        if (this.actorCreationController != null) {
            this.actorCreationController.disable();
        }
    }

    @Override
    public void enterNavigationMode() {
        exitCreationMode();
        navigationController.enable();
    }

    @Override
    public void enterFrameByFrameMode() {
        realTimeModeProperty.set(false);
        onFrameByFrameCreationModeEntered.fire(this);
    }

    @Override
    public void enterRealTimeMode() {
        realTimeModeProperty.set(true);
        onRealTimeCreationModeEntered.fire(this);
    }

    @Override
    public void toggleRealTimeMode() {
        if (realTimeModeProperty.get()) {
            enterFrameByFrameMode();
        } else {
            enterRealTimeMode();
        }
    }

    @Override
    public Zoom getZoom() {
        return navigationController.getZoom();
    }

    @Override
    public void setZoom(Zoom zoom) {
        navigationController.setZoom(zoom);
    }

    @Override
    public void zoomIn() {
        navigationController.zoomIn();
    }

    @Override
    public void zoomOut() {
        navigationController.zoomOut();
    }

    @Override
    public void autoFit() {
        navigationController.autoFit();
    }

    @Override
    public Zoom getMinZoom() {
        return navigationController.getMinZoom();
    }

    @Override
    public Zoom getMaxZoom() {
        return navigationController.getMaxZoom();
    }

    @Override
    public Boolean isActorLabelDisplayEnabled() {
        return showActorLabelsProperty.get();
    }

    @Override
    public void setActorLabelDisplay(Boolean showActorLabels) {
        this.showActorLabelsProperty.set(showActorLabels);
        settingsService.setShowActorLabels(showActorLabels);
    }

    @Override
    public Boolean isMovementArrowDisplayEnabled() {
        return showMovementArrowsProperty.get();
    }

    @Override
    public void setMovementArrowDisplay(Boolean showMovementArrows) {
        this.showMovementArrowsProperty.set(showMovementArrows);
        settingsService.setShowMovementArrows(showMovementArrows);
    }

    @Override
    public Boolean isResizeActorsOnZoomEnabled() {
        return resizeActorsOnZoomProperty.get();
    }

    @Override
    public void setResizeActorsOnZoom(Boolean resizeActorsOnZoom) {
        this.resizeActorsOnZoomProperty.set(resizeActorsOnZoom);
        settingsService.setResizeActorsOnZoom(resizeActorsOnZoom);
    }

    @FXML
    protected void onScrollPaneTouchPressed(TouchEvent e) {
        navigationController.onScrollPaneTouchPressed(e);
    }

    @FXML
    protected void onScrollPaneTouchMoved(TouchEvent e) {
        navigationController.onScrollPaneTouchMoved(e);
    }

    @FXML
    protected void onScrollPaneZoomStarted(ZoomEvent e) {
        navigationController.onScrollPaneZoomStarted(e);
    }

    @FXML
    protected void onScrollPaneZoom(ZoomEvent e) {
        navigationController.onScrollPaneZoom(e);
    }

    @FXML
    protected void onScrollPaneZoomFinished(ZoomEvent e) {
        navigationController.onScrollPaneZoomFinished(e);
    }

    @FXML
    protected void onScrollPaneMouseMoved(MouseEvent e) {
        navigationController.onScrollPaneMouseMoved(e);
    }

    private void onPlayingSurfaceMouseClicked(Object sender, Vector2 sizeRelativePosition) {
        if (actorCreationController != null) {
            actorCreationController.onPlayingSurfaceMouseClicked(sizeRelativePosition);
        }
    }

    private void onPlayingSurfaceMouseMoved(Object sender, Vector2 sizeRelativePosition) {
        if (actorCreationController != null) {
            actorCreationController.onPlayingSurfaceMouseMoved(sizeRelativePosition);
        }
    }

}
