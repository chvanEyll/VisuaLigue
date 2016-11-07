package ca.ulaval.glo2004.visualigue.domain.play.frame;

import ca.ulaval.glo2004.visualigue.domain.DomainObject;
import ca.ulaval.glo2004.visualigue.domain.play.actor.Actor;
import ca.ulaval.glo2004.visualigue.domain.play.actorstate.ActorState;
import java.util.HashMap;
import java.util.Map;

public class Frame extends DomainObject {

    private Integer time;
    private Map<Actor, ActorState> currentActorStates = new HashMap();
    private Map<Actor, ActorState> nextActorStates = new HashMap();

    public Frame(Integer time) {
        this.time = time;
    }

    public Integer getTime() {
        return time;
    }

    public Map<Actor, ActorState> getCurrentActorStates() {
        return currentActorStates;
    }

    public void setCurrentActorState(Actor actor, ActorState actorState) {
        currentActorStates.put(actor, actorState);
    }

    public ActorState getNextActorState(Actor actor) {
        return nextActorStates.get(actor);
    }

    public void setNextActorState(Actor actor, ActorState actorState) {
        nextActorStates.put(actor, actorState);
    }

    public void removeNextActorState(Actor actor) {
        nextActorStates.remove(actor);
    }

}
