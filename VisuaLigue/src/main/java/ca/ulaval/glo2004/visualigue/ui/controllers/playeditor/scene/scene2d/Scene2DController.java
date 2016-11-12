package ca.ulaval.glo2004.visualigue.ui.controllers.playeditor.scene.scene2d;

import ca.ulaval.glo2004.visualigue.domain.play.Play;
import ca.ulaval.glo2004.visualigue.services.play.PlayService;
import ca.ulaval.glo2004.visualigue.ui.InjectableFXMLLoader;
import ca.ulaval.glo2004.visualigue.ui.KeyboardShortcutMapper;
import ca.ulaval.glo2004.visualigue.ui.View;
import ca.ulaval.glo2004.visualigue.ui.controllers.common.ExtendedScrollPane;
import ca.ulaval.glo2004.visualigue.ui.controllers.playeditor.actor.ActorFactory;
import ca.ulaval.glo2004.visualigue.ui.controllers.playeditor.scene.SceneController;
import ca.ulaval.glo2004.visualigue.ui.controllers.playeditor.scene.Zoom;
import ca.ulaval.glo2004.visualigue.ui.converters.FrameModelConverter;
import ca.ulaval.glo2004.visualigue.ui.models.FrameModel;
import ca.ulaval.glo2004.visualigue.ui.models.PlayModel;
import ca.ulaval.glo2004.visualigue.utils.geometry.Vector2;
import java.util.function.BiConsumer;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.scene.input.*;
import javafx.scene.layout.StackPane;
import javax.inject.Inject;

public class Scene2DController extends SceneController {

    @FXML private ExtendedScrollPane scrollPane;
    @FXML public StackPane sceneViewport;
    @FXML private StackPane layerStackPane;
    @Inject private PlayService playService;
    @Inject private ActorFactory actorFactory;
    @Inject private FrameModelConverter frameModelConverter;
    private BiConsumer<Object, Play> onPlayFrameChanged = this::onPlayFrameChanged;
    private PlayingSurfaceLayerController playingSurfaceLayerController;
    private NavigationController navigationController;
    private LayerController layerController;
    private ActorCreationController actorCreationController;
    private FrameModel frameModel = new FrameModel();
    private BooleanProperty realTimeModeProperty = new SimpleBooleanProperty(false);

    @Override
    public void init(PlayModel playModel) {
        this.playModel = playModel;
        playService.onFrameChanged.addHandler(onPlayFrameChanged);
        initControllers();
        initKeyboardShortcuts();
    }

    private void initControllers() {
        View playingSurfaceView = InjectableFXMLLoader.loadView(PlayingSurfaceLayerController.VIEW_NAME);
        playingSurfaceLayerController = (PlayingSurfaceLayerController) playingSurfaceView.getController();
        playingSurfaceLayerController.init(playModel, layerStackPane);
        navigationController = new NavigationController(scrollPane, sceneViewport, playingSurfaceLayerController);
        navigationController.onEnabled.forward(this.onNavigationModeEntered);
        navigationController.onDisabled.forward(this.onNavigationModeExited);
        layerController = new LayerController(layerStackPane, actorFactory, playModel, frameModel, playingSurfaceLayerController, navigationController.zoomProperty(), settings);
        layerController.addView(playingSurfaceView, Integer.MIN_VALUE);
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
        super.clean();
    }

    private void onPlayFrameChanged(Object sender, Play play) {
        update(frameModel.time.get());
    }

    @Override
    public void update(Long time) {
        frameModelConverter.update(frameModel, playService.getFrame(playModel.getUUID(), time, settings.showPlayerTrailsOnLastFrameProperty.get()), playModel);
    }

    @Override
    public void enterCreationMode(ActorCreationController actorCreationController) {
        navigationController.disable();
        exitCreationMode();
        this.actorCreationController = actorCreationController;
        actorCreationController.enable(layerController, playingSurfaceLayerController, playModel, frameModel);
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
    public ReadOnlyObjectProperty<Vector2> realWorldMousePositionProperty() {
        return navigationController.realWorldMousePositionProperty();
    }

    @Override
    public ObjectProperty<Zoom> zoomProperty() {
        return navigationController.zoomProperty();
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

    @FXML
    protected void onSceneTouchPressed(TouchEvent e) {
        navigationController.onSceneTouchPressed(e);
    }

    @FXML
    protected void onSceneTouchMoved(TouchEvent e) {
        navigationController.onSceneTouchMoved(e);
    }

    @FXML
    protected void onSceneZoomStarted(ZoomEvent e) {
        navigationController.onSceneZoomStarted(e);
    }

    @FXML
    protected void onSceneZoom(ZoomEvent e) {
        navigationController.onSceneZoom(e);
    }

    @FXML
    protected void onSceneZoomFinished(ZoomEvent e) {
        navigationController.onSceneZoomFinished(e);
    }

    @FXML
    protected void onSceneMouseClicked(MouseEvent e) {
        if (actorCreationController != null) {
            actorCreationController.onSceneMouseClicked(e);
        }
    }

    @FXML
    protected void onSceneMouseMoved(MouseEvent e) {
        navigationController.onSceneMouseMoved(e);
        if (actorCreationController != null) {
            actorCreationController.onSceneMouseMoved(e);
        }
    }

    @FXML
    protected void onSceneMousePressed(MouseEvent e) {
        navigationController.onSceneMousePressed(e);
    }

    @FXML
    protected void onSceneMouseDragged(MouseEvent e) {
        navigationController.onSceneMouseDragged(e);
    }

    @FXML
    protected void onSceneMouseReleased(MouseEvent e) {
        navigationController.onSceneMouseReleased(e);
    }

    @FXML
    protected void onSceneMouseEntered(MouseEvent e) {
        if (actorCreationController != null) {
            actorCreationController.onSceneMouseEntered(e);
        }
    }

    @FXML
    protected void onSceneMouseExited(MouseEvent e) {
        if (actorCreationController != null) {
            actorCreationController.onSceneMouseExited(e);
        }
    }

}
