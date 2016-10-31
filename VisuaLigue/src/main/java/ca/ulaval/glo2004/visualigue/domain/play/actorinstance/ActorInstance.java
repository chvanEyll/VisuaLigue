package ca.ulaval.glo2004.visualigue.domain.play.actorinstance;

import ca.ulaval.glo2004.visualigue.domain.DomainObject;
import javax.xml.bind.annotation.XmlSeeAlso;

@XmlSeeAlso({BallInstance.class, ObstacleInstance.class, PlayerInstance.class})
public abstract class ActorInstance extends DomainObject {

}
