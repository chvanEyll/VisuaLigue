package ca.ulaval.glo2004.visualigue.domain.play.actorstate.transition;

import ca.ulaval.glo2004.visualigue.utils.geometry.Vector2;
import ca.ulaval.glo2004.visualigue.utils.math.easing.ExponentialEaseOutFunction;

public class ExponentialEaseOutTransition extends StateTransition {

    private ExponentialEaseOutFunction exponentialEaseOutFunction = new ExponentialEaseOutFunction();

    @Override
    public Vector2 interpolate(Vector2 startPosition, Vector2 nextPosition, Double interpolant) {
        return startPosition.interpolate(nextPosition, interpolant, exponentialEaseOutFunction);
    }

    @Override
    public Double interpolate(Double startPosition, Double nextPosition, Double interpolant) {
        return exponentialEaseOutFunction.ease(startPosition, nextPosition, interpolant, 1.0);
    }

}
