package ca.ulaval.glo2004.visualigue.domain.play.actorstate;

import ca.ulaval.glo2004.visualigue.domain.play.actorinstance.PlayerInstance;
import ca.ulaval.glo2004.visualigue.domain.play.position.Position;
import ca.ulaval.glo2004.visualigue.utils.math.easing.EasingFunction;
import java.util.Optional;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "ballstate")
@XmlAccessorType(XmlAccessType.FIELD)
public class BallState extends ActorState implements Cloneable {

    private Optional<Position> position = Optional.empty();
    private Optional<PlayerInstance> owner = Optional.empty();

    private BallState() {

    }

    public BallState(Optional<Position> position, Optional<PlayerInstance> owner) {
        this.position = position;
        this.owner = owner;
    }

    @Override
    public BallState merge(ActorState actorState) {
        BallState oldState = this.clone();
        BallState newState = (BallState) actorState;
        if (newState.position.isPresent()) {
            position = newState.position;
        }
        if (newState.owner.isPresent()) {
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
        return !position.isPresent() && !owner.isPresent();
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
        if (position.isPresent() && nextBallState.position.isPresent()) {
            interpolatedState.position = Optional.of(position.get().interpolate(nextBallState.position.get(), interpolant, easingFunction));
        }
        interpolatedState.owner = nextBallState.owner;
        return interpolatedState;
    }

}
