package ca.ulaval.glo2004.visualigue.domain.play.keyframe;

import ca.ulaval.glo2004.visualigue.domain.play.position.Position;
import ca.ulaval.glo2004.visualigue.utils.math.easing.EasingFunction;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)
public class FreeformTransition implements KeyframeTransition {

    List<Position> transitionPoints = new ArrayList();

    public FreeformTransition(List<Position> transitionPoints) {
        this.transitionPoints = transitionPoints;
    }

    @Override
    public Position interpolate(Position startPosition, Position nextPosition, Integer interpolant, EasingFunction easingFunction) {
        Integer index = (transitionPoints.size() + 1) * interpolant;
        if (index == 0) {
            return startPosition;
        } else {
            return transitionPoints.get(index);
        }
    }

}
