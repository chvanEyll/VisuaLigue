package ca.ulaval.glo2004.visualigue.domain.play.keyframe;

import ca.ulaval.glo2004.visualigue.domain.DomainObject;
import ca.ulaval.glo2004.visualigue.domain.play.keyframe.transition.KeyframeTransition;
import ca.ulaval.glo2004.visualigue.utils.geometry.Vector2;

public class Keyframe extends DomainObject {

    private Object value;
    private KeyframeTransition transition;

    public Keyframe() {
        //Required for JAXB instanciation.
    }

    public Keyframe(Object value, KeyframeTransition transition) {
        this.value = value;
        this.transition = transition;
    }

    public Object getValue() {
        return value;
    }

    public Object interpolate(Double interpolant, Keyframe nextKeyframe) {
        Object interpolatedValue;
        if (value instanceof Double) {
            interpolatedValue = transition.interpolate((Double) value, (Double) nextKeyframe.value, interpolant);
        } else if (value instanceof Vector2) {
            interpolatedValue = transition.interpolate((Vector2) value, (Vector2) nextKeyframe.value, interpolant);
        } else {
            interpolatedValue = value;
        }
        return interpolatedValue;
    }

}
