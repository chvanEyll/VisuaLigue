package ca.ulaval.glo2004.visualigue.domain.play.frame;

import ca.ulaval.glo2004.visualigue.domain.DomainObject;
import ca.ulaval.glo2004.visualigue.domain.play.actor.ActorInstance;
import ca.ulaval.glo2004.visualigue.domain.play.actorstate.ActorState;
import java.util.HashMap;
import java.util.Map;

public class Frame extends DomainObject {

    Map<ActorInstance, ActorState> actorStates = new HashMap<>();

    public Frame() {
    }

    public void addActorState(ActorInstance actorInstance, ActorState actorState) {
        actorStates.put(actorInstance, actorState);
    }

}
