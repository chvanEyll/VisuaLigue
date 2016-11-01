package ca.ulaval.glo2004.visualigue.domain.play.keyframe;

import ca.ulaval.glo2004.visualigue.utils.geometry.Vector2;

public class LinearKeyframeTransition extends KeyframeTransition {

    @Override
    public Vector2 interpolate(Vector2 startPosition, Vector2 nextPosition, Double interpolant) {
        return startPosition.interpolate(nextPosition, interpolant);
    }

}
