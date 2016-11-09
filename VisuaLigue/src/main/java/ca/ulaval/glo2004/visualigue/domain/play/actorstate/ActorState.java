package ca.ulaval.glo2004.visualigue.domain.play.actorstate;

import ca.ulaval.glo2004.visualigue.domain.DomainObject;
import ca.ulaval.glo2004.visualigue.utils.geometry.Vector2;
import javax.xml.bind.annotation.XmlSeeAlso;

@XmlSeeAlso({BallState.class, ObstacleState.class, PlayerState.class})
public abstract class ActorState extends DomainObject {

    protected Vector2 position;
    protected Vector2 nextPosition;
    protected Boolean isLocked = false;
    protected Double opacity = 1.0;
    protected Integer zOrder = 0;
    protected Boolean showLabel = true;

    public Vector2 getPosition() {
        return position;
    }

    public static ActorProperty getPositionProperty() {
        return new ActorProperty("position");
    }

    public Vector2 getNextPosition() {
        return nextPosition;
    }

    public void setNextPosition(Vector2 nextPosition) {
        this.nextPosition = nextPosition;
    }

    public Boolean isLocked() {
        return isLocked;
    }

    public void setIsLocked(Boolean isLocked) {
        this.isLocked = isLocked;
    }

    public Double getOpacity() {
        return opacity;
    }

    public void setOpacity(Double opacity) {
        this.opacity = opacity;
    }

    public Integer getZOrder() {
        return zOrder;
    }

    public void setZOrder(Integer zOrder) {
        this.zOrder = zOrder;
    }

    public Boolean getShowLabel() {
        return showLabel;
    }

    public void setShowLabel(Boolean showLabel) {
        this.showLabel = showLabel;
    }

    public void setPropertyValue(ActorProperty actorProperty, Object value) {
        switch (actorProperty.getPropertyName()) {
            case "position":
                this.position = (Vector2) value;
        }
    }

}
