package ca.ulaval.glo2004.visualigue.domain.play.actorstate;

public class ObstacleState extends ActorState implements Cloneable {

    public ObstacleState() {
        zOrder = -5000;
    }

    @Override
    public void setPropertyValue(ActorProperty actorProperty, Object value) {
        super.setPropertyValue(actorProperty, value);
    }

}
