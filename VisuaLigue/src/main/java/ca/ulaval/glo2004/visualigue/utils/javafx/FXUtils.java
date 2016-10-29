package ca.ulaval.glo2004.visualigue.utils.javafx;

import ca.ulaval.glo2004.visualigue.utils.geometry.Vector2;
import java.awt.MouseInfo;
import java.io.File;
import javafx.application.Platform;
import javafx.geometry.Point2D;
import javafx.scene.ImageCursor;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class FXUtils {

    public static void setDisplay(Node node, Boolean display) {
        node.setVisible(display);
        node.setManaged(display);
    }

    public static void requestFocusDelayed(Node node) {
        Platform.runLater(() -> node.requestFocus());
    }

    public static String colorToHex(Color color) {
        return "#" + Integer.toHexString(color.hashCode()).substring(0, 6).toUpperCase();
    }

    public static File chooseImage(Stage stage) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Sélectionner une image");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.bmp", "*.gif", "*.tiff"));
        return fileChooser.showOpenDialog(stage);
    }

    public static Vector2 mouseToNodePoint(Node node) {
        Vector2 location = getMouseLocation();
        Point2D localLocation = node.screenToLocal(location.getX(), location.getY());
        if (localLocation != null) {
            return new Vector2(localLocation);
        } else {
            return null;
        }
    }

    public static Vector2 sceneToNodePoint(Node node, Vector2 scenePoint) {
        return new Vector2(node.sceneToLocal(scenePoint.toPoint2D()));
    }

    public static Vector2 getMouseLocation() {
        return new Vector2(MouseInfo.getPointerInfo().getLocation());
    }

    public static ImageCursor chooseBestCursor(String pathNameFormatString, int[] availableSizes, int hotspotX, int hotspotY) {
        Image[] images = new Image[availableSizes.length];
        for (int i = 0; i < availableSizes.length; i++) {
            images[i] = new Image(FXUtils.class.getResourceAsStream(String.format(pathNameFormatString, availableSizes[i])));
        }
        return ImageCursor.chooseBestCursor(images, hotspotX, hotspotY);
    }

}
