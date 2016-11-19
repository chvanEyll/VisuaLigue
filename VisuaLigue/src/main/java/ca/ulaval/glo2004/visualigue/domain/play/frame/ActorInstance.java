package ca.ulaval.glo2004.visualigue.domain.play.frame;

import ca.ulaval.glo2004.visualigue.domain.DomainObject;
import ca.ulaval.glo2004.visualigue.domain.play.actor.Actor;
import ca.ulaval.glo2004.visualigue.domain.play.actorstate.ActorState;
import java.util.Objects;
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

    public Actor getActor() {
        return actor;
    }

    public ActorState getActorState() {
        return actorState;
    }

    public Integer getInstanceID() {
        return instanceID;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(actor).append(instanceID).build();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final DomainObject other = (DomainObject) obj;
        return Objects.equals(this.hashCode(), other.hashCode());
    }
}
