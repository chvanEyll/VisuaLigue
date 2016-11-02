package ca.ulaval.glo2004.visualigue.ui.controllers.common;

import ca.ulaval.glo2004.visualigue.utils.geometry.Line;
import ca.ulaval.glo2004.visualigue.utils.geometry.Triangle;
import ca.ulaval.glo2004.visualigue.utils.geometry.Vector2;
import java.util.List;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Polygon;

public class Arrow extends Group {

    private javafx.scene.shape.Line lineShape = new javafx.scene.shape.Line();
    private Polygon head = new Polygon();
    private Vector2 tailLocation = new Vector2(0, 0);
    private Vector2 headLocation = new Vector2(0, 0);
    private Vector2 headSize = new Vector2(0, 0);
    private Line line = new Line(tailLocation, headLocation);
    private Double tailGrow = 0.0;

    public Arrow() {
        this.getChildren().add(lineShape);
        this.getChildren().add(head);
    }

    public void setArrowFill(Paint value) {
        head.setFill(value);
    }

    public void setStroke(Paint value) {
        lineShape.setStroke(value);
    }

    public void setStrokeWidth(Double width) {
        lineShape.setStrokeWidth(width);
    }

    public ObservableList<Double> getStrokeDashArray() {
        return lineShape.getStrokeDashArray();
    }

    public void setTailLocation(Vector2 tailLocation) {
        if (this.tailLocation == null || !this.tailLocation.equals(tailLocation)) {
            this.tailLocation = tailLocation;
            updateLine();
            updateHead();
        }
    }

    public void setHeadLocation(Vector2 headLocation) {
        if (this.headLocation == null || !this.headLocation.equals(headLocation)) {
            this.headLocation = headLocation;
            updateLine();
            updateHead();
        }
    }

    public void setHeadSize(Vector2 headSize) {
        if (this.headSize == null || !this.headSize.equals(headSize)) {
            this.headSize = headSize;
            updateHead();
        }
    }

    public void setTailGrow(Double tailGrow) {
        if (!this.tailGrow.equals(tailGrow)) {
            this.tailGrow = tailGrow;
            updateLine();
        }
    }

    private void updateLine() {
        line = new Line(tailLocation, headLocation);
        line = line.grow(-headSize.getHeight());
        if (tailGrow != 0) {
            line = line.invert().grow(tailGrow).invert();
        }
        if (line.getLength() > 0) {
            lineShape.setEndX(line.getPoint1().getX());
            lineShape.setEndY(line.getPoint1().getY());
            lineShape.setStartX(line.getPoint2().getX());
            lineShape.setStartY(line.getPoint2().getY());
            lineShape.setVisible(true);
        } else {
            lineShape.setVisible(false);
        }
    }

    private void updateHead() {
        head.getPoints().clear();
        if (line.getLength() > 0) {
            Triangle triangle = new Triangle(0.0, 0.0, headSize.getWidth(), headSize.getHeight());
            triangle = triangle.offset(headLocation.substract(triangle.getTip()));
            List<Vector2> points = triangle.getPoints();
            Double angle = new Line(this.tailLocation, this.headLocation).getAngle();
            for (Integer i = 0; i < points.size(); i++) {
                points.set(i, points.get(i).rotateCenter(triangle.getTip(), angle + 90));
                head.getPoints().addAll(new Double[]{points.get(i).getX(), points.get(i).getY()});
            }
        }
    }

}
