package ca.ulaval.glo2004.visualigue.domain.play.frame;

import ca.ulaval.glo2004.visualigue.domain.DomainObject;
import ca.ulaval.glo2004.visualigue.domain.play.actorinstance.ActorInstance;
import ca.ulaval.glo2004.visualigue.domain.play.actorstate.ActorState;
import java.util.HashMap;
import java.util.Map;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "frame")
@XmlAccessorType(XmlAccessType.FIELD)
public class Frame extends DomainObject {

    Map<ActorInstance, ActorState> actorStates = new HashMap();

    public Frame() {
    }

    public void addActorState(ActorInstance actorInstance, ActorState actorState) {
        actorStates.put(actorInstance, actorState);
    }

    public Map<ActorInstance, ActorState> getActorStates() {
        return actorStates;
    }

}
