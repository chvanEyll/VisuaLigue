package ca.ulaval.glo2004.visualigue.domain.play.actorstate;

import ca.ulaval.glo2004.visualigue.domain.DomainObject;
import ca.ulaval.glo2004.visualigue.utils.geometry.Vector2;
import javax.xml.bind.annotation.XmlSeeAlso;

@XmlSeeAlso({BallState.class, ObstacleState.class, PlayerState.class})
public abstract class ActorState extends DomainObject {

    protected Vector2 position;

    public Vector2 getPosition() {
        return position;
    }

    public static ActorProperty getPositionProperty() {
        return new ActorProperty("position");
    }

    public void setPropertyValue(ActorProperty actorProperty, Object value) {
        switch (actorProperty.getPropertyName()) {
            case "position":
                this.position = (Vector2) value;
        }
    }

}
