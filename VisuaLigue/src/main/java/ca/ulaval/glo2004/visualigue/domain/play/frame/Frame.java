package ca.ulaval.glo2004.visualigue.domain.play.frame;

import ca.ulaval.glo2004.visualigue.domain.DomainObject;
import ca.ulaval.glo2004.visualigue.domain.play.actor.Actor;
import ca.ulaval.glo2004.visualigue.domain.play.actorstate.ActorState;
import java.util.HashMap;
import java.util.Map;

public class Frame extends DomainObject {

    private Long time;
    private Boolean isLocked = false;
    private Double opacity = 1.0;
    private Map<Actor, ActorState> currentActorStates = new HashMap();
    private Map<Actor, ActorState> nextActorStates = new HashMap();

    public Frame(Long time) {
        this.time = time;
    }

    public Long getTime() {
        return time;
    }

    public Boolean isLocked() {
        return isLocked;
    }

    public void setIsLocked(Boolean isLocked) {
        this.isLocked = isLocked;
    }

    public Double getOpacity() {
        return opacity;
    }

    public void setOpacity(Double opacity) {
        this.opacity = opacity;
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
