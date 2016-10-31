package ca.ulaval.glo2004.visualigue.domain.play.actorstate;

import ca.ulaval.glo2004.visualigue.domain.play.keyframe.KeyframeTransition;
import ca.ulaval.glo2004.visualigue.utils.geometry.Vector2;
import ca.ulaval.glo2004.visualigue.utils.math.easing.EasingFunction;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)
public class PlayerState extends ActorState implements Cloneable {

    private KeyframeTransition positionTransition;
    private Double orientation;

    private PlayerState() {
    }

    public PlayerState(Vector2 position, KeyframeTransition positionTransition, Double orientation) {
        this.position = position;
        this.positionTransition = positionTransition;
        this.orientation = orientation;
    }

    @Override
    public PlayerState merge(ActorState actorState) {
        PlayerState oldState = this.clone();
        PlayerState newState = (PlayerState) actorState;
        if (newState.position != null) {
            position = newState.position;
        }
        if (newState.positionTransition != null) {
            positionTransition = newState.positionTransition;
        }
        if (newState.orientation != null) {
            orientation = newState.orientation;
        }
        return oldState;
    }

    @Override
    public void unmerge(ActorState actorState) {
        PlayerState oldState = (PlayerState) actorState;
        position = oldState.position;
        orientation = oldState.orientation;
    }

    @Override
    public Boolean isBlank() {
        return position == null && orientation == null;
    }

    @Override
    public PlayerState clone() {
        PlayerState clonedState = new PlayerState();
        clonedState.position = position;
        clonedState.orientation = orientation;
        return clonedState;
    }

    @Override
    public PlayerState interpolate(ActorState nextState, Integer interpolant, EasingFunction easingFunction) {
        PlayerState nextActorState = (PlayerState) nextState;
        PlayerState interpolatedState = this.clone();
        if (nextActorState.position != null) {
            interpolatedState.position = positionTransition.interpolate(position, nextActorState.position, interpolant, easingFunction);
        }
        if (nextActorState.orientation != null) {
            interpolatedState.orientation = easingFunction.ease(orientation, nextActorState.orientation, interpolant, 1.0);
        }
        return interpolatedState;
    }

    public Double getOrientation() {
        return orientation;
    }

}
