package ca.ulaval.glo2004.visualigue.domain.play.transition;

import ca.ulaval.glo2004.visualigue.domain.play.position.Position;
import ca.ulaval.glo2004.visualigue.utils.math.easing.EasingFunction;

public interface Transition {

    public abstract Position interpolate(Position startPosition, Position nextPosition, Integer interpolant, EasingFunction easingFunction);

}
