package ca.ulaval.glo2004.visualigue.ui.controllers.playeditor.scene.scene2d;

import ca.ulaval.glo2004.visualigue.domain.play.actorinstance.TeamSide;
import ca.ulaval.glo2004.visualigue.ui.InjectableFXMLLoader;
import ca.ulaval.glo2004.visualigue.ui.View;
import ca.ulaval.glo2004.visualigue.ui.controllers.common.ExtendedScrollPane;
import ca.ulaval.glo2004.visualigue.ui.controllers.playeditor.scene.SceneController;
import ca.ulaval.glo2004.visualigue.ui.controllers.playeditor.scene.Zoom;
import ca.ulaval.glo2004.visualigue.ui.models.*;
import ca.ulaval.glo2004.visualigue.utils.FXUtils;
import ca.ulaval.glo2004.visualigue.utils.FilenameUtils;
import ca.ulaval.glo2004.visualigue.utils.geometry.Vector2;
import ca.ulaval.glo2004.visualigue.utils.math.MathUtils;
import java.util.*;
import javafx.beans.value.ObservableValue;
import javafx.collections.MapChangeListener;
import javafx.collections.MapChangeListener.Change;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.ImageCursor;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.input.TouchEvent;
import javafx.scene.input.ZoomEvent;
import javafx.scene.layout.StackPane;

public class Scene2DController extends SceneController {

    private static final Double ZOOM_WIDTH_BASE = 1000.0;
    private static final Zoom MIN_ZOOM = new Zoom(0.5);
    private static final Zoom MAX_ZOOM = new Zoom(5.0);

    @FXML private ExtendedScrollPane scrollPane;
    @FXML private StackPane scrollPaneContent;
    @FXML private StackPane stackPane;
    @FXML private ImageView backgroundImageView;
    private List<View> sceneLayers = new ArrayList();
    private Map<ActorModel, View> sceneLayerMap = new HashMap();
    private FrameModel frameModel = new FrameModel();
    private PlayModel playModel;
    private Image backgroundImage;
    private Zoom zoom;
    private Boolean playerCategoryLabelDisplayEnabled = false;
    private Vector2 contentAlignPoint;
    private Vector2 viewportAlignPoint;
    private Boolean isMousePressed = false;
    private Boolean isZoomStarted = false;
    private Vector2 startTouchPoint1;
    private Vector2 startTouchPoint2;
    private Vector2 startContentPoint1;
    private Vector2 startContentPoint2;
    private Vector2 movedTouchPoint1;
    private Vector2 movedTouchPoint2;
    private Vector2 mousePressContentLocation;

    @Override
    public void init(PlayModel playModel) {
        this.playModel = playModel;
        initPlayingSurfaceBackground();
        frameModel.actorStates.addListener(this::onActorStateMapChanged);
        stackPane.minWidthProperty().bind(backgroundImageView.fitWidthProperty());
        stackPane.maxWidthProperty().bind(backgroundImageView.fitWidthProperty());
        stackPane.minHeightProperty().bind(backgroundImageView.fitHeightProperty());
        stackPane.maxHeightProperty().bind(backgroundImageView.fitHeightProperty());
        scrollPane.addEventFilter(ScrollEvent.ANY, new ScrollHandler());
        scrollPaneContent.widthProperty().addListener(this::scrollPaneContentWidthChangedListener);
        scrollPaneContent.heightProperty().addListener(this::scrollPaneContentHeightChangedListener);
        setZoom(new Zoom(1));
    }

    private class ScrollHandler implements EventHandler<ScrollEvent> {

        @Override
        public void handle(ScrollEvent scrollEvent) {
            scrollEvent.consume();
            if (!scrollEvent.isDirect()) {
                Double delta = scrollEvent.getDeltaY() / 100;
                setZoom(new Zoom(getZoom().getValue() + delta));
            }
        }
    }

    private void onActorStateMapChanged(MapChangeListener.Change change) {
        onActorStateChanged(change);
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
        onObstacleCreationModeExited.fire(this, null);
        onBallCreationModeExited.fire(this, null);
        onNavigationModeExited.fire(this, null);
    }

    @Override
    public void enterBallCreationMode(BallModel ballModel) {
        onObstacleCreationModeExited.fire(this, null);
        onPlayerCreationModeExited.fire(this, null);
        onNavigationModeExited.fire(this, null);
    }

    @Override
    public void enterObstacleCreationMode(ObstacleModel obstacleModel) {
        onPlayerCreationModeExited.fire(this, null);
        onBallCreationModeExited.fire(this, null);
        onNavigationModeExited.fire(this, null);
    }

    @Override
    public void enterFrameByFrameMode() {

    }

    @Override
    public void enterRealTimeMode() {

    }

    @Override
    public void enterNavigationMode() {
        onPlayerCreationModeExited.fire(this, null);
        onObstacleCreationModeExited.fire(this, null);
        onBallCreationModeExited.fire(this, null);
        onNavigationModeEntered.fire(this, null);
        ImageCursor imageCursor = FXUtils.chooseBestCursor("/images/cursors/pan-%1$sx%1$s.png", new int[]{32, 48, 96, 128}, 16, 16);
        backgroundImageView.setCursor(imageCursor);
    }

    @Override
    public Zoom getZoom() {
        return zoom;
    }

    private Double getBaseSceneWidth() {
        return ZOOM_WIDTH_BASE;
    }

    private Double getBaseSceneHeight() {
        return ZOOM_WIDTH_BASE / (backgroundImage.getWidth() / backgroundImage.getHeight());
    }

