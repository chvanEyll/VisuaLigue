package ca.ulaval.glo2004.visualigue.domain.play.actorstate;

import ca.ulaval.glo2004.visualigue.domain.play.actorstate.transition.StateTransition;
import ca.ulaval.glo2004.visualigue.utils.geometry.Vector2;

public class PlayerState extends ActorState implements Cloneable {

    private Double orientation;
    private StateTransition orientationTransition;

    public PlayerState() {
        //Required for JAXB instanciation.
    }

    public PlayerState(Vector2 position, StateTransition positionTransition, Double orientation, StateTransition orientationTransition) {
        this.position = position;
        this.positionTransition = positionTransition;
        this.orientation = orientation;
        this.orientationTransition = orientationTransition;
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
        if (newState.orientationTransition != null) {
            orientationTransition = newState.orientationTransition;
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
        clonedState.positionTransition = positionTransition;
        clonedState.orientation = orientation;
        clonedState.orientationTransition = orientationTransition;
        return clonedState;
    }

    @Override
    public PlayerState interpolate(ActorState nextState, Double interpolant) {
        PlayerState nextActorState = (PlayerState) nextState;
        PlayerState interpolatedState = this.clone();
        if (nextActorState.position != null) {
            interpolatedState.position = positionTransition.interpolate(position, nextActorState.position, interpolant);
        }
        if (nextActorState.orientation != null) {
            interpolatedState.orientation = orientationTransition.interpolate(orientation, nextActorState.orientation, interpolant);
        }
        return interpolatedState;
    }

    public Double getOrientation() {
        return orientation;
    }

}
