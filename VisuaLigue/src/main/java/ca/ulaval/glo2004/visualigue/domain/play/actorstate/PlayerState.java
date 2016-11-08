package ca.ulaval.glo2004.visualigue.domain.play.actorstate;

public class PlayerState extends ActorState implements Cloneable {

    private Double orientation;

    public PlayerState() {
        //Required for JAXB instanciation.
    }

    public Double getOrientation() {
        return orientation;
    }

    public static ActorProperty getOrientationProperty() {
        return new ActorProperty("orientation");
    }

    @Override
    public void setPropertyValue(ActorProperty actorProperty, Object value) {
        switch (actorProperty.getPropertyName()) {
            case "orientation":
                this.orientation = (Double) value;
        }
        super.setPropertyValue(actorProperty, value);
    }

}
