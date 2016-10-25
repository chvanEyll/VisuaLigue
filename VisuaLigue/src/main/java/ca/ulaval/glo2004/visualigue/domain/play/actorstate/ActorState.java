package ca.ulaval.glo2004.visualigue.domain.play.actorstate;

import ca.ulaval.glo2004.visualigue.domain.DomainObject;
import ca.ulaval.glo2004.visualigue.utils.geometry.Vector2;
import ca.ulaval.glo2004.visualigue.utils.math.easing.EasingFunction;
import java.util.Optional;

public abstract class ActorState extends DomainObject {

    protected Optional<Vector2> position = Optional.empty();

    public Vector2 getPosition() {
        return position.get();
    }

    public abstract ActorState merge(ActorState actorState);

    public abstract void unmerge(ActorState actorState);

    public abstract Boolean isBlank();

    public abstract ActorState interpolate(ActorState nextState, Integer interpolant, EasingFunction easingFunction);

}
