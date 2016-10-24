package ca.ulaval.glo2004.visualigue.ui.controllers.playeditor.scene;

import ca.ulaval.glo2004.visualigue.domain.play.actorinstance.TeamSide;
import ca.ulaval.glo2004.visualigue.ui.InjectableFXMLLoader;
import ca.ulaval.glo2004.visualigue.ui.View;
import ca.ulaval.glo2004.visualigue.ui.models.*;
import ca.ulaval.glo2004.visualigue.utils.FilenameUtils;
import java.util.*;
import javafx.collections.MapChangeListener;
import javafx.collections.MapChangeListener.Change;
import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

public class Scene2DController extends SceneController {

    private static final Double ZOOM_WIDTH_BASE = 800.0;
    private static final Double MIN_ZOOM = 0.5;
    private static final Double MAX_ZOOM = 5.0;
    private static final Double ZOOM_CHANGE_VALUE = 0.25;

    @FXML private ScrollPane scrollPane;
    @FXML private StackPane stackPane;
    @FXML private ImageView backgroundImageView;
    private List<View> sceneLayers = new ArrayList();
    private Map<ActorModel, View> sceneLayerMap = new HashMap();
    private FrameModel frameModel = new FrameModel();
    private PlayModel playModel;
    private Image backgroundImage;
    private Double zoom = 1.0;
    private Boolean playerCategoryLabelDisplayEnabled = false;

    @Override
    public void init(PlayModel playModel) {
        this.playModel = playModel;
        initPlayingSurfaceBackground();
        frameModel.actorStates.addListener(new MapChangeListener() {
            @Override
            public void onChanged(MapChangeListener.Change change) {
                onActorStateChanged(change);
            }
        });
        setZoom(1.0);
    }

    private void initPlayingSurfaceBackground() {
        if (playModel.customPlayingSurfaceImagePathName.isNotEmpty().get()) {
            backgroundImage = new Image(FilenameUtils.getURIString(playModel.customPlayingSurfaceImagePathName.get()));
        } else if (playModel.builtInPlayingSurfaceImagePathName.isNotEmpty().get()) {
            backgroundImage = new Image(playModel.builtInPlayingSurfaceImagePathName.get());
        }
        backgroundImageView.setImage(backgroundImage);
    }

    private void onActorStateChanged(Change<UUID, ActorModel> change) {
        if (change.wasAdded()) {
            View view = InjectableFXMLLoader.loadView(SceneLayerController.VIEW_NAME);
            SceneLayerController controller = (SceneLayerController) view.getController();
            ActorModel addedModel = change.getValueAdded();
            controller.init(addedModel, getBaseSceneWidth(), getBaseSceneHeight());
            sceneLayers.add(view);
            sceneLayerMap.put(change.getValueAdded(), view);
            stackPane.getChildren().add(view.getRoot());
        }
        if (change.wasRemoved()) {
            ActorModel removedActorModel = change.getValueRemoved();
            View view = sceneLayerMap.get(removedActorModel);
            stackPane.getChildren().remove(sceneLayers.indexOf(view));
            sceneLayers.remove(view);
            sceneLayerMap.remove(removedActorModel);
        }
    }

    @Override
    public FrameModel getFrameModel() {
        return frameModel;
    }

    @Override
    public void enterPlayerCreationMode(PlayerCategoryModel playerCategoryModel, TeamSide teamSide) {

    }

    @Override
    public void enterBallCreationMode(BallModel ballModel) {

    }

    @Override
    public void enterObstacleCreationMode(ObstacleModel obstacleModel) {

    }

    @Override
    public void enterFrameByFrameCreationMode() {

    }

    @Override
    public void enterRealTimeCreationMode() {

    }

    @Override
    public void exitCreationMode() {

    }

    @Override
    public Double getZoom() {
        return zoom;
    }

    private Double getBaseSceneWidth() {
        return ZOOM_WIDTH_BASE;
    }

    private Double getBaseSceneHeight() {
        return ZOOM_WIDTH_BASE / (backgroundImage.getWidth() / backgroundImage.getHeight());
    }

    @Override
    public void setZoom(Double zoom) {
        this.zoom = zoom;
        Double width = getBaseSceneWidth() * zoom;
        Double height = getBaseSceneHeight() * zoom;
        backgroundImageView.setFitWidth(width);
        backgroundImageView.setFitHeight(height);
        stackPane.setMinWidth(width);
        stackPane.setMinHeight(height);
        stackPane.setPrefWidth(width);
        stackPane.setPrefHeight(height);
        stackPane.setMaxWidth(width);
        stackPane.setMaxHeight(height);
        sceneLayers.forEach(view -> ((SceneLayerController) view.getController()).setZoom(zoom));
    }

    @Override
    public void zoomIn() {
        setZoom(Math.min(zoom + ZOOM_CHANGE_VALUE, getMaxZoom()));
    }

    @Override
    public void zoomOut() {
        setZoom(Math.max(zoom - ZOOM_CHANGE_VALUE, getMinZoom()));
    }

    @Override
    public void autoFit() {
        if (scrollPane.getWidth() / scrollPane.getHeight() > backgroundImage.getWidth() / backgroundImage.getHeight()) {
            setZoom(scrollPane.getHeight() / backgroundImage.getHeight());
        } else {
            setZoom(scrollPane.getWidth() / backgroundImage.getWidth());
        }
    }

    @Override
    public Double getMinZoom() {
        return MIN_ZOOM;
    }

    @Override
    public Double getMaxZoom() {
        return MAX_ZOOM;
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

    @Override
    public void undo() {

    }

    @Override
    public void redo() {

    }

}
