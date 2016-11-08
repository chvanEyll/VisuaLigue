package ca.ulaval.glo2004.visualigue.utils.geometry;

import ca.ulaval.glo2004.visualigue.utils.math.easing.EasingFunction;
import java.awt.Point;
import javafx.geometry.Point2D;

public class Vector2 implements Cloneable {

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

    public Vector2(Vector2 vector2) {
        this.x = vector2.x;
        this.y = vector2.y;
    }

    public static Vector2 fromCenter(Double center, Double size) {
        return new Vector2(center - size / 2, 0);
    }

    public Point2D toPoint2D() {
        return new Point2D(x, y);
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

    public Double getWidth() {
        return this.x;
    }

    public Double getHeight() {
        return this.y;
    }

    public Double magnitude() {
        return Math.sqrt(x * x + y * y);
    }

    public Vector2 interpolate(Vector2 nextVector, Double interpolant) {
        return this.add(nextVector.substract(this).multiply(interpolant));
    }

    public Vector2 interpolate(Vector2 nextVector, Double interpolant, EasingFunction easing) {
        Double interpolatedX = easing.ease(x, nextVector.x, interpolant, 1.0);
        Double interpolatedY = easing.ease(y, nextVector.y, interpolant, 1.0);
        return new Vector2(interpolatedX, interpolatedY);
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

    public Vector2 multiply(Double operand) {
        return new Vector2(this.x * operand, this.y * operand);
    }

    public Vector2 divide(Vector2 operand) {
        return new Vector2(this.x / operand.x, this.y / operand.y);
    }

    public Vector2 divide(Double operand) {
        return new Vector2(this.x / operand, this.y / operand);
    }

    public Boolean lessThanOrEqual(Vector2 operand) {
        return x <= operand.getX() && y <= operand.getY();
    }

    public Vector2 average(Vector2 operand) {
        return new Vector2((x + operand.x) / 2, (y + operand.y) / 2);
    }

    public Vector2 offset(Vector2 offset) {
        return new Vector2(this.x + offset.getX(), this.y + offset.getY());
    }

    public Vector2 negate() {
        return new Vector2(-this.x, -this.y);
    }

    public Vector2 rotateCenter(Vector2 center, Double angle) {
        Vector2 point = this.offset(center.negate());
        point = point.rotateOrigin(angle);
        point = point.offset(center);
        return point;
    }

    public Vector2 rotateOrigin(Double angle) {
        Double angleRadians = Math.toRadians(angle);
        Double newX = x * Math.cos(angleRadians) + y * Math.sin(angleRadians);
        Double newY = -x * Math.sin(angleRadians) + y * Math.cos(angleRadians);
        return new Vector2(newX, newY);
    }

    @Override
    public String toString() {
        return String.format("X: (%s), Y: (%s)", x, y);
    }

    @Override
    public Vector2 clone() {
        return new Vector2(x, y);
    }
}
