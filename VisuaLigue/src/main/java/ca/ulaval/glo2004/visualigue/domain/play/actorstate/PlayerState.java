package ca.ulaval.glo2004.visualigue.domain.play.actorstate;

import ca.ulaval.glo2004.visualigue.domain.play.actor.BallActor;
import javax.xml.bind.annotation.XmlIDREF;

public class PlayerState extends ActorState implements Cloneable {

    private Double orientation;
    @XmlIDREF
    private BallActor snappedBall;

    public PlayerState() {
        //Required for JAXB instanciation.
    }

    public Double getOrientation() {
        return orientation;
    }

    public static ActorProperty getOrientationProperty() {
        return new ActorProperty("orientation");
    }

    public BallActor getSnappedBall() {
        return snappedBall;
    }

    public Boolean hasSnappedBall() {
        return snappedBall != null;
    }

    public static ActorProperty getSnappedBallProperty() {
        return new ActorProperty("snappedBall");
    }

    @Override
    public void setPropertyValue(ActorProperty actorProperty, Object value) {
        switch (actorProperty.getPropertyName()) {
            case "orientation":
                this.orientation = (Double) value;
                return;
            case "snappedBall":
                this.snappedBall = (BallActor) value;
                return;
        }
        super.setPropertyValue(actorProperty, value);
    }

}
