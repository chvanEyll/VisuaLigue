package ca.ulaval.glo2004.visualigue.domain.play.frame;

import ca.ulaval.glo2004.visualigue.domain.DomainObject;
import ca.ulaval.glo2004.visualigue.domain.play.actorinstance.ActorInstance;
import ca.ulaval.glo2004.visualigue.domain.play.actorstate.ActorState;
import java.util.HashMap;
import java.util.Map;

public class Frame extends DomainObject {

    Map<ActorInstance, ActorState> currentActorStates = new HashMap();
    Map<ActorInstance, ActorState> nextActorStates = new HashMap();

    public Frame() {
    }

    public void addActorState(ActorInstance actorInstance, ActorState actorState) {
        currentActorStates.put(actorInstance, actorState);
    }

    public Map<ActorInstance, ActorState> getActorStates() {
        return currentActorStates;
    }

}
