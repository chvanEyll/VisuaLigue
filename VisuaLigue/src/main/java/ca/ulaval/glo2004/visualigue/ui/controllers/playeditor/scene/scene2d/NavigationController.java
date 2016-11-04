package ca.ulaval.glo2004.visualigue.ui.controllers.playeditor.scene.scene2d;

import ca.ulaval.glo2004.visualigue.ui.controllers.ControllerBase;
import ca.ulaval.glo2004.visualigue.ui.controllers.common.ExtendedScrollPane;
import static ca.ulaval.glo2004.visualigue.ui.controllers.playeditor.scene.SceneController.PREDEFINED_ZOOMS;
import ca.ulaval.glo2004.visualigue.ui.controllers.playeditor.scene.Zoom;
import ca.ulaval.glo2004.visualigue.ui.controllers.playeditor.scene.scene2d.layers.PlayingSurfaceLayerController;
import ca.ulaval.glo2004.visualigue.utils.EventHandler;
import ca.ulaval.glo2004.visualigue.utils.geometry.Vector2;
import ca.ulaval.glo2004.visualigue.utils.javafx.FXUtils;
import ca.ulaval.glo2004.visualigue.utils.math.MathUtils;
import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.scene.Cursor;
import javafx.scene.ImageCursor;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.input.TouchEvent;
import javafx.scene.input.ZoomEvent;
import javafx.scene.layout.StackPane;

public class NavigationController extends ControllerBase {

    public EventHandler onNavigationModeEntered = new EventHandler();
    public EventHandler onNavigationModeExited = new EventHandler();
    private static final Zoom MIN_ZOOM = new Zoom(0.5);
    private static final Zoom MAX_ZOOM = new Zoom(5.0);
    public EventHandler<Vector2> onRealWorldMousePositionChanged = new EventHandler();
    public EventHandler<Zoom> onZoomChanged = new EventHandler();
    private Vector2 contentAlignPoint;
    private Vector2 viewportAlignPoint;
    private ObjectProperty<Zoom> zoomProperty = new SimpleObjectProperty(new Zoom(1));
    private Boolean touchZooming = false;
    private Vector2 touchPoint1;
    private Vector2 touchPoint2;
    private Vector2 mousePressContentPoint;
    private ExtendedScrollPane scrollPane;
    private PlayingSurfaceLayerController playingSurfaceLayerController;
    private Boolean enabled = false;

    public NavigationController(ExtendedScrollPane scrollPane, StackPane scrollPaneContent, PlayingSurfaceLayerController playingSurfaceLayerController) {
        this.scrollPane = scrollPane;
        scrollPaneContent.widthProperty().addListener(this::scrollPaneContentWidthChangedListener);
        scrollPaneContent.heightProperty().addListener(this::scrollPaneContentHeightChangedListener);
        this.playingSurfaceLayerController = playingSurfaceLayerController;
        playingSurfaceLayerController.onMouseDragged.addHandler(this::onPlayingSurfaceMouseDragged);
        playingSurfaceLayerController.onMousePressed.addHandler(this::onPlayingSurfaceMousePressed);
        playingSurfaceLayerController.onMouseReleased.addHandler(this::onPlayingSurfaceMouseReleased);
        scrollPane.addEventFilter(ScrollEvent.ANY, this::scrollPaneEventFilter);
        Platform.runLater(() -> {
            autoFit();
        });
    }

    public Zoom getMinZoom() {
        return MIN_ZOOM;
    }

    public Zoom getMaxZoom() {
        return MAX_ZOOM;
    }

    public Zoom getZoom() {
        return zoomProperty.get();
    }

    public ObjectProperty<Zoom> getZoomProperty() {
        return zoomProperty;
    }

    public void setZoom(Zoom zoom) {
        if (MathUtils.lessThan(zoom, getMinZoom())) {
            zoom = getMinZoom();
        } else if (MathUtils.greaterThan(zoom, getMaxZoom())) {
            zoom = getMaxZoom();
        }
        if (!touchZooming) {
            contentAlignPoint = scrollPane.mouseToRelativeContentPoint();
            viewportAlignPoint = scrollPane.mouseToViewportPoint();
        }
        if (contentAlignPoint == null || viewportAlignPoint == null) {
            contentAlignPoint = scrollPane.contentToRelativePoint(scrollPane.getVisibleContentCenter());
            viewportAlignPoint = scrollPane.getViewportCenter();
        }
        playingSurfaceLayerController.setZoom(zoom);
        onZoomChanged.fire(this, zoom);
        this.zoomProperty.set(zoom);
    }

