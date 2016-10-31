package ca.ulaval.glo2004.visualigue.ui.controllers.playeditor.scene.scene2d;

import ca.ulaval.glo2004.visualigue.ui.controllers.ControllerBase;
import ca.ulaval.glo2004.visualigue.ui.controllers.playeditor.scene.Zoom;
import ca.ulaval.glo2004.visualigue.ui.models.PlayModel;
import ca.ulaval.glo2004.visualigue.utils.EventHandler;
import ca.ulaval.glo2004.visualigue.utils.FilenameUtils;
import ca.ulaval.glo2004.visualigue.utils.geometry.Vector2;
import ca.ulaval.glo2004.visualigue.utils.javafx.FXUtils;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class PlayingSurfaceController extends ControllerBase {

    private static final Double ZOOM_WIDTH_BASE = 1000.0;

    public EventHandler<MouseEvent> onMousePressed = new EventHandler();
    public EventHandler<MouseEvent> onMouseDragged = new EventHandler();
    public EventHandler<MouseEvent> onMouseReleased = new EventHandler();
    private Image image;
    private PlayModel playModel;
    @FXML private ImageView rootNode;

    public void init(PlayModel playModel) {
        this.playModel = playModel;
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
        Vector2 mousePosition = FXUtils.mouseToNodePoint(rootNode);
        Vector2 relativeMousePosition = mousePosition.divide(getSurfaceSize());
        Vector2 surfacePosition = relativeMousePosition.multiply(new Vector2(playModel.playingSurfaceWidth.get(), playModel.playingSurfaceLength.get()));
        return surfacePosition;
    }

}
