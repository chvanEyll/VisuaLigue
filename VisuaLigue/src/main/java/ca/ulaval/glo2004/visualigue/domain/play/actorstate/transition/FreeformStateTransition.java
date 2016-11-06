package ca.ulaval.glo2004.visualigue.domain.play.actorstate.transition;

import ca.ulaval.glo2004.visualigue.utils.geometry.Vector2;
import java.util.ArrayList;
import java.util.List;

public class FreeformStateTransition extends StateTransition {

    private List<Vector2> transitionPoints = new ArrayList();

    public FreeformStateTransition(List<Vector2> transitionPoints) {
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

    @Override
    public Double interpolate(Double startPosition, Double nextPosition, Double interpolant) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
