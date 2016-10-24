package ca.ulaval.glo2004.visualigue.domain.play.actorstate;

import ca.ulaval.glo2004.visualigue.domain.DomainObject;
import ca.ulaval.glo2004.visualigue.domain.play.position.Position;
import ca.ulaval.glo2004.visualigue.utils.math.easing.EasingFunction;
import java.util.Optional;

public abstract class ActorState extends DomainObject {

    protected Optional<Position> position = Optional.empty();

    public Position getPosition() {
        return position.get();
    }

    public abstract ActorState merge(ActorState actorState);

    public abstract void unmerge(ActorState actorState);

    public abstract Boolean isBlank();

    public abstract ActorState interpolate(ActorState nextState, Integer interpolant, EasingFunction easingFunction);

}
