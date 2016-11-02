package ca.ulaval.glo2004.visualigue.domain.play.actorstate.transition;

import ca.ulaval.glo2004.visualigue.domain.DomainObject;
import ca.ulaval.glo2004.visualigue.utils.geometry.Vector2;
import javax.xml.bind.annotation.XmlSeeAlso;

@XmlSeeAlso({FreeformTransition.class, ExponentialEaseOutTransition.class})
public abstract class StateTransition extends DomainObject {

    public abstract Vector2 interpolate(Vector2 startPosition, Vector2 nextPosition, Double interpolant);

    public abstract Double interpolate(Double startPosition, Double nextPosition, Double interpolant);

}
