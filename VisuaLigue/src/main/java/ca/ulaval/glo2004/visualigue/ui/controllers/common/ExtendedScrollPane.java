package ca.ulaval.glo2004.visualigue.ui.controllers.common;

import ca.ulaval.glo2004.visualigue.utils.geometry.Rect;
import ca.ulaval.glo2004.visualigue.utils.geometry.Vector2;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Region;

public class ExtendedScrollPane extends ScrollPane {

    public Vector2 getContentSize() {
        return new Vector2(getContentWidth(), getContentHeight());
    }

    public Double getContentWidth() {
        return ((Region) getContent()).getWidth();
    }

    public Double getContentHeight() {
        return ((Region) getContent()).getHeight();
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
        } else if (!value.isNaN()) {
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
        } else if (!value.isNaN()) {
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
