package ca.ulaval.glo2004.visualigue.ui.controllers.playeditor.scene.scene2d;

import ca.ulaval.glo2004.visualigue.ui.controllers.ControllerBase;
import ca.ulaval.glo2004.visualigue.ui.controllers.common.ExtendedScrollPane;
import static ca.ulaval.glo2004.visualigue.ui.controllers.playeditor.scene.SceneController.PREDEFINED_ZOOMS;
import ca.ulaval.glo2004.visualigue.ui.controllers.playeditor.scene.Zoom;
import ca.ulaval.glo2004.visualigue.utils.EventHandler;
import ca.ulaval.glo2004.visualigue.utils.geometry.Vector2;
import ca.ulaval.glo2004.visualigue.utils.javafx.FXUtils;
import ca.ulaval.glo2004.visualigue.utils.math.MathUtils;
import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
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

    public EventHandler onEnabled = new EventHandler();
    public EventHandler onDisabled = new EventHandler();
    private static final Zoom MIN_ZOOM = new Zoom(0.5);
    private static final Zoom MAX_ZOOM = new Zoom(5.0);
    private Vector2 contentAlignPoint;
    private Vector2 viewportAlignPoint;
    private ObjectProperty<Zoom> zoomProperty = new SimpleObjectProperty(new Zoom(1));
    private ObjectProperty<Vector2> mousePixelPositionProperty = new SimpleObjectProperty(new Vector2(0, 0));
    private Boolean touchZooming = false;
    private Vector2 touchPoint1;
    private Vector2 touchPoint2;
    private Vector2 mousePressContentPoint;
    private ExtendedScrollPane scrollPane;
    private PlayingSurfaceLayerController playingSurfaceLayerController;
    private Boolean enabled = false;

    public NavigationController(ExtendedScrollPane scrollPane, StackPane sceneViewport, PlayingSurfaceLayerController playingSurfaceLayerController) {
        this.scrollPane = scrollPane;
        this.playingSurfaceLayerController = playingSurfaceLayerController;
        zoomProperty.addListener(this::onZoomPropertyChanged);
        sceneViewport.widthProperty().addListener(this::scrollPaneContentWidthChangedListener);
        sceneViewport.heightProperty().addListener(this::scrollPaneContentHeightChangedListener);
        scrollPane.addEventFilter(ScrollEvent.ANY, this::scrollPaneEventFilter);
        Platform.runLater(() -> {
            autoFit();
        });
    }

    public ReadOnlyObjectProperty<Vector2> mousePixelPositionProperty() {
        return mousePixelPositionProperty;
    }

    public Zoom getMinZoom() {
        return MIN_ZOOM;
    }

    public Zoom getMaxZoom() {
        return MAX_ZOOM;
    }

    public ObjectProperty<Zoom> zoomProperty() {
        return zoomProperty;
    }

    public void onZoomPropertyChanged(ObservableValue<? extends Zoom> value, Zoom oldPropertyValue, Zoom newPropertyValue) {
        if (MathUtils.lessThan(newPropertyValue, getMinZoom())) {
            zoomProperty.set(getMinZoom());
            return;
        } else if (MathUtils.greaterThan(newPropertyValue, getMaxZoom())) {
            zoomProperty.set(getMaxZoom());
            return;
        }
        if (!touchZooming) {
            contentAlignPoint = scrollPane.mouseToSizeRelativeContentPoint();
            viewportAlignPoint = scrollPane.mouseToViewportPoint();
        }
        if (contentAlignPoint == null || viewportAlignPoint == null) {
            contentAlignPoint = scrollPane.contentToSizeRelativePoint(scrollPane.getVisibleContentCenter());
            viewportAlignPoint = scrollPane.getViewportCenter();
        }
        playingSurfaceLayerController.setZoom(zoomProperty.get());
    }

    public void zoomIn() {
        Zoom nextZoom = PREDEFINED_ZOOMS.higher(PREDEFINED_ZOOMS.ceiling(zoomProperty.get()));
        if (nextZoom == null) {
            nextZoom = PREDEFINED_ZOOMS.last();
        }
        zoomProperty.set(nextZoom);
    }

    public void zoomOut() {
        Zoom nextZoom = PREDEFINED_ZOOMS.lower(PREDEFINED_ZOOMS.floor(zoomProperty.get()));
        if (nextZoom == null) {
            nextZoom = PREDEFINED_ZOOMS.first();
        }
        zoomProperty.set(nextZoom);
    }

    public void autoFit() {
        Vector2 baseSceneSize = playingSurfaceLayerController.getBaseSurfaceSize();
        if (scrollPane.getWidth() / scrollPane.getHeight() > baseSceneSize.getX() / baseSceneSize.getY()) {
            zoomProperty.set(new Zoom(scrollPane.getHeight() / baseSceneSize.getY()));
        } else {
            zoomProperty.set(new Zoom(scrollPane.getWidth() / baseSceneSize.getX()));
        }
    }

    public void enable() {
        if (!enabled) {
            ImageCursor imageCursor = FXUtils.chooseBestCursor("/images/cursors/pan-%1$sx%1$s.png", new int[]{32, 48, 96, 128}, 16, 16);
            playingSurfaceLayerController.setCursor(imageCursor);
            enabled = true;
            onEnabled.fire(this);
        }
    }

    public void disable() {
        if (enabled) {
            playingSurfaceLayerController.setCursor(Cursor.DEFAULT);
            enabled = false;
            onDisabled.fire(this);
        }
    }

    public void scrollPaneContentWidthChangedListener(ObservableValue<? extends Number> value, Number oldPropertyValue, Number newPropertyValue) {
        if (contentAlignPoint != null && viewportAlignPoint != null) {
            Vector2 newContentAlignPoint = scrollPane.sizeRelativeToContentPoint(contentAlignPoint);
            scrollPane.alignX(newContentAlignPoint.getX(), viewportAlignPoint.getX());
        }

    }

    public void scrollPaneContentHeightChangedListener(ObservableValue<? extends Number> value, Number oldPropertyValue, Number newPropertyValue) {
        if (contentAlignPoint != null && viewportAlignPoint != null) {
            Vector2 newContentAlignPoint = scrollPane.sizeRelativeToContentPoint(contentAlignPoint);
            scrollPane.alignY(newContentAlignPoint.getY(), viewportAlignPoint.getY());
        }

    }

    private void scrollPaneEventFilter(ScrollEvent scrollEvent) {
        scrollEvent.consume();
        if (!scrollEvent.isDirect()) {
            Double delta = scrollEvent.getDeltaY() / 100;
            zoomProperty.set(new Zoom(zoomProperty.get().getValue() + delta));
        }
    }

    public void onSceneTouchPressed(TouchEvent e) {
        if (enabled && e.getTouchPoints().size() == 2) {
            Vector2 contentPoint1 = scrollPane.sceneToContentPoint(new Vector2(e.getTouchPoints().get(0).getSceneX(), e.getTouchPoints().get(0).getSceneY()));
            Vector2 contentPoint2 = scrollPane.sceneToContentPoint(new Vector2(e.getTouchPoints().get(1).getSceneX(), e.getTouchPoints().get(1).getSceneY()));
            contentAlignPoint = scrollPane.contentToSizeRelativePoint(contentPoint1.average(contentPoint2));
        }
    }

    public void onSceneTouchMoved(TouchEvent e) {
        if (enabled && touchZooming && e.getTouchPoints().size() == 2) {
            touchPoint1 = scrollPane.sceneToViewportPoint(new Vector2(e.getTouchPoints().get(0).getSceneX(), e.getTouchPoints().get(0).getSceneY()));
            touchPoint2 = scrollPane.sceneToViewportPoint(new Vector2(e.getTouchPoints().get(1).getSceneX(), e.getTouchPoints().get(1).getSceneY()));
        }
    }

    public void onSceneZoomStarted(ZoomEvent e) {
        if (enabled) {
            touchZooming = true;
        }
    }

    public void onSceneZoom(ZoomEvent e) {
        if (enabled && touchPoint1 != null && touchPoint2 != null) {
            viewportAlignPoint = touchPoint1.average(touchPoint2);
            zoomProperty.set(new Zoom(zoomProperty.get().getValue() * e.getZoomFactor()));
        }
    }

    public void onSceneZoomFinished(ZoomEvent e) {
        if (enabled) {
            touchZooming = false;
            touchPoint1 = null;
            touchPoint2 = null;
        }
    }

    public void onSceneMouseMoved(MouseEvent e) {
        Vector2 mousePixelPosition = playingSurfaceLayerController.getMousePixelPosition();
        mousePixelPositionProperty.set(mousePixelPosition);
    }

    protected void onSceneMousePressed(MouseEvent e) {
        if (enabled) {
            mousePressContentPoint = scrollPane.mouseToContentPoint();
        }
    }

    protected void onSceneMouseDragged(MouseEvent e) {
        if (enabled && !touchZooming && scrollPane.mouseToViewportPoint() != null && mousePressContentPoint != null) {
            scrollPane.align(mousePressContentPoint, scrollPane.mouseToViewportPoint());
        }
    }

    protected void onSceneMouseReleased(MouseEvent e) {
        if (enabled) {
            mousePressContentPoint = null;
        }
    }
}
