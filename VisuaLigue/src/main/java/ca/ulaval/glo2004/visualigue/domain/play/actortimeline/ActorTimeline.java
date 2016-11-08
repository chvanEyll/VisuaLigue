package ca.ulaval.glo2004.visualigue.domain.play.actortimeline;

import ca.ulaval.glo2004.visualigue.domain.DomainObject;
import ca.ulaval.glo2004.visualigue.domain.play.actor.Actor;
import ca.ulaval.glo2004.visualigue.domain.play.actorstate.ActorProperty;
import ca.ulaval.glo2004.visualigue.domain.play.actorstate.ActorState;
import ca.ulaval.glo2004.visualigue.domain.play.keyframe.Keyframe;
import ca.ulaval.glo2004.visualigue.domain.play.keyframe.transition.KeyframeTransition;
import java.util.Optional;
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

    public ActorState getActorState(Long time) {
        ActorState baseState = actor.getBaseState();
        propertyTimelines.values().forEach(propertyTimeline -> {
            baseState.setPropertyValue(propertyTimeline.getActorProperty(), propertyTimeline.getValue(time));
        });
        return baseState;
    }

    public ActorState getNextActorState(Long time) {
        ActorState baseState = actor.getBaseState();
        propertyTimelines.values().forEach(propertyTimeline -> {
            baseState.setPropertyValue(propertyTimeline.getActorProperty(), propertyTimeline.getNextValue(time));
        });
        return baseState;
    }
}
