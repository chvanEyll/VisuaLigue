package ca.ulaval.glo2004.visualigue.domain.play.keyframe;

import ca.ulaval.glo2004.visualigue.utils.geometry.Vector2;
import ca.ulaval.glo2004.visualigue.utils.math.easing.EasingFunction;

public abstract class KeyframeTransition {

    public abstract Vector2 interpolate(Vector2 startPosition, Vector2 nextPosition, Integer interpolant, EasingFunction easingFunction);

}
