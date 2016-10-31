package ca.ulaval.glo2004.visualigue.ui.controllers.playeditor.scene.scene2d;

import ca.ulaval.glo2004.visualigue.domain.play.actorinstance.TeamSide;
import ca.ulaval.glo2004.visualigue.ui.View;
import ca.ulaval.glo2004.visualigue.ui.controllers.common.ExtendedScrollPane;
import ca.ulaval.glo2004.visualigue.ui.controllers.playeditor.scene.SceneController;
import ca.ulaval.glo2004.visualigue.ui.controllers.playeditor.scene.Zoom;
import ca.ulaval.glo2004.visualigue.ui.controllers.playeditor.scene.scene2d.layers.LayerViewFactory;
import ca.ulaval.glo2004.visualigue.ui.controllers.playeditor.scene.scene2d.layers.SceneLayerController;
import ca.ulaval.glo2004.visualigue.ui.models.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
    @FXML private StackPane stackPane;
    @FXML private PlayingSurfaceController playingSurfaceController;
    @Inject LayerViewFactory layerViewFactory;
    private NavigationController navigationController;
    private List<View> sceneLayers = new ArrayList();
    private Map<ActorModel, View> sceneLayerMap = new HashMap();
    private FrameModel frameModel = new FrameModel();
    private PlayModel playModel;
    private Boolean playerCategoryLabelDisplayEnabled = false;

    @Override
    public void init(PlayModel playModel) {
        this.playModel = playModel;
        playingSurfaceController.init(playModel, stackPane);
        frameModel.actorStates.addListener(this::onActorStateMapChanged);
        navigationController = new NavigationController(scrollPane, scrollPaneContent, playingSurfaceController, playModel);
        navigationController.onMousePositionChanged.forward(onMousePositionChanged);
        navigationController.onZoomChanged.forward(onZoomChanged);
        navigationController.setZoom(new Zoom(1));
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
        View view = layerViewFactory.create(actorModel);
        SceneLayerController controller = (SceneLayerController) view.getController();
        controller.init(actorModel, playingSurfaceController);
        super.addChild(controller);
        sceneLayers.add(view);
        sceneLayerMap.put(actorModel, view);
        stackPane.getChildren().add(view.getRoot());
    }

    private void removeActorLayer(ActorModel actorModel) {
        View view = sceneLayerMap.get(actorModel);
        stackPane.getChildren().remove(sceneLayers.indexOf(view));
        sceneLayers.remove(view);
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
        sceneLayers.forEach(view -> ((SceneLayerController) view.getController()).setPlayerCategoryLabelDisplayEnabled(enabled));
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
