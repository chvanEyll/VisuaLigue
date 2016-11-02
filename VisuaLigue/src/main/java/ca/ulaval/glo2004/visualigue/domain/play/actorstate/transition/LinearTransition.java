package ca.ulaval.glo2004.visualigue.domain.play.actorstate.transition;

import ca.ulaval.glo2004.visualigue.utils.geometry.Vector2;
import ca.ulaval.glo2004.visualigue.utils.math.MathUtils;

public class LinearTransition extends StateTransition {

    @Override
    public Vector2 interpolate(Vector2 startPosition, Vector2 nextPosition, Double interpolant) {
        return startPosition.interpolate(nextPosition, interpolant);
    }

    @Override
    public Double interpolate(Double startPosition, Double nextPosition, Double interpolant) {
        return MathUtils.interpolate(startPosition, nextPosition, interpolant);
    }

}
