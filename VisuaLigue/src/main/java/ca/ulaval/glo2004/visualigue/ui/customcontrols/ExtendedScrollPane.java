package ca.ulaval.glo2004.visualigue.ui.customcontrols;

import ca.ulaval.glo2004.visualigue.utils.geometry.Rect;
import ca.ulaval.glo2004.visualigue.utils.geometry.Vector2;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;

public class ExtendedScrollPane extends ScrollPane {

    public Vector2 getContentSize() {
        return new Vector2(getContent().getBoundsInLocal().getWidth(), getContent().getBoundsInLocal().getHeight());
    }

    public Double getContentWidth() {
        return getContent().getBoundsInLocal().getWidth();
    }

    public Double getContentHeight() {
        return getContent().getBoundsInLocal().getHeight();
    }

    public Vector2 getViewportSize() {
        return new Vector2(getViewportBounds().getWidth(), getViewportBounds().getHeight());
    }

    public Double getViewportWidth() {
        return getViewportBounds().getWidth();
    }

    public Double getViewportHeight() {
        return getViewportBounds().getHeight();
    }

    public Vector2 getValue() {
        return new Vector2(this.getHvalue(), this.getVvalue());
    }

    public void setValue(Vector2 value) {
        this.setHvalue(value.getX());
        this.setVvalue(value.getY());
    }

    public Vector2 getVisibleContentCenter() {
        return getVisibleContentBounds().center();
    }

    public void setVisibleContentCenter(Vector2 center) {
        setVisibleContentBounds(Rect.fromCenter(center, getViewportSize()));
    }

    public void setVisibleContentCenterX(Double centerX) {
        setVisibleContentX(centerX - getViewportWidth() / 2);
    }

    public void setVisibleContentCenterY(Double centerY) {
        setVisibleContentY(centerY - getViewportHeight() / 2);
    }

    public Rect getVisibleContentBounds() {
        Vector2 scrollableSize = getScrollableSize();
        return new Rect(scrollableSize.multiply(getValue()), getViewportSize());
    }

    public void setVisibleContentBounds(Rect contentBounds) {
        Vector2 scrollableSize = getScrollableSize();
        Vector2 value = contentBounds.getLocation().divide(scrollableSize);
        setValue(value);
    }

    public void setVisibleContentX(Double minX) {
        Double scrollableWidth = getScrollableX();
        Double value = minX / scrollableWidth;
        if (value > getHmax()) {
            setHvalue(getHmax());
        } else if (value < getHmin()) {
            setHvalue(getHmin());
        } else {
            setHvalue(value);
        }
    }

    public void setVisibleContentY(Double minY) {
        Double scrollableHeight = getScrollableY();
        Double value = minY / scrollableHeight;
        if (value > getVmax()) {
            setVvalue(getVmax());
        } else if (value < getVmin()) {
            setVvalue(getVmin());
        } else {
            setVvalue(value);
        }
    }

    public Vector2 getScrollableSize() {
        return getContentSize().substract(getViewportSize());
    }

    public Double getScrollableX() {
        return getContentWidth() - getViewportWidth();
    }

    public Double getScrollableY() {
        return getContentHeight() - getViewportHeight();
    }

    public void ensureVisible(Node node) {

    }

}
