package ca.ulaval.glo2004.visualigue.domain.play.actorstate;

import ca.ulaval.glo2004.visualigue.domain.DomainObject;
import ca.ulaval.glo2004.visualigue.domain.play.actorstate.transition.StateTransition;
import ca.ulaval.glo2004.visualigue.utils.geometry.Vector2;
import javax.xml.bind.annotation.XmlSeeAlso;

@XmlSeeAlso({BallState.class, ObstacleState.class, PlayerState.class})
public abstract class ActorState extends DomainObject {

    protected StateTransition positionTransition;
    protected Vector2 position;

    public Vector2 getPosition() {
        return position;
    }

    public StateTransition getPositionTransition() {
        return positionTransition;
    }

    public abstract ActorState merge(ActorState actorState);

    public abstract void unmerge(ActorState actorState);

    public abstract Boolean isBlank();

    public abstract ActorState interpolate(ActorState nextState, Double interpolant);

}