    public void zoomIn() {
        Zoom nextZoom = PREDEFINED_ZOOMS.higher(PREDEFINED_ZOOMS.ceiling(zoomProperty.get()));
        if (nextZoom == null) {
            nextZoom = PREDEFINED_ZOOMS.last();
        }
        setZoom(nextZoom);
    }

    public void zoomOut() {
        Zoom nextZoom = PREDEFINED_ZOOMS.lower(PREDEFINED_ZOOMS.floor(zoomProperty.get()));
        if (nextZoom == null) {
            nextZoom = PREDEFINED_ZOOMS.first();
        }
        setZoom(nextZoom);
    }

    public void autoFit() {
        Vector2 baseSceneSize = playingSurfaceLayerController.getBaseSurfaceSize();
        if (scrollPane.getWidth() / scrollPane.getHeight() > baseSceneSize.getX() / baseSceneSize.getY()) {
            setZoom(new Zoom(scrollPane.getHeight() / baseSceneSize.getY()));
        } else {
            setZoom(new Zoom(scrollPane.getWidth() / baseSceneSize.getX()));
        }
    }

    public void enterNavigationMode() {
        if (!enabled) {
            ImageCursor imageCursor = FXUtils.chooseBestCursor("/images/cursors/pan-%1$sx%1$s.png", new int[]{32, 48, 96, 128}, 16, 16);
            playingSurfaceLayerController.setCursor(imageCursor);
            enabled = true;
            onNavigationModeEntered.fire(this);
        }
    }

    public void exitNavigationMode() {
        if (enabled) {
            playingSurfaceLayerController.setCursor(Cursor.DEFAULT);
            enabled = false;
            onNavigationModeExited.fire(this);
        }
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

    protected void onPlayingSurfaceMousePressed(Object sender, MouseEvent e) {
        if (enabled) {
            mousePressContentPoint = scrollPane.mouseToContentPoint();
        }
    }

    protected void onPlayingSurfaceMouseDragged(Object sender, MouseEvent e) {
        if (enabled && !touchZooming && scrollPane.mouseToViewportPoint() != null && mousePressContentPoint != null) {
            scrollPane.align(mousePressContentPoint, scrollPane.mouseToViewportPoint());
        }
    }

    protected void onPlayingSurfaceMouseReleased(Object sender, MouseEvent e) {
        if (enabled) {
            mousePressContentPoint = null;
        }
    }

    private void scrollPaneEventFilter(ScrollEvent scrollEvent) {
        scrollEvent.consume();
        if (!scrollEvent.isDirect()) {
            Double delta = scrollEvent.getDeltaY() / 100;
            setZoom(new Zoom(getZoom().getValue() + delta));
        }
    }

    public void onScrollPaneTouchPressed(TouchEvent e) {
        if (enabled && e.getTouchPoints().size() == 2) {
            Vector2 contentPoint1 = scrollPane.sceneToContentPoint(new Vector2(e.getTouchPoints().get(0).getSceneX(), e.getTouchPoints().get(0).getSceneY()));
            Vector2 contentPoint2 = scrollPane.sceneToContentPoint(new Vector2(e.getTouchPoints().get(1).getSceneX(), e.getTouchPoints().get(1).getSceneY()));
            contentAlignPoint = scrollPane.contentToRelativePoint(contentPoint1.average(contentPoint2));
        }
    }

    public void onScrollPaneTouchMoved(TouchEvent e) {
        if (enabled && touchZooming && e.getTouchPoints().size() == 2) {
            touchPoint1 = scrollPane.sceneToViewportPoint(new Vector2(e.getTouchPoints().get(0).getSceneX(), e.getTouchPoints().get(0).getSceneY()));
            touchPoint2 = scrollPane.sceneToViewportPoint(new Vector2(e.getTouchPoints().get(1).getSceneX(), e.getTouchPoints().get(1).getSceneY()));
        }
    }

    public void onScrollPaneZoomStarted(ZoomEvent e) {
        if (enabled) {
            touchZooming = true;
        }
    }

    public void onScrollPaneZoom(ZoomEvent e) {
        if (enabled && touchPoint1 != null && touchPoint2 != null) {
            viewportAlignPoint = touchPoint1.average(touchPoint2);
            setZoom(new Zoom(getZoom().getValue() * e.getZoomFactor()));
        }
    }

    public void onScrollPaneZoomFinished(ZoomEvent e) {
        if (enabled) {
            touchZooming = false;
            touchPoint1 = null;
            touchPoint2 = null;
        }
    }

    public void onScrollPaneMouseMoved(MouseEvent e) {
        Vector2 realWorldSurfacePosition = playingSurfaceLayerController.getRealWorldMousePosition();
        onRealWorldMousePositionChanged.fire(this, realWorldSurfacePosition);
    }
}
