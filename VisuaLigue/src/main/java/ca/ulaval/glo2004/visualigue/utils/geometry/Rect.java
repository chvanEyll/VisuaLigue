package ca.ulaval.glo2004.visualigue.utils.geometry;

public class Rect {

    Vector2 location;
    Vector2 size;

    public Rect(Double x, Double y, Double width, Double height) {
        this.location = new Vector2(x, y);
        this.size = new Vector2(width, height);
    }

    public Rect(Vector2 location, Vector2 size) {
        this.location = location;
        this.size = size;
    }

    public static Rect fromCenter(Vector2 center, Vector2 size) {
        Vector2 location = center.substract(size.divide(2.0));
        return new Rect(location, size);
    }

    public Vector2 getCenter() {
        return new Vector2(location.getX() + size.getX() / 2.0, location.getY() + size.getY() / 2.0);
    }

    public Vector2 getLocation() {
        return location;
    }

    public Double getX() {
        return location.getX();
    }

    public Double getY() {
        return location.getY();
    }

    public Double getRight() {
        return location.getX() + size.getWidth();
    }

    public Double getBottom() {
        return location.getY() + size.getHeight();
    }

    public Double getWidth() {
        return size.getWidth();
    }

    public Double getHeight() {
        return size.getHeight();
    }

    public Boolean contains(Vector2 point) {
        return point.getX() >= location.getX() && point.getY() >= location.getY()
                && point.getX() <= location.getX() + size.getX() && point.getY() <= location.getY() + size.getY();
    }

    public Vector2 contain(Vector2 point) {
        Vector2 containedPoint = new Vector2(point);
        if (point.getX() < this.getX()) {
            containedPoint.setX(this.getX());
        }
        if (point.getY() < this.getY()) {
            containedPoint.setY(this.getY());
        }
        if (point.getX() > this.getRight()) {
            containedPoint.setX(this.getRight());
        }
        if (point.getY() > this.getBottom()) {
            containedPoint.setY(this.getBottom());
        }
        return containedPoint;
    }

    @Override
    public String toString() {
        return String.format("Location: (%s), Size: (%s)", location, size);
    }

}
