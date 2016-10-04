package ca.ulaval.glo2004.visualigue.utils;

import javafx.scene.Node;

public class FXUtils {

    public static void setDisplay(Node node, Boolean display) {
        node.setVisible(display);
        node.setManaged(display);
    }
}
