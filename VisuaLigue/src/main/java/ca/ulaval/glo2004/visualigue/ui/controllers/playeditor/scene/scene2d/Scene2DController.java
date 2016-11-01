package ca.ulaval.glo2004.visualigue.ui.controllers.playeditor.scene.scene2d;

import ca.ulaval.glo2004.visualigue.domain.play.actorinstance.TeamSide;
import ca.ulaval.glo2004.visualigue.ui.View;
import ca.ulaval.glo2004.visualigue.ui.controllers.ControllerBase;
import ca.ulaval.glo2004.visualigue.ui.controllers.common.ExtendedScrollPane;
import ca.ulaval.glo2004.visualigue.ui.controllers.playeditor.scene.SceneController;
import ca.ulaval.glo2004.visualigue.ui.controllers.playeditor.scene.Zoom;
import ca.ulaval.glo2004.visualigue.ui.controllers.playeditor.scene.scene2d.layers.ActorLayerController;
import ca.ulaval.glo2004.visualigue.ui.controllers.playeditor.scene.scene2d.layers.ActorLayerViewFactory;
import ca.ulaval.glo2004.visualigue.ui.controllers.playeditor.scene.scene2d.layers.PlayingSurfaceLayerController;
import ca.ulaval.glo2004.visualigue.ui.models.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javafx.collections.MapChangeListener;
import javafx.collections.MapChangeListener.Change;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TouchEvent;
import javafx.scene.input.ZoomEvent;
import javafx.scene.layout.StackPane;
import javax.inject.Inject;

public class Scene2DController extends SceneController {

    @FXML private ExtendedScrollPane scrollPane;
    @FXML private StackPane scrollPaneContent;
    @FXML private StackPane layerStackPane;
    @FXML private PlayingSurfaceLayerController playingSurfaceLayerController;
    @Inject ActorLayerViewFactory actorLayerViewFactory;
    private NavigationController navigationController;
    private List<ControllerBase> sceneLayers = new ArrayList();
    private Map<ActorModel, ControllerBase> sceneLayerMap = new HashMap();
    private final FrameModel frameModel = new FrameModel();
    private PlayModel playModel;
    private Boolean playerCategoryLabelDisplayEnabled = false;

    @Override
    public void init(PlayModel playModel) {
        this.playModel = playModel;
        playingSurfaceLayerController.init(playModel, layerStackPane);
        frameModel.actorStates.addListener(this::onActorStateMapChanged);
        navigationController = new NavigationController(scrollPane, scrollPaneContent, playingSurfaceLayerController, playModel);
        navigationController.onMousePositionChanged.forward(onMousePositionChanged);
        navigationController.onZoomChanged.setHandler(this::onZoomChanged);
        navigationController.setZoom(new Zoom(1));
        sceneLayers.add(playingSurfaceLayerController);
    }

    private void onActorStateMapChanged(MapChangeListener.Change change) {
        onActorStateChanged(change);
    }

    private void onActorStateChanged(Change<String, ActorModel> change) {
        if (change.wasAdded()) {
            addActorLayer(change.getValueAdded());
        }
        if (change.wasRemoved()) {
            removeActorLayer(change.getValueRemoved());
        }
    }

    private void addActorLayer(ActorModel actorModel) {
        View view = actorLayerViewFactory.create(actorModel);
        ActorLayerController controller = (ActorLayerController) view.getController();
        controller.init(actorModel, playingSurfaceLayerController);
        super.addChild(controller);
        sceneLayers.add(controller);
        sceneLayerMap.put(actorModel, controller);
        layerStackPane.getChildren().add(view.getRoot());
    }

    private void removeActorLayer(ActorModel actorModel) {
        ControllerBase controller = sceneLayerMap.get(actorModel);
        layerStackPane.getChildren().remove(sceneLayers.indexOf(controller));
        sceneLayers.remove(controller);
        sceneLayerMap.remove(actorModel);
    }

    @Override
    public FrameModel getFrameModel() {
        return frameModel;
    }

    @Override
    public void enterPlayerCreationMode(PlayerCategoryModel playerCategoryModel, TeamSide teamSide) {
        onObstacleCreationModeExited.fire(this);
        onBallCreationModeExited.fire(this);
        onNavigationModeExited.fire(this);
    }

    @Override
    public void enterBallCreationMode(BallModel ballModel) {
        onObstacleCreationModeExited.fire(this);
        onPlayerCreationModeExited.fire(this);
        onNavigationModeExited.fire(this);
    }

    @Override
    public void enterObstacleCreationMode(ObstacleModel obstacleModel) {
        onPlayerCreationModeExited.fire(this);
        onBallCreationModeExited.fire(this);
        onNavigationModeExited.fire(this);
    }

    @Override
    public void enterFrameByFrameMode() {

    }

    @Override
    public void enterRealTimeMode() {

    }

    @Override
    public void enterNavigationMode() {
        onPlayerCreationModeExited.fire(this);
        onObstacleCreationModeExited.fire(this);
        onBallCreationModeExited.fire(this);
        onNavigationModeEntered.fire(this);
        navigationController.enterNavigationMode();
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
    public Boolean isPlayerCategoryLabelDisplayEnabled() {
        return playerCategoryLabelDisplayEnabled;
    }

    @Override
    public void setPlayerCategoryLabelDisplayEnabled(Boolean enabled) {
        playerCategoryLabelDisplayEnabled = enabled;
        getActorLayers().forEach(controller -> controller.setPlayerCategoryLabelDisplayEnabled(enabled));
        onPlayerCategoryLabelDisplayEnableChanged.fire(this, enabled);
    }

    private List<ActorLayerController> getActorLayers() {
        return sceneLayers.stream().filter(controller -> controller instanceof ActorLayerController).map(controller -> (ActorLayerController) controller).collect(Collectors.toList());
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

    private void onZoomChanged(Object sender, Zoom zoom) {
        getActorLayers().forEach(controller -> controller.updateZoom(zoom));
        onZoomChanged.fire(sender, zoom);
    }

}
