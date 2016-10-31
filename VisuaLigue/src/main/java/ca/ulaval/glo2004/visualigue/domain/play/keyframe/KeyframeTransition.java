package ca.ulaval.glo2004.visualigue.domain.play.keyframe;

import ca.ulaval.glo2004.visualigue.domain.DomainObject;
import ca.ulaval.glo2004.visualigue.utils.geometry.Vector2;
import ca.ulaval.glo2004.visualigue.utils.math.easing.EasingFunction;
import javax.xml.bind.annotation.XmlSeeAlso;

@XmlSeeAlso({FreeformTransition.class, LinearKeyframeTransition.class})
public abstract class KeyframeTransition extends DomainObject {

    public abstract Vector2 interpolate(Vector2 startPosition, Vector2 nextPosition, Integer interpolant, EasingFunction easingFunction);

}
