package ca.ulaval.glo2004.visualigue.utils.geometry;

import ca.ulaval.glo2004.visualigue.domain.DomainObject;
import ca.ulaval.glo2004.visualigue.utils.math.easing.EasingFunction;
import java.awt.Point;
import javafx.geometry.Point2D;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)
public class Vector2 extends DomainObject {

    private Double x;
    private Double y;

    public Vector2() {
        this.x = 0.0;
        this.y = 0.0;
    }

    public Vector2(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Vector2(Point point) {
        this.x = (double) point.x;
        this.y = (double) point.y;
    }

    public Vector2(Point2D point2d) {
        this.x = point2d.getX();
        this.y = point2d.getY();
    }

    public static Vector2 fromCenter(Double center, Double size) {
        return new Vector2(center - size / 2, 0);
    }

    public Double getX() {
        return x;
    }

    public void setX(Double x) {
        this.x = x;
    }

    public Double getY() {
        return y;
    }

    public void setY(Double y) {
        this.y = y;
    }

    public Vector2 interpolate(Vector2 nextPosition, Integer interpolant, EasingFunction easingFunction) {
        Vector2 interpolatedPosition = new Vector2();
        interpolatedPosition.x = easingFunction.ease(x, nextPosition.x, interpolant, 1.0);
        interpolatedPosition.y = easingFunction.ease(y, nextPosition.y, interpolant, 1.0);
        return interpolatedPosition;
    }

    public Vector2 add(Vector2 operand) {
        return new Vector2(this.x + operand.x, this.y + operand.y);
    }

    public Vector2 substract(Vector2 operand) {
        return new Vector2(this.x - operand.x, this.y - operand.y);
    }

    public Vector2 multiply(Vector2 operand) {
        return new Vector2(this.x * operand.x, this.y * operand.y);
    }

    public Vector2 divide(Vector2 operand) {
        return new Vector2(this.x / operand.x, this.y / operand.y);
    }

    public Vector2 divide(Double operand) {
        return new Vector2(this.x / operand, this.y / operand);
    }

    @Override
    public String toString() {
        return String.format("X: (%s), Y: (%s)", x, y);
    }
}
