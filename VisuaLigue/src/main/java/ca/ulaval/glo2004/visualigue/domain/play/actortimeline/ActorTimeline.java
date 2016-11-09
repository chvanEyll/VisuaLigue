package ca.ulaval.glo2004.visualigue.domain.play.actortimeline;

import ca.ulaval.glo2004.visualigue.domain.DomainObject;
import ca.ulaval.glo2004.visualigue.domain.play.actor.Actor;
import ca.ulaval.glo2004.visualigue.domain.play.actorstate.ActorProperty;
import ca.ulaval.glo2004.visualigue.domain.play.actorstate.ActorState;
import ca.ulaval.glo2004.visualigue.domain.play.keyframe.Keyframe;
import ca.ulaval.glo2004.visualigue.domain.play.keyframe.transition.KeyframeTransition;
import ca.ulaval.glo2004.visualigue.utils.geometry.Vector2;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.TreeMap;
import javax.xml.bind.annotation.XmlIDREF;

public class ActorTimeline extends DomainObject {

    private final TreeMap<ActorProperty, ActorPropertyTimeline> propertyTimelines = new TreeMap();
    @XmlIDREF
    private Actor actor;

    public ActorTimeline() {
        //Required for JAXB instanciation.
    }

    public ActorTimeline(Actor actor) {
        this.actor = actor;
    }

    public Keyframe merge(Long time, ActorProperty actorProperty, Object value, KeyframeTransition transition) {
        ActorPropertyTimeline propertyTimeline;
        if (propertyTimelines.containsKey(actorProperty)) {
            propertyTimeline = propertyTimelines.get(actorProperty);
        } else {
            propertyTimeline = new ActorPropertyTimeline(actorProperty);
            propertyTimelines.put(actorProperty, propertyTimeline);
        }
        return propertyTimeline.merge(time, value, transition);
    }

    public void unmerge(Long time, ActorProperty actorProperty, Keyframe oldKeyframe) {
        ActorPropertyTimeline propertyTimeline = propertyTimelines.get(actorProperty);
        propertyTimeline.unmerge(time, oldKeyframe);
        if (propertyTimeline.isEmpty()) {
            propertyTimelines.remove(actorProperty);
        }
    }

    public Long getLength() {
        Optional<ActorPropertyTimeline> longestPropertyTimeline = propertyTimelines.values().stream().max((t1, t2) -> t1.getLength().compareTo(t2.getLength()));
        if (longestPropertyTimeline.isPresent()) {
            return longestPropertyTimeline.get().getLength();
        } else {
            return 0L;
        }
    }

    public Boolean isEmpty() {
        return propertyTimelines.isEmpty();
    }

    public Actor getActor() {
        return actor;
    }

    public Object getLowerPropertyValue(Long time, ActorProperty actorProperty) {
        ActorPropertyTimeline propertyTimeline = propertyTimelines.get(actorProperty);
        return propertyTimeline.getLowerValue(time);
    }

    public Set<Long> getPropertyKeyframeTimes(ActorProperty actorProperty) {
        if (propertyTimelines.containsKey(actorProperty)) {
            return propertyTimelines.get(actorProperty).getTimes();
        } else {
            return new HashSet();
        }
    }

    public ActorState getActorState(Long time, Long nextStateLookaheadTime) {
        ActorState actorState = getCurrentActorState(time);
        if (actorState != null) {
            Vector2 nextPosition = (Vector2) propertyTimelines.get(ActorState.getPositionProperty()).getHigherValue(time + nextStateLookaheadTime);
            actorState.setNextPosition(nextPosition);
        }
        return actorState;
    }

    private ActorState getCurrentActorState(Long time) {
        ActorState baseState = actor.getBaseState();
        propertyTimelines.values().forEach(propertyTimeline -> {
            baseState.setPropertyValue(propertyTimeline.getActorProperty(), propertyTimeline.getValue(time));
        });
        return baseState;
    }
}
