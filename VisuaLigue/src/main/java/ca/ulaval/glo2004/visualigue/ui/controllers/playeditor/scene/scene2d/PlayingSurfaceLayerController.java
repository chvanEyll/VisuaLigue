package ca.ulaval.glo2004.visualigue.ui.controllers.playeditor.scene.scene2d;

import ca.ulaval.glo2004.visualigue.ui.controllers.ControllerBase;
import ca.ulaval.glo2004.visualigue.ui.controllers.playeditor.scene.Zoom;
import ca.ulaval.glo2004.visualigue.ui.models.PlayModel;
import ca.ulaval.glo2004.visualigue.utils.FilenameUtils;
import ca.ulaval.glo2004.visualigue.utils.geometry.Rect;
import ca.ulaval.glo2004.visualigue.utils.geometry.Vector2;
import ca.ulaval.glo2004.visualigue.utils.javafx.FXUtils;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

public class PlayingSurfaceLayerController extends ControllerBase {

    public static final String VIEW_NAME = "/views/playeditor/scene2d/playing-surface-layer.fxml";
    private static final Double ZOOM_WIDTH_BASE = 1000.0;

    @FXML private ImageView rootNode;
    private Image image;
    private PlayModel playModel;
    private Cursor oldCursor;

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

    public Vector2 getMousePosition() {
        return FXUtils.mouseToNodePoint(rootNode);
    }

    public Vector2 getSizeRelativeMousePosition(Boolean contain) {
        Vector2 sizeRelativeMousePosition = surfaceToSizeRelativePoint(getMousePosition());
        if (contain) {
            return new Rect(0.0, 0.0, 1.0, 1.0).contain(sizeRelativeMousePosition);
        } else {
            return sizeRelativeMousePosition;
        }
    }

    public Vector2 getRealWorldMousePosition() {
        Vector2 sizeRelativeMousePosition = getSizeRelativeMousePosition(false);
        return sizeRelativeMousePosition.multiply(new Vector2(playModel.playingSurfaceWidth.get(), playModel.playingSurfaceLength.get()));
    }

    public void setCursor(Cursor cursor) {
        rootNode.setCursor(cursor);
    }

    public Vector2 sizeRelativeToSurfacePoint(Vector2 relativePoint) {
        return relativePoint.multiply(getSurfaceSize());
    }

    public Vector2 surfaceToSizeRelativePoint(Vector2 surfacePoint) {
        return surfacePoint.divide(getSurfaceSize());
    }

}
