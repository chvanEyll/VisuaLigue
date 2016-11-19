package ca.ulaval.glo2004.visualigue.domain.play.frame;

import ca.ulaval.glo2004.visualigue.domain.DomainObject;
import java.util.HashSet;
import java.util.Set;

public class Frame extends DomainObject {

    private Long time;
    private Set<ActorInstance> actorInstances = new HashSet();

    public Frame(Long time) {
        this.time = time;
    }

    public Long getTime() {
        return time;
    }

    public Set<ActorInstance> getActorInstances() {
        return actorInstances;
    }

    public void addActorInstance(ActorInstance actorInstance) {
        actorInstances.add(actorInstance);
    }

}
