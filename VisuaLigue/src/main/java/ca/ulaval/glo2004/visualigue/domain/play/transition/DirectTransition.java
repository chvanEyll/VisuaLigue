package ca.ulaval.glo2004.visualigue.domain.play.transition;

import ca.ulaval.glo2004.visualigue.domain.play.position.Position;
import ca.ulaval.glo2004.visualigue.utils.math.easing.EasingFunction;

public class DirectTransition implements Transition {

    @Override
    public Position interpolate(Position startPosition, Position nextPosition, Integer interpolant, EasingFunction easingFunction) {
        return startPosition.interpolate(nextPosition, interpolant, easingFunction);
    }

}
