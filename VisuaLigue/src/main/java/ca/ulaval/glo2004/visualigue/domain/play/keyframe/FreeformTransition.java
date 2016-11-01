package ca.ulaval.glo2004.visualigue.domain.play.keyframe;

import ca.ulaval.glo2004.visualigue.utils.geometry.Vector2;
import java.util.ArrayList;
import java.util.List;

public class FreeformTransition extends KeyframeTransition {

    List<Vector2> transitionPoints = new ArrayList();

    public FreeformTransition(List<Vector2> transitionPoints) {
        this.transitionPoints = transitionPoints;
    }

    @Override
    public Vector2 interpolate(Vector2 startPosition, Vector2 nextPosition, Double interpolant) {
        Integer index = (int) ((transitionPoints.size() + 1) * interpolant);
        if (index == 0) {
            return startPosition;
        } else {
            return transitionPoints.get(index);
        }
    }

}
