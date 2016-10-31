package ca.ulaval.glo2004.visualigue.ui.controllers.playeditor.scene.scene2d;

import ca.ulaval.glo2004.visualigue.ui.controllers.ControllerBase;
import ca.ulaval.glo2004.visualigue.ui.controllers.common.ExtendedScrollPane;
import static ca.ulaval.glo2004.visualigue.ui.controllers.playeditor.scene.SceneController.PREDEFINED_ZOOMS;
import ca.ulaval.glo2004.visualigue.ui.controllers.playeditor.scene.Zoom;
import ca.ulaval.glo2004.visualigue.ui.models.PlayModel;
import ca.ulaval.glo2004.visualigue.utils.EventHandler;
import ca.ulaval.glo2004.visualigue.utils.geometry.Vector2;
import ca.ulaval.glo2004.visualigue.utils.math.MathUtils;
import javafx.beans.value.ObservableValue;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.input.TouchEvent;
import javafx.scene.input.ZoomEvent;
import javafx.scene.layout.StackPane;

public class NavigationController extends ControllerBase {

    private static final Zoom MIN_ZOOM = new Zoom(0.5);
    private static final Zoom MAX_ZOOM = new Zoom(5.0);
    public EventHandler<Vector2> onMousePositionChanged = new EventHandler();
    public EventHandler<Zoom> onZoomChanged = new EventHandler();
    private Vector2 contentAlignPoint;
    private Vector2 viewportAlignPoint;
    private Zoom zoom;
    private Boolean touchZooming = false;
    private Vector2 touchPoint1;
    private Vector2 touchPoint2;
    private Vector2 mousePressContentPoint;
    private ExtendedScrollPane scrollPane;
    private StackPane scrollPaneContent;
    private PlayingSurfaceController playingSurfaceController;
    private PlayModel playModel;

    public NavigationController(ExtendedScrollPane scrollPane, StackPane scrollPaneContent, PlayingSurfaceController playingSurfaceController, PlayModel playModel) {
        this.scrollPane = scrollPane;
        this.scrollPaneContent = scrollPaneContent;
        scrollPaneContent.widthProperty().addListener(this::scrollPaneContentWidthChangedListener);
        scrollPaneContent.heightProperty().addListener(this::scrollPaneContentHeightChangedListener);
        this.playingSurfaceController = playingSurfaceController;
        playingSurfaceController.onMouseDragged.addHandler(this::onPlayingSurfaceMouseDragged);
        playingSurfaceController.onMousePressed.addHandler(this::onPlayingSurfaceMousePressed);
        playingSurfaceController.onMouseReleased.addHandler(this::onPlayingSurfaceMouseReleased);
        this.playModel = playModel;
        scrollPane.addEventFilter(ScrollEvent.ANY, this::scrollPaneEventFilter);
    }

    public Zoom getMinZoom() {
        return MIN_ZOOM;
    }

    public Zoom getMaxZoom() {
        return MAX_ZOOM;
    }

    public Zoom getZoom() {
        return zoom;
    }

    public void setZoom(Zoom zoom) {
        if (MathUtils.lessThan(zoom, getMinZoom())) {
            zoom = getMinZoom();
        } else if (MathUtils.greaterThan(zoom, getMaxZoom())) {
            zoom = getMaxZoom();
        }
        this.zoom = zoom;
        if (!touchZooming) {
            contentAlignPoint = scrollPane.mouseToRelativeContentPoint();
            viewportAlignPoint = scrollPane.mouseToViewportPoint();
        }
        if (contentAlignPoint == null || viewportAlignPoint == null) {
            contentAlignPoint = scrollPane.contentToRelativePoint(scrollPane.getVisibleContentCenter());
            viewportAlignPoint = scrollPane.getViewportCenter();
        }
        playingSurfaceController.setZoom(zoom);
        onZoomChanged.fire(this, zoom);
    }

    public void zoomIn() {
        Zoom nextZoom = PREDEFINED_ZOOMS.higher(PREDEFINED_ZOOMS.ceiling(zoom));
        if (nextZoom != null) {
            setZoom(nextZoom);
        }
    }

    public void zoomOut() {
        Zoom nextZoom = PREDEFINED_ZOOMS.lower(PREDEFINED_ZOOMS.floor(zoom));
        if (nextZoom != null) {
            setZoom(nextZoom);
        }
    }

    public void autoFit() {
        Vector2 baseSceneSize = playingSurfaceController.getBaseSurfaceSize();
        if (scrollPane.getWidth() / scrollPane.getHeight() > baseSceneSize.getX() / baseSceneSize.getY()) {
            setZoom(new Zoom(scrollPane.getHeight() / baseSceneSize.getY()));
        } else {
            setZoom(new Zoom(scrollPane.getWidth() / baseSceneSize.getX()));
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
        mousePressContentPoint = scrollPane.mouseToContentPoint();
    }

    protected void onPlayingSurfaceMouseDragged(Object sender, MouseEvent e) {
        if (!touchZooming && scrollPane.mouseToViewportPoint() != null && mousePressContentPoint != null) {
            scrollPane.align(mousePressContentPoint, scrollPane.mouseToViewportPoint());
        }
    }

    protected void onPlayingSurfaceMouseReleased(Object sender, MouseEvent e) {
        mousePressContentPoint = null;
    }

    private void scrollPaneEventFilter(ScrollEvent scrollEvent) {
        scrollEvent.consume();
        if (!scrollEvent.isDirect()) {
            Double delta = scrollEvent.getDeltaY() / 100;
            setZoom(new Zoom(getZoom().getValue() + delta));
        }
    }

    public void onScrollPaneTouchPressed(TouchEvent e) {
        if (e.getTouchPoints().size() == 2) {
            Vector2 contentPoint1 = scrollPane.sceneToContentPoint(new Vector2(e.getTouchPoints().get(0).getSceneX(), e.getTouchPoints().get(0).getSceneY()));
            Vector2 contentPoint2 = scrollPane.sceneToContentPoint(new Vector2(e.getTouchPoints().get(1).getSceneX(), e.getTouchPoints().get(1).getSceneY()));
            contentAlignPoint = scrollPane.contentToRelativePoint(contentPoint1.average(contentPoint2));
        }
    }

    public void onScrollPaneTouchMoved(TouchEvent e) {
        if (touchZooming && e.getTouchPoints().size() == 2) {
            touchPoint1 = scrollPane.sceneToViewportPoint(new Vector2(e.getTouchPoints().get(0).getSceneX(), e.getTouchPoints().get(0).getSceneY()));
            touchPoint2 = scrollPane.sceneToViewportPoint(new Vector2(e.getTouchPoints().get(1).getSceneX(), e.getTouchPoints().get(1).getSceneY()));
        }
    }

    public void onScrollPaneZoomStarted(ZoomEvent e) {
        touchZooming = true;
    }

    public void onScrollPaneZoom(ZoomEvent e) {
        if (touchPoint1 != null && touchPoint2 != null) {
            viewportAlignPoint = touchPoint1.average(touchPoint2);
            setZoom(new Zoom(getZoom().getValue() * e.getZoomFactor()));
        }
    }

    public void onScrollPaneZoomFinished(ZoomEvent e) {
        touchZooming = false;
        touchPoint1 = null;
        touchPoint2 = null;
    }

    public void onScrollPaneMouseMoved(MouseEvent e) {
        Vector2 surfacePosition = playingSurfaceController.getMousePosition();
        onMousePositionChanged.fire(this, surfacePosition);
    }
}
