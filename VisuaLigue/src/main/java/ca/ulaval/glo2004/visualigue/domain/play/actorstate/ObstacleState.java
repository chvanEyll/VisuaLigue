package ca.ulaval.glo2004.visualigue.domain.play.actorstate;

import ca.ulaval.glo2004.visualigue.utils.geometry.Vector2;
import ca.ulaval.glo2004.visualigue.utils.math.easing.EasingFunction;

public class ObstacleState extends ActorState implements Cloneable {

    public ObstacleState() {
        //Required for JAXB instanciation.
    }

    public ObstacleState(Vector2 position) {
        this.position = position;
    }

    @Override
    public ObstacleState merge(ActorState actorState) {
        ObstacleState oldState = this.clone();
        ObstacleState newState = (ObstacleState) actorState;
        if (newState.position != null) {
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
        return position == null;
    }

    @Override
    public ObstacleState clone() {
        ObstacleState clonedState = new ObstacleState();
        clonedState.position = position;
        return clonedState;
    }

    @Override
    public ObstacleState interpolate(ActorState nextState, Integer interpolant, EasingFunction easingFunction) {
        ObstacleState nextObstacleState = (ObstacleState) nextState;
        ObstacleState interpolatedState = this.clone();
        if (nextObstacleState.position != null) {
            interpolatedState.position = position.interpolate(nextObstacleState.position, interpolant, easingFunction);
        }
        return interpolatedState;
    }
}
