package ca.ulaval.glo2004.visualigue.domain.play.keyframe;

import ca.ulaval.glo2004.visualigue.domain.DomainObject;
import ca.ulaval.glo2004.visualigue.domain.play.actor.Actor;
import ca.ulaval.glo2004.visualigue.domain.play.actorstate.ActorState;
import java.util.HashMap;
import java.util.Map;

public class Keyframe extends DomainObject {

    private Map<Actor, ActorState> actorStateDelta = new HashMap<>();

    public ActorState mergeActorState(Actor actor, ActorState actorState) {
        if (actorStateDelta.containsKey(actor)) {
            ActorState currentActorState = actorStateDelta.get(actor);
            return currentActorState.merge(actorState);
        } else {
            actorStateDelta.put(actor, actorState);
            return null;
        }
    }

    public void unmergeActorState(Actor actor, ActorState actorState) {
        ActorState currentActorState = actorStateDelta.get(actor);
        currentActorState.unmerge(actorState);
        if (actorState.isBlank()) {
            actorStateDelta.remove(actor);
        }
    }

    public Boolean isEmpty() {
        return actorStateDelta.isEmpty();
    }

}
