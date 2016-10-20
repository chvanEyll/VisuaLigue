package ca.ulaval.glo2004.visualigue.domain.play.actorstate;

import ca.ulaval.glo2004.visualigue.domain.play.position.Position;
import ca.ulaval.glo2004.visualigue.utils.math.easing.EasingFunction;
import java.util.Optional;

public class PlayerState extends ActorState implements Cloneable {

    private Optional<Position> position = Optional.empty();
    private Optional<Double> orientation = Optional.empty();

    private PlayerState() {
    }

    public PlayerState(Optional<Position> position, Optional<Double> orientation) {
        this.position = position;
        this.orientation = orientation;
    }

    @Override
    public PlayerState merge(ActorState actorState) {
        PlayerState oldState = this.clone();
        PlayerState newState = (PlayerState) actorState;
        if (newState.position.isPresent()) {
            position = newState.position;
        }
        if (newState.orientation.isPresent()) {
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
        return !position.isPresent() && !orientation.isPresent();
    }

    @Override
    public PlayerState clone() {
        try {
            super.clone();
            PlayerState clonedState = new PlayerState();
            clonedState.position = position;
            clonedState.orientation = orientation;
            return clonedState;
        } catch (CloneNotSupportedException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public PlayerState interpolate(ActorState nextState, Integer interpolant, EasingFunction easingFunction) {
        PlayerState nextActorState = (PlayerState) nextState;
        PlayerState interpolatedState = this.clone();
        if (nextActorState.position.isPresent()) {
            interpolatedState.position = Optional.of(position.get().interpolate(nextActorState.position.get(), interpolant, easingFunction));
        }
        if (nextActorState.orientation.isPresent()) {
            interpolatedState.orientation = Optional.of(easingFunction.ease(orientation.get(), nextActorState.orientation.get(), interpolant, 1.0));
        }
        return interpolatedState;
    }

}
