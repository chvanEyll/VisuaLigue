package ca.ulaval.glo2004.visualigue.utils.geometry;

import ca.ulaval.glo2004.visualigue.utils.math.MathUtils;

public class Line {

    public static final Line RIGHT = new Line(new Vector2(0, 0), new Vector2(1, 0));

    private Vector2 point1;
    private Vector2 point2;

    public Line() {
        this.point1 = new Vector2();
        this.point2 = new Vector2();
    }

    public Line(Vector2 point1, Vector2 point2) {
        this.point1 = point1;
        this.point2 = point2;
    }

    public Double getAngle() {
        return getAngleFromLine(Line.RIGHT);
    }

    public Vector2 getPoint1() {
        return point1;
    }

    public void setPoint1(Vector2 point1) {
        this.point1 = point1;
    }

    public Vector2 getPoint2() {
        return point2;
    }

    public void setPoint2(Vector2 point2) {
        this.point2 = point2;
    }

    public Line offsetToOrigin() {
        Vector2 newPoint1 = point1.offset(point1.negate());
        Vector2 newPoint2 = point2.offset(point1.negate());
        return new Line(newPoint1, newPoint2);
    }

    public Double getAngleFromLine(Line line) {
        Line line1 = this.offsetToOrigin();
        Line line2 = line.offsetToOrigin();
        Double angle1 = Math.atan2(line1.point1.getY() - line1.point2.getY(), line1.point1.getX() - line1.point2.getX());
        Double angle2 = Math.atan2(line2.point1.getY() - line2.point2.getY(), line2.point1.getX() - line2.point2.getX());
        Double angle = Math.toDegrees(Math.abs(angle1 - angle2));
        return MathUtils.wrapAngle(angle);
    }

    public Line grow(Double magnitude) {
        if (magnitude <= 0 && Math.abs(magnitude) >= this.getLength()) {
            return new Line(point1.clone(), point2.clone());
        } else {
            Double angle = Math.toRadians(this.getAngle());
            Double diffX = magnitude * Math.cos(angle);
            Double diffY = -(magnitude * Math.sin(angle));
            return this.grow(new Vector2(diffX, diffY));
        }
    }

    public Line grow(Vector2 size) {
        return new Line(point1.clone(), point2.offset(size));
    }

    public Line invert() {
        return new Line(point2.clone(), point1.clone());
    }

    public Double getLength() {
        return Math.sqrt(Math.pow(point2.getX() - point1.getX(), 2) + Math.pow(point2.getY() - point1.getY(), 2));
    }

}
