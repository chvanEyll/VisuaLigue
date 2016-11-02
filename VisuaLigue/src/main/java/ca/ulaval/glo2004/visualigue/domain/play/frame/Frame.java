package ca.ulaval.glo2004.visualigue.domain.play.frame;

import ca.ulaval.glo2004.visualigue.domain.DomainObject;
import ca.ulaval.glo2004.visualigue.domain.play.actorinstance.ActorInstance;
import ca.ulaval.glo2004.visualigue.domain.play.actorstate.ActorState;
import java.util.HashMap;
import java.util.Map;

public class Frame extends DomainObject {

    private Map<ActorInstance, ActorState> currentActorStates = new HashMap();
    private Map<ActorInstance, ActorState> nextActorStates = new HashMap();

    public Frame() {
    }

    public Map<ActorInstance, ActorState> getCurrentActorStates() {
        return currentActorStates;
    }

    public void setCurrentActorState(ActorInstance actorInstance, ActorState actorState) {
        currentActorStates.put(actorInstance, actorState);
    }

    public ActorState getNextActorState(ActorInstance actorInstance) {
        return nextActorStates.get(actorInstance);
    }

    public void setNextActorState(ActorInstance actorInstance, ActorState actorState) {
        nextActorStates.put(actorInstance, actorState);
    }

    public void removeNextActorState(ActorInstance actorInstance) {
        nextActorStates.remove(actorInstance);
    }

}
