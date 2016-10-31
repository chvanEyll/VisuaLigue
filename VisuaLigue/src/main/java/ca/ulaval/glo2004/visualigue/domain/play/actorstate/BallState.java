package ca.ulaval.glo2004.visualigue.domain.play.actorstate;

import ca.ulaval.glo2004.visualigue.domain.play.actorinstance.PlayerInstance;
import ca.ulaval.glo2004.visualigue.utils.geometry.Vector2;
import ca.ulaval.glo2004.visualigue.utils.math.easing.EasingFunction;

public class BallState extends ActorState implements Cloneable {

    private PlayerInstance owner;

    private BallState() {

    }

    public BallState(Vector2 position, PlayerInstance owner) {
        this.position = position;
        this.owner = owner;
    }

    @Override
    public BallState merge(ActorState actorState) {
        BallState oldState = this.clone();
        BallState newState = (BallState) actorState;
        if (newState.position != null) {
            position = newState.position;
        }
        if (newState.owner != null) {
            owner = newState.owner;
        }
        return oldState;
    }

    @Override
    public void unmerge(ActorState actorState) {
        BallState oldState = (BallState) actorState;
        position = oldState.position;
        owner = oldState.owner;
    }

    @Override
    public Boolean isBlank() {
        return position == null && owner == null;
    }

    @Override
    public BallState clone() {
        BallState clonedState = new BallState();
        clonedState.position = position;
        clonedState.owner = owner;
        return clonedState;
    }

    @Override
    public BallState interpolate(ActorState nextState, Integer interpolant, EasingFunction easingFunction) {
        BallState nextBallState = (BallState) nextState;
        BallState interpolatedState = this.clone();
        if (position != null && nextBallState.position != null) {
            interpolatedState.position = position.interpolate(nextBallState.position, interpolant, easingFunction);
        }
        interpolatedState.owner = nextBallState.owner;
        return interpolatedState;
    }

    public PlayerInstance getOwner() {
        return owner;
    }

    public void setOwner(PlayerInstance owner) {
        this.owner = owner;
    }

}
