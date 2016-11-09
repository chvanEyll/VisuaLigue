package ca.ulaval.glo2004.visualigue.domain.play.actor;

import ca.ulaval.glo2004.visualigue.domain.DomainObject;
import ca.ulaval.glo2004.visualigue.domain.play.actorstate.ActorState;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class ActorInstance extends DomainObject {

    private Actor actor;
    private Integer instanceNumber;
    private ActorState actorState;

    public ActorInstance(Actor actor, ActorState actorState, Integer instanceNumber) {
        this.actor = actor;
        this.actorState = actorState;
        this.instanceNumber = instanceNumber;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(actor).append(instanceNumber).build();
    }

    public Actor getActor() {
        return actor;
    }

    public ActorState getActorState() {
        return actorState;
    }
}
