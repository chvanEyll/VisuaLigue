package ca.ulaval.glo2004.visualigue.utils.geometry;

import java.util.Arrays;
import java.util.List;

public class Triangle {

    private Vector2 baseLeft;
    private Vector2 baseRight;
    private Vector2 tip;

    public Triangle(Double x, Double y, Double width, Double height) {
        baseLeft = new Vector2(x, y - height);
        baseRight = new Vector2(x + width, y - height);
        tip = new Vector2(x + width / 2, y);
    }

    public Triangle(Vector2 baseLeft, Vector2 baseRight, Vector2 tip) {
        this.baseLeft = baseLeft;
        this.baseRight = baseRight;
        this.tip = tip;
    }

    public Triangle offset(Vector2 offset) {
        return new Triangle(baseLeft.offset(offset), baseRight.offset(offset), tip.offset(offset));
    }

    public Vector2 getTip() {
        return tip;
    }

    public List<Vector2> getPoints() {
        return Arrays.asList(baseLeft, baseRight, tip);
    }

}
