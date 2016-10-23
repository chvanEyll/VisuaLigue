package ca.ulaval.glo2004.visualigue.ui.customcontrols;

import javafx.scene.Node;
import javafx.scene.control.ScrollPane;

public class ExtendedScrollPane extends ScrollPane {

    public void ensureVisible(Node node) {
        double fullWidth = getContent().getBoundsInLocal().getWidth();
        double fullHeight = getContent().getBoundsInLocal().getHeight();
        int minVisibleX = -1 * (int) getViewportBounds().getMinX() + 1;
        int maxVisibleX = -1 * (int) getViewportBounds().getMinX() + (int) getViewportBounds().getMaxX();
        int minVisibleY = -1 * (int) getViewportBounds().getMinY() + 1;
        int maxVisibleY = -1 * (int) getViewportBounds().getMinY() + (int) getViewportBounds().getMaxY();
        if (node.getBoundsInParent().getMinX() < minVisibleX) {
            setHvalue(node.getBoundsInParent().getMinX() / fullWidth);
        } else if (node.getBoundsInParent().getMaxX() > maxVisibleX) {
            setHvalue(node.getBoundsInParent().getMaxX() / fullWidth);
        }
        if (node.getBoundsInParent().getMinY() < minVisibleY) {
            setHvalue(node.getBoundsInParent().getMinY() / fullHeight);
        } else if (node.getBoundsInParent().getMaxY() > maxVisibleY) {
            setHvalue(node.getBoundsInParent().getMaxY() / fullHeight);
        }
    }

}
