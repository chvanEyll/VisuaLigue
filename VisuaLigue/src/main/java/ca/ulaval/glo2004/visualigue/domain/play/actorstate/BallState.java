package ca.ulaval.glo2004.visualigue.domain.play.actorstate;

import ca.ulaval.glo2004.visualigue.domain.play.actor.PlayerActor;
import javax.xml.bind.annotation.XmlIDREF;

public class BallState extends ActorState implements Cloneable {

    @XmlIDREF
    private PlayerActor owner;

    public BallState() {
        //Required for JAXB instanciation.
    }

    public PlayerActor getOwner() {
        return owner;
    }

    public static ActorProperty getOwnerProperty() {
        return new ActorProperty("owner");
    }

    @Override
    public void setPropertyValue(ActorProperty actorProperty, Object value) {
        switch (actorProperty.getPropertyName()) {
            case "owner":
                this.owner = (PlayerActor) value;
        }
        super.setPropertyValue(actorProperty, value);
    }
}
