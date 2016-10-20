package ca.ulaval.glo2004.visualigue.domain.play.keyframe;

import ca.ulaval.glo2004.visualigue.domain.DomainObject;
import ca.ulaval.glo2004.visualigue.domain.play.actor.Actor;
import ca.ulaval.glo2004.visualigue.domain.play.actorstate.ActorState;
import java.util.HashMap;
import java.util.Map;

public class Keyframe extends DomainObject {

    private Map<Actor, ActorState> actorStateDelta = new HashMap<>();

    public void mergeActorState(Actor actor, ActorState actorState) {
        actorStateDelta.put(actor, actorState);
    }

    public void removeActorState(Actor actor, ActorState actorState) {
        actorStateDelta.remove(actor);
    }

}
