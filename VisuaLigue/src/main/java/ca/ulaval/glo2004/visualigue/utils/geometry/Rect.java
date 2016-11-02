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

    public Vector2 center() {
        return new Vector2(location.getX() + size.getX() / 2.0, location.getY() + size.getY() / 2.0);
    }

    public Vector2 getLocation() {
        return location;
    }

    public Boolean contains(Vector2 point) {
        return point.getX() >= location.getX() && point.getY() >= location.getY()
                && point.getX() <= location.getX() + size.getX() && point.getY() <= location.getY() + size.getY();
    }

    @Override
    public String toString() {
        return String.format("Location: (%s), Size: (%s)", location, size);
    }

}
