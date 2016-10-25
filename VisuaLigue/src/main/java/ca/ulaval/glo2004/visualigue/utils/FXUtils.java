package ca.ulaval.glo2004.visualigue.utils;

import ca.ulaval.glo2004.visualigue.utils.geometry.Vector2;
import java.awt.MouseInfo;
import java.awt.Point;
import java.io.File;
import javafx.application.Platform;
import javafx.scene.Node;
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

    public static Vector2 getNodeMousePosition(Node node) {
        Point location = MouseInfo.getPointerInfo().getLocation();
        return new Vector2(node.screenToLocal(location.x, location.y));
    }

}
