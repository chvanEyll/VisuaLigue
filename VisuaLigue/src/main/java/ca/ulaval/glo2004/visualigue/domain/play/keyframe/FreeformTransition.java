package ca.ulaval.glo2004.visualigue.domain.play.keyframe;

import ca.ulaval.glo2004.visualigue.utils.geometry.Vector2;
import ca.ulaval.glo2004.visualigue.utils.math.easing.EasingFunction;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)
public class FreeformTransition extends KeyframeTransition {

    List<Vector2> transitionPoints = new ArrayList();

    public FreeformTransition(List<Vector2> transitionPoints) {
        this.transitionPoints = transitionPoints;
    }

    @Override
    public Vector2 interpolate(Vector2 startPosition, Vector2 nextPosition, Integer interpolant, EasingFunction easingFunction) {
        Integer index = (transitionPoints.size() + 1) * interpolant;
        if (index == 0) {
            return startPosition;
        } else {
            return transitionPoints.get(index);
        }
    }

}
