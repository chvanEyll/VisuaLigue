package ca.ulaval.glo2004.visualigue.domain.play.actorstate;

import ca.ulaval.glo2004.visualigue.domain.DomainObject;
import javax.xml.bind.annotation.XmlIDREF;

public class BallState extends ActorState implements Cloneable {

    @XmlIDREF
    private DomainObject ownerPlayer;

    public BallState() {
        zOrder = Integer.MAX_VALUE;
    }

    public DomainObject getOwnerPlayer() {
        return ownerPlayer;
    }

    public Boolean hasOwnerPlayer() {
        return ownerPlayer != null;
    }

    public static ActorProperty getOwnerPlayerProperty() {
        return new ActorProperty("ownerPlayer");
    }

    @Override
    public void setPropertyValue(ActorProperty actorProperty, Object value) {
        switch (actorProperty.getPropertyName()) {
            case "ownerPlayer":
                this.ownerPlayer = (DomainObject) value;
                return;
        }
        super.setPropertyValue(actorProperty, value);
    }

}
