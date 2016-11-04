package ca.ulaval.glo2004.visualigue.ui.controllers.playeditor.scene.scene2d;

import ca.ulaval.glo2004.visualigue.domain.play.Play;
import ca.ulaval.glo2004.visualigue.domain.play.actorinstance.TeamSide;
import ca.ulaval.glo2004.visualigue.services.play.PlayService;
import ca.ulaval.glo2004.visualigue.services.settings.SettingsService;
import ca.ulaval.glo2004.visualigue.ui.InjectableFXMLLoader;
import ca.ulaval.glo2004.visualigue.ui.KeyboardShortcutMapper;
import ca.ulaval.glo2004.visualigue.ui.View;
import ca.ulaval.glo2004.visualigue.ui.controllers.common.ExtendedScrollPane;
import ca.ulaval.glo2004.visualigue.ui.controllers.playeditor.scene.SceneController;
import ca.ulaval.glo2004.visualigue.ui.controllers.playeditor.scene.Zoom;
import ca.ulaval.glo2004.visualigue.ui.controllers.playeditor.scene.scene2d.actorcreation.BallCreationController;
import ca.ulaval.glo2004.visualigue.ui.controllers.playeditor.scene.scene2d.actorcreation.ObstacleCreationController;
import ca.ulaval.glo2004.visualigue.ui.controllers.playeditor.scene.scene2d.actorcreation.PlayerCreationController;
import ca.ulaval.glo2004.visualigue.ui.controllers.playeditor.scene.scene2d.layers.ActorLayerViewFactory;
import ca.ulaval.glo2004.visualigue.ui.controllers.playeditor.scene.scene2d.layers.PlayingSurfaceLayerController;
import ca.ulaval.glo2004.visualigue.ui.converters.ActorModelConverter;
import ca.ulaval.glo2004.visualigue.ui.converters.FrameModelConverter;
import ca.ulaval.glo2004.visualigue.ui.models.*;
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
    @Inject private ActorLayerViewFactory actorLayerViewFactory;
    @Inject private ActorModelConverter actorModelConverter;
    @Inject private FrameModelConverter frameModelConverter;
    private BiConsumer<Object, Play> onPlayFrameChanged = this::onPlayFrameChanged;
    private Integer currentTime;
    private PlayingSurfaceLayerController playingSurfaceLayerController;
    private NavigationController navigationController;
    private LayerController layerController;
    private PlayerCreationController playerCreationController;
    private ObstacleCreationController obstacleCreationController;
    private BallCreationController ballCreationController;
    private final FrameModel frameModel = new FrameModel();

    @Override
    public void init(PlayModel playModel) {
        this.playModel = playModel;
        showActorLabelsProperty.set(settingsService.getShowActorLabels());
        showMovementArrowsProperty.set(settingsService.getShowMovementArrows());
        resizeActorsOnZoomProperty.set(settingsService.getResizeActorsOnZoom());
        playService.onPlayFrameChanged.addHandler(onPlayFrameChanged);
        initControllers();
        initKeyboardShortcuts();
        enterNavigationMode();
    }

    private void initControllers() {
        View playingSurfaceView = InjectableFXMLLoader.loadView(PlayingSurfaceLayerController.VIEW_NAME);
        playingSurfaceLayerController = (PlayingSurfaceLayerController) playingSurfaceView.getController();
        playingSurfaceLayerController.init(playModel, layerStackPane);
        navigationController = new NavigationController(scrollPane, scrollPaneContent, playingSurfaceLayerController);
        navigationController.onRealWorldMousePositionChanged.forward(onRealWorldMousePositionChanged);
        navigationController.onZoomChanged.forward(this.onZoomChanged);
        navigationController.onNavigationModeEntered.forward(this.onNavigationModeEntered);
        navigationController.onNavigationModeExited.forward(this.onNavigationModeExited);
        layerController = new LayerController(frameModel.actorModels, actorLayerViewFactory, layerStackPane, navigationController, playingSurfaceView, showActorLabelsProperty, showMovementArrowsProperty, resizeActorsOnZoomProperty);
        playerCreationController = new PlayerCreationController(playingSurfaceLayerController, layerController, actorModelConverter, playModel, playService);
        playerCreationController.onCreationModeExited.forward(this.onPlayerCreationModeExited);
        playerCreationController.onCreationModeEntered.forward(this.onCreationModeEntered);
        obstacleCreationController = new ObstacleCreationController(playingSurfaceLayerController, layerController, actorModelConverter, playModel, playService);
        obstacleCreationController.onCreationModeExited.forward(this.onObstacleCreationModeExited);
        obstacleCreationController.onCreationModeEntered.forward(this.onCreationModeEntered);
        ballCreationController = new BallCreationController(playingSurfaceLayerController, layerController, actorModelConverter, playModel, playService);
        ballCreationController.onCreationModeExited.forward(this.onBallCreationModeExited);
        ballCreationController.onCreationModeEntered.forward(this.onCreationModeEntered);
        super.addChild(playingSurfaceLayerController);
        super.addChild(navigationController);
        super.addChild(layerController);
        super.addChild(playerCreationController);
        super.addChild(obstacleCreationController);
        super.addChild(ballCreationController);
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
        playService.onPlayFrameChanged.removeHandler(onPlayFrameChanged);
    }

    private void onPlayFrameChanged(Object sender, Play play) {
        update(currentTime);
    }

    @Override
    public void update(Integer time) {
        currentTime = time;
        frameModelConverter.update(playService.getFrame(playModel.getUUID(), time), frameModel, playModel);
    }

    @Override
    public void enterPlayerCreationMode(PlayerCategoryModel playerCategoryModel, TeamSide teamSide) {
        navigationController.exitNavigationMode();
        obstacleCreationController.exitCreationMode();
        ballCreationController.exitCreationMode();
        playerCreationController.enterCreationMode(playerCategoryModel, teamSide);
    }

    @Override
    public void enterBallCreationMode(BallModel ballModel) {
        navigationController.exitNavigationMode();
        playerCreationController.exitCreationMode();
        obstacleCreationController.exitCreationMode();
        ballCreationController.enterCreationMode(ballModel);
    }

    @Override
    public void enterObstacleCreationMode(ObstacleModel obstacleModel) {
        navigationController.exitNavigationMode();
        playerCreationController.exitCreationMode();
        ballCreationController.exitCreationMode();
        obstacleCreationController.enterCreationMode(obstacleModel);
    }

    @Override
    public void enterNavigationMode() {
        playerCreationController.exitCreationMode();
        obstacleCreationController.exitCreationMode();
        ballCreationController.exitCreationMode();
        navigationController.enterNavigationMode();
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
    public Boolean isResizeActorOnZoomEnabled() {
        return resizeActorsOnZoomProperty.get();
    }

    @Override
    public void setResizeActorOnZoom(Boolean resizeActorsOnZoom) {
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

}
