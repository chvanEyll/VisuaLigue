package ca.ulaval.glo2004.visualigue.domain.play.actor;

import ca.ulaval.glo2004.visualigue.domain.DomainObject;
import javax.xml.bind.annotation.XmlSeeAlso;

@XmlSeeAlso({BallActor.class, ObstacleActor.class, PlayerActor.class})
public abstract class Actor extends DomainObject {

}