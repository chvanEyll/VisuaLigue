package ca.ulaval.glo2004.visualigue.ui.controllers.playeditor.scene.scene2d.layers;

import ca.ulaval.glo2004.visualigue.ui.controllers.ControllerBase;
import ca.ulaval.glo2004.visualigue.ui.controllers.playeditor.scene.Zoom;
import ca.ulaval.glo2004.visualigue.ui.models.PlayModel;
import ca.ulaval.glo2004.visualigue.utils.EventHandler;
import ca.ulaval.glo2004.visualigue.utils.FilenameUtils;
import ca.ulaval.glo2004.visualigue.utils.geometry.Vector2;
import ca.ulaval.glo2004.visualigue.utils.javafx.FXUtils;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;

public class PlayingSurfaceLayerController extends ControllerBase {

    public static final String VIEW_NAME = "/views/playeditor/scene2d/layers/playing-surface-layer.fxml";
    private static final Double ZOOM_WIDTH_BASE = 1000.0;

    public EventHandler<MouseEvent> onMousePressed = new EventHandler();
    public EventHandler<MouseEvent> onMouseDragged = new EventHandler();
    public EventHandler<MouseEvent> onMouseReleased = new EventHandler();
    public EventHandler<MouseEvent> onMouseMoved = new EventHandler();
    public EventHandler<MouseEvent> onMouseClicked = new EventHandler();
    @FXML private ImageView rootNode;
    private Image image;
    private PlayModel playModel;

    public void init(PlayModel playModel, StackPane stackPane) {
        this.playModel = playModel;
        initSizeBindings(stackPane);
        initImage();
    }

    private void initSizeBindings(StackPane stackPane) {
        stackPane.minWidthProperty().bind(rootNode.fitWidthProperty());
        stackPane.maxWidthProperty().bind(rootNode.fitWidthProperty());
        stackPane.minHeightProperty().bind(rootNode.fitHeightProperty());
        stackPane.maxHeightProperty().bind(rootNode.fitHeightProperty());
    }

    private void initImage() {
        if (playModel.customPlayingSurfaceImagePathName.isNotEmpty().get()) {
            image = new Image(FilenameUtils.getURIString(playModel.customPlayingSurfaceImagePathName.get()));
        } else if (playModel.builtInPlayingSurfaceImagePathName.isNotEmpty().get()) {
            image = new Image(playModel.builtInPlayingSurfaceImagePathName.get());
        }
        rootNode.setImage(image);
    }

    @FXML
    protected void onMousePressed(MouseEvent e) {
        onMousePressed.fire(this, e);
    }

    @FXML
    protected void onMouseDragged(MouseEvent e) {
        onMouseDragged.fire(this, e);
    }

    @FXML
    protected void onMouseReleased(MouseEvent e) {
        onMouseReleased.fire(this, e);
    }

    @FXML
    protected void onMouseMoved(MouseEvent e) {
        onMouseMoved.fire(this, e);
    }

    @FXML
    protected void onMouseClicked(MouseEvent e) {
        onMouseClicked.fire(this, e);
    }

    public void setFitSize(Vector2 fitSize) {
        rootNode.setFitWidth(getSurfaceSize().getX());
        rootNode.setFitHeight(getSurfaceSize().getY());
    }

    public void setZoom(Zoom zoom) {
        rootNode.setFitWidth(getBaseSurfaceSize().getX() * zoom.getValue());
        rootNode.setFitHeight(getBaseSurfaceSize().getY() * zoom.getValue());
    }

    public Vector2 getBaseSurfaceSize() {
        return new Vector2(ZOOM_WIDTH_BASE, ZOOM_WIDTH_BASE / (image.getWidth() / image.getHeight()));
    }

    public Vector2 getSurfaceSize() {
        return new Vector2(rootNode.getFitWidth(), rootNode.getFitHeight());
    }

    public Vector2 getRelativeMousePosition() {
        Vector2 mousePosition = FXUtils.mouseToNodePoint(rootNode);
        return mousePosition.divide(getSurfaceSize());
    }

    public Vector2 getRealWorldMousePosition() {
        Vector2 relativeMousePosition = getRelativeMousePosition();
        Vector2 surfacePosition = relativeMousePosition.multiply(new Vector2(playModel.playingSurfaceWidth.get(), playModel.playingSurfaceLength.get()));
        return surfacePosition;
    }

    public void setCursor(Cursor cursor) {
        rootNode.setCursor(cursor);
    }

    public Vector2 relativeToSurfacePoint(Vector2 relativePoint) {
        return relativePoint.multiply(getSurfaceSize());
    }

}
