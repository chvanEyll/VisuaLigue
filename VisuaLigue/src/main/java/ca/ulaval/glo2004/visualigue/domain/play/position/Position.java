package ca.ulaval.glo2004.visualigue.domain.play.position;

import ca.ulaval.glo2004.visualigue.domain.DomainObject;
import ca.ulaval.glo2004.visualigue.utils.math.easing.EasingFunction;

public class Position extends DomainObject {

    private Double x;
    private Double y;

    public Position interpolate(Position nextPosition, Integer interpolant, EasingFunction easingFunction) {
        Position interpolatedPosition = new Position();
        interpolatedPosition.x = easingFunction.ease(x, nextPosition.x, interpolant, 1.0);
        interpolatedPosition.y = easingFunction.ease(y, nextPosition.y, interpolant, 1.0);
        return interpolatedPosition;
    }
}
