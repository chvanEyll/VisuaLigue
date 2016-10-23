package ca.ulaval.glo2004.visualigue.domain.play.keyframe;

import ca.ulaval.glo2004.visualigue.domain.play.position.Position;
import ca.ulaval.glo2004.visualigue.utils.math.easing.EasingFunction;

public class LinearKeyframeTransition implements KeyframeTransition {

    @Override
    public Position interpolate(Position startPosition, Position nextPosition, Integer interpolant, EasingFunction easingFunction) {
        return startPosition.interpolate(nextPosition, interpolant, easingFunction);
    }

}