    @Override
    public void setZoom(Zoom zoom) {
        if (MathUtils.lessThan(zoom, getMinZoom())) {
            zoom = getMinZoom();
        } else if (MathUtils.greaterThan(zoom, getMaxZoom())) {
            zoom = getMaxZoom();
        }
        this.zoom = zoom;
        if (!isZoomStarted) {
            contentAlignPoint = scrollPane.mouseToRelativeContentPoint();
            viewportAlignPoint = scrollPane.mouseToViewportPoint();
        }
        if (contentAlignPoint == null || viewportAlignPoint == null) {
            contentAlignPoint = scrollPane.contentToRelativePoint(scrollPane.getVisibleContentCenter());
            viewportAlignPoint = scrollPane.getViewportCenter();
        }
        backgroundImageView.setFitWidth(getBaseSceneWidth() * zoom.getValue());
        backgroundImageView.setFitHeight(getBaseSceneHeight() * zoom.getValue());
        onZoomChanged.fire(this, zoom);
    }

    public void scrollPaneContentWidthChangedListener(ObservableValue<? extends Number> value, Number oldPropertyValue, Number newPropertyValue) {
        if (contentAlignPoint != null && viewportAlignPoint != null) {
            Vector2 newContentAlignPoint = scrollPane.relativeToContentPoint(contentAlignPoint);
            scrollPane.alignX(newContentAlignPoint.getX(), viewportAlignPoint.getX());
        }
    }

    public void scrollPaneContentHeightChangedListener(ObservableValue<? extends Number> value, Number oldPropertyValue, Number newPropertyValue) {
        if (contentAlignPoint != null && viewportAlignPoint != null) {
            Vector2 newContentAlignPoint = scrollPane.relativeToContentPoint(contentAlignPoint);
            scrollPane.alignY(newContentAlignPoint.getY(), viewportAlignPoint.getY());
        }
    }

    @Override
    public void zoomIn() {
        Zoom nextZoom = PREDEFINED_ZOOMS.higher(PREDEFINED_ZOOMS.ceiling(zoom));
        if (nextZoom != null) {
            setZoom(nextZoom);
        }
    }

    @Override
    public void zoomOut() {
        Zoom nextZoom = PREDEFINED_ZOOMS.lower(PREDEFINED_ZOOMS.floor(zoom));
        if (nextZoom != null) {
            setZoom(nextZoom);
        }
    }

    @Override
    public void autoFit() {
        if (scrollPane.getWidth() / scrollPane.getHeight() > getBaseSceneWidth() / getBaseSceneHeight()) {
            setZoom(new Zoom(scrollPane.getHeight() / getBaseSceneHeight()));
        } else {
            setZoom(new Zoom(scrollPane.getWidth() / getBaseSceneWidth()));
        }
    }

    @Override
    public Zoom getMinZoom() {
        return MIN_ZOOM;
    }

    @Override
    public Zoom getMaxZoom() {
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

    @FXML
    protected void onBackgroundMousePressed(MouseEvent e) {
        isMousePressed = true;
        mousePressContentLocation = scrollPane.mouseToContentPoint();
    }

    @FXML
    protected void onBackgroundMouseDragged(MouseEvent e) {
        if (scrollPane.mouseToViewportPoint() != null && !isZoomStarted) {
            scrollPane.align(mousePressContentLocation, scrollPane.mouseToViewportPoint());
        }
    }

    @FXML
    protected void onBackgroundMouseReleased(MouseEvent e) {
        isMousePressed = false;
    }

    @FXML
    protected void onScrollPaneTouchPressed(TouchEvent e) {
        if (e.getTouchPoints().size() == 2) {
            startTouchPoint1 = new Vector2(e.getTouchPoints().get(0).getSceneX(), e.getTouchPoints().get(0).getSceneY());
            startTouchPoint2 = new Vector2(e.getTouchPoints().get(1).getSceneX(), e.getTouchPoints().get(1).getSceneY());
            startContentPoint1 = scrollPane.sceneToContentPoint(startTouchPoint1);
            startContentPoint2 = scrollPane.sceneToContentPoint(startTouchPoint2);
            contentAlignPoint = scrollPane.contentToRelativePoint(startContentPoint1.average(startContentPoint2));
        }
    }

    @FXML
    protected void onScrollPaneZoomStarted(ZoomEvent e) {
        isZoomStarted = true;
    }

    @FXML
    protected void onScrollPaneZoomFinished(ZoomEvent e) {
        isZoomStarted = false;
        movedTouchPoint1 = null;
        movedTouchPoint2 = null;
    }

    @FXML
    protected void onScrollPaneTouchMoved(TouchEvent e) {
        if (isZoomStarted) {
            movedTouchPoint1 = scrollPane.sceneToViewportPoint(new Vector2(e.getTouchPoints().get(0).getSceneX(), e.getTouchPoints().get(0).getSceneY()));
            movedTouchPoint2 = scrollPane.sceneToViewportPoint(new Vector2(e.getTouchPoints().get(1).getSceneX(), e.getTouchPoints().get(1).getSceneY()));
        }
    }

    @FXML
    protected void onScrollPaneZoom(ZoomEvent e) {
        if (movedTouchPoint1 != null && movedTouchPoint2 != null) {
            viewportAlignPoint = movedTouchPoint1.average(movedTouchPoint2);
            System.out.println(contentAlignPoint);
            System.out.println("\t" + viewportAlignPoint);
            setZoom(new Zoom(getZoom().getValue() * e.getZoomFactor()));
        }
    }
}