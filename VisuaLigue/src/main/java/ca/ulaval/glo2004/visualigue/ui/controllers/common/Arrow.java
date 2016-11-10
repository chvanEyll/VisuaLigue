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
    private Vector2 headSize = new Vector2(0, 0);
    private Line line = new Line();
    private Double tailGrow = 0.0;
    private Double headGrow = 0.0;

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
        if (!line.getPoint1().equals(tailLocation)) {
            line.setPoint1(tailLocation);
            updateLine();
            updateHead();
        }
    }

    public void setHeadLocation(Vector2 headLocation) {
        if (!line.getPoint2().equals(headLocation)) {
            line.setPoint2(headLocation);
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

    public void setHeadGrow(Double headGrow) {
        if (!this.headGrow.equals(headGrow)) {
            this.headGrow = headGrow;
            updateLine();
        }
    }

    private void updateLine() {
        Double totalHeadGrowLength = -headSize.getHeight() + headGrow;
        Line bodyLine = line.grow(totalHeadGrowLength);
        if (tailGrow != 0) {
            bodyLine = bodyLine.invert().grow(tailGrow).invert();
        }
        if (line.getLength() + totalHeadGrowLength + tailGrow > 0) {
            lineShape.setEndX(bodyLine.getPoint1().getX());
            lineShape.setEndY(bodyLine.getPoint1().getY());
            lineShape.setStartX(bodyLine.getPoint2().getX());
            lineShape.setStartY(bodyLine.getPoint2().getY());
            lineShape.setVisible(true);
        } else {
            lineShape.setVisible(false);
        }
    }

    private void updateHead() {
        Line lineToHead = line.grow(headGrow);
        head.getPoints().clear();
        if (lineShape.isVisible()) {
            Triangle triangle = new Triangle(0.0, 0.0, headSize.getWidth(), headSize.getHeight());
            triangle = triangle.offset(lineToHead.getPoint2().substract(triangle.getTip()));
            List<Vector2> points = triangle.getPoints();
            for (Integer i = 0; i < points.size(); i++) {
                points.set(i, points.get(i).rotateCenter(triangle.getTip(), lineToHead.getAngle() + 90));
                head.getPoints().addAll(new Double[]{points.get(i).getX(), points.get(i).getY()});
            }
        }
        head.setVisible(lineShape.isVisible());
    }

}
