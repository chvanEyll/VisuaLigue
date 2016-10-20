package ca.ulaval.glo2004.visualigue.domain.play.actorstate;

import ca.ulaval.glo2004.visualigue.domain.play.position.Position;
import ca.ulaval.glo2004.visualigue.utils.math.easing.EasingFunction;
import java.util.Optional;

public class ObstacleState extends ActorState implements Cloneable {

    private Optional<Position> position = Optional.empty();

    private ObstacleState() {

    }

    public ObstacleState(Optional<Position> position) {
        this.position = position;
    }

    @Override
    public ObstacleState merge(ActorState actorState) {
        ObstacleState oldState = this.clone();
        ObstacleState newState = (ObstacleState) actorState;
        if (newState.position.isPresent()) {
            position = newState.position;
        }
        return oldState;
    }

    @Override
    public void unmerge(ActorState actorState) {
        ObstacleState oldState = (ObstacleState) actorState;
        position = oldState.position;
    }

    @Override
    public Boolean isBlank() {
        return !position.isPresent();
    }

    @Override
    public ObstacleState clone() {
        try {
            super.clone();
            ObstacleState clonedState = new ObstacleState();
            clonedState.position = position;
            return clonedState;
        } catch (CloneNotSupportedException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public ObstacleState interpolate(ActorState nextState, Integer interpolant, EasingFunction easingFunction) {
        ObstacleState nextObstacleState = (ObstacleState) nextState;
        ObstacleState interpolatedState = this.clone();
        if (nextObstacleState.position.isPresent()) {
            interpolatedState.position = Optional.of(position.get().interpolate(nextObstacleState.position.get(), interpolant, easingFunction));
        }
        return interpolatedState;
    }
}
