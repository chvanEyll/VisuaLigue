package ca.ulaval.glo2004.visualigue.domain.play.actor;

import ca.ulaval.glo2004.visualigue.domain.DomainObject;
import ca.ulaval.glo2004.visualigue.domain.play.actorstate.ActorState;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class ActorInstance extends DomainObject {

    private Actor actor;
    private Integer instanceID;
    private ActorState actorState;

    public ActorInstance(Actor actor, ActorState actorState, Integer instanceID) {
        this.actor = actor;
        this.actorState = actorState;
        this.instanceID = instanceID;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(actor).append(instanceID).build();
    }

    public Actor getActor() {
        return actor;
    }

    public ActorState getActorState() {
        return actorState;
    }
}
