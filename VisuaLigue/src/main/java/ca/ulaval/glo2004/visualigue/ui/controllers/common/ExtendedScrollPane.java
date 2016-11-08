package ca.ulaval.glo2004.visualigue.ui.controllers.common;

import ca.ulaval.glo2004.visualigue.utils.geometry.Rect;
import ca.ulaval.glo2004.visualigue.utils.geometry.Vector2;
import ca.ulaval.glo2004.visualigue.utils.javafx.FXUtils;
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

    public Vector2 getViewportCenter() {
        return getViewportSize().divide(2.0);
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

    public Rect getViewportRect() {
        return new Rect(new Vector2(), getViewportSize());
    }

    public Vector2 getVisibleContentCenter() {
        return getVisibleContentBounds().getCenter();
    }

    public Rect getVisibleContentBounds() {
        Vector2 scrollableSize = getScrollableSize();
        return new Rect(scrollableSize.multiply(getValue()), getViewportSize());
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

    public Vector2 getValue() {
        return new Vector2(this.getHvalue(), this.getVvalue());
    }

    public void setValueSafe(Vector2 value) {
        this.setHvalueSafe(value.getX());
        this.setVvalueSafe(value.getY());
    }

    public void setHvalueSafe(Double value) {
        if (value > getHmax()) {
            setHvalue(getHmax());
        } else if (value < getHmin()) {
            setHvalue(getHmin());
        } else if (!value.isNaN()) {
            setHvalue(value);
        }
    }

    public void setVvalueSafe(Double value) {
        if (value > getVmax()) {
            setVvalue(getVmax());
        } else if (value < getVmin()) {
            setVvalue(getVmin());
        } else if (!value.isNaN()) {
            setVvalue(value);
        }
    }

    public void scrollContent(Vector2 delta) {
        Vector2 scrollSize = getScrollableSize();
        setValueSafe(getValue().add(delta.divide(scrollSize)));
    }

    public void alignToViewportLeft(Double contentX) {
        setHvalueSafe(contentX / getScrollableX());
    }

    public void alignToViewportTop(Double contentY) {
        setVvalueSafe(contentY / getScrollableY());
    }

    public void alignToViewportCenterX(Double contentX) {
        alignToViewportLeft(contentX - getViewportWidth() / 2);
    }

    public void alignToViewportCenterY(Double contentY) {
        alignToViewportTop(contentY - getViewportHeight() / 2);
    }

    public void align(Vector2 contentPoint, Vector2 viewPortPoint) {
        alignX(contentPoint.getX(), viewPortPoint.getX());
        alignY(contentPoint.getY(), viewPortPoint.getY());
    }

    public void alignX(Double contentX, Double viewPortX) {
        alignToViewportLeft(contentX - viewPortX);
    }

    public void alignY(Double contentY, Double viewPortY) {
        alignToViewportTop(contentY - viewPortY);
    }

    public Vector2 mouseToViewportPoint() {
        Vector2 viewportPoint = FXUtils.mouseToNodePoint(this);
        if (viewportPoint != null && getViewportRect().contains(viewportPoint)) {
            return viewportPoint;
        } else {
            return null;
        }
    }

    public Vector2 sceneToViewportPoint(Vector2 scenePoint) {
        return FXUtils.sceneToNodePoint(this, scenePoint);
    }

    public Vector2 sceneToContentPoint(Vector2 scenePoint) {
        return FXUtils.sceneToNodePoint(getContent(), scenePoint);
    }

    public Vector2 mouseToContentPoint() {
        return FXUtils.mouseToNodePoint(getContent());
    }

    public Vector2 mouseToSizeRelativeContentPoint() {
        Vector2 contentPoint = mouseToContentPoint();
        if (contentPoint != null) {
            return contentToSizeRelativePoint(mouseToContentPoint());
        } else {
            return null;
        }
    }

    public Vector2 contentToSizeRelativePoint(Vector2 contentPoint) {
        return contentPoint.divide(getContentSize());
    }

    public Double sizeRelativeToContentX(Double x) {
        return x * getContentWidth();
    }

    public Double sizeRelativeToContentY(Double y) {
        return y * getContentHeight();
    }

    public Vector2 sizeRelativeToContentPoint(Vector2 sizeRelativePoint) {
        return sizeRelativePoint.multiply(getContentSize());
    }

    public void ensureVisible(Node node) {

    }

}
