package ca.ulaval.glo2004.visualigue.utils;

import java.util.ArrayList;
import java.util.List;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;

public class FXUtils {

    public static void setDisplay(Node node, Boolean display) {
        node.setVisible(display);
        node.setManaged(display);
    }

    public static void requestFocusDelayed(Node node) {
        Platform.runLater(() -> node.requestFocus());
    }

    public static void insertGridPaneRow(GridPane gridPane, Integer rowIndex, List<Node> children) {
        gridPane.getChildren().forEach(child -> {
            Integer currentRowIndex = GridPane.getRowIndex(child);
            if (currentRowIndex != null && currentRowIndex >= rowIndex) {
                GridPane.setRowIndex(child, currentRowIndex + 1);
            }
        });
        children.forEach(child -> {
            GridPane.setRowIndex(child, rowIndex);
        });
    }

    public static void removeGridPaneRow(GridPane gridPane, Integer rowIndex) {
        removeChildrenFromRow(gridPane, rowIndex);
        gridPane.getChildren().forEach(child -> {
            Integer currentRowIndex = GridPane.getRowIndex(child);
            if (currentRowIndex != null && currentRowIndex > rowIndex) {
                GridPane.setRowIndex(child, currentRowIndex - 1);
            }
        });
    }

    public static void removeChildrenFromRow(GridPane gridPane, Integer rowIndex) {
        List<Node> childrenToRemove = new ArrayList<>();
        gridPane.getChildren().forEach(child -> {
            Integer currentRowIndex = GridPane.getRowIndex(child);
            if (currentRowIndex != null && currentRowIndex == rowIndex) {
                childrenToRemove.add(child);
            }
        });
        childrenToRemove.forEach(child -> {
            gridPane.getChildren().remove(child);
        });
    }

    public static void addStyleClass(List<Node> nodes, String styleClass) {
        nodes.forEach(node -> {
            node.getStyleClass().add(styleClass);
        });
    }

    public static void removeStyleClass(List<Node> nodes, String styleClass) {
        nodes.forEach(node -> {
            node.getStyleClass().remove(styleClass);
        });
    }

}
