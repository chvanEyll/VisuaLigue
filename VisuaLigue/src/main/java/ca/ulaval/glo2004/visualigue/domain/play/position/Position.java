package ca.ulaval.glo2004.visualigue.domain.play.position;

import ca.ulaval.glo2004.visualigue.domain.DomainObject;
import ca.ulaval.glo2004.visualigue.utils.math.easing.EasingFunction;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)
public class Position extends DomainObject {

    private Double x;
    private Double y;

    public Position interpolate(Position nextPosition, Integer interpolant, EasingFunction easingFunction) {
        Position interpolatedPosition = new Position();
        interpolatedPosition.x = easingFunction.ease(x, nextPosition.x, interpolant, 1.0);
        interpolatedPosition.y = easingFunction.ease(y, nextPosition.y, interpolant, 1.0);
        return interpolatedPosition;
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

}
