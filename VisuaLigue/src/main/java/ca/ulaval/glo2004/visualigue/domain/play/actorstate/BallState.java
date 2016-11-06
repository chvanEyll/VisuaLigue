package ca.ulaval.glo2004.visualigue.domain.play.actorstate;

import ca.ulaval.glo2004.visualigue.domain.play.actorinstance.PlayerInstance;
import ca.ulaval.glo2004.visualigue.domain.play.actorstate.transition.StateTransition;
import ca.ulaval.glo2004.visualigue.utils.geometry.Vector2;
import javax.xml.bind.annotation.XmlIDREF;

public class BallState extends ActorState implements Cloneable {

    @XmlIDREF
    private PlayerInstance owner;

    public BallState() {
        //Required for JAXB instanciation.
    }

    public BallState(Vector2 position, StateTransition positionTransition, PlayerInstance owner) {
        this.position = position;
        this.positionTransition = positionTransition;
        this.owner = owner;
    }

    @Override
    public BallState merge(ActorState actorState) {
        BallState oldState = this.clone();
        BallState newState = (BallState) actorState;
        if (newState.position != null) {
            position = newState.position;
        }
        if (newState.positionTransition != null) {
            positionTransition = newState.positionTransition;
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
        clonedState.positionTransition = positionTransition;
        clonedState.owner = owner;
        return clonedState;
    }

    @Override
    public BallState interpolate(ActorState nextState, Double interpolant) {
        BallState nextBallState = (BallState) nextState;
        BallState interpolatedState = this.clone();
        if (position != null && nextBallState.position != null) {
            interpolatedState.position = positionTransition.interpolate(position, nextBallState.position, interpolant);
        }
        interpolatedState.owner = nextBallState.owner;
        return interpolatedState;
    }

    public PlayerInstance getOwner() {
        return owner;
    }
}
