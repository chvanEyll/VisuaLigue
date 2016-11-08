package ca.ulaval.glo2004.visualigue.domain.play.actortimeline;

import ca.ulaval.glo2004.visualigue.domain.DomainObject;
import ca.ulaval.glo2004.visualigue.domain.play.actor.Actor;
import ca.ulaval.glo2004.visualigue.domain.play.actorstate.ActorState;
import ca.ulaval.glo2004.visualigue.domain.play.keyframe.Keyframe;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;

public class ActorTimeline extends DomainObject {

    private final TreeMap<Long, Keyframe> keyframes = new TreeMap();

    public ActorTimeline() {
        //Required for JAXB instanciation.
    }

    public ActorState mergeKeyframe(Long time, Actor actor, ActorState actorState, Long keyPointInterval) {
        Keyframe keyframe;
        if (keyframes.containsKey(time)) {
            keyframe = keyframes.get(time);
            return keyframe.mergeActorState(actorState);
        } else {
            Keyframe previousKeypoint = getKeyframe(time - keyPointInterval);
            if (previousKeypoint != null) {
                keyframe = new Keyframe(time, actor, previousKeypoint.getActorState().clone());
                keyframe.mergeActorState(actorState);
            } else {
                keyframe = new Keyframe(time, actor, actorState);
            }
            keyframes.put(time, keyframe);
            return null;
        }
    }

    public void unmergeKeyframe(Long time, Actor actor, ActorState oldState) {
        Keyframe keyframe = keyframes.get(time);
        if (oldState != null) {
            keyframe.unmergeActorState(actor, oldState);
        } else {
            keyframes.remove(time);
        }
    }

    public Long getLength() {
        Optional<Long> maxTime = keyframes.keySet().stream().max((k1, k2) -> {
            return k1.compareTo(k2);
        });
        if (maxTime.isPresent()) {
            return maxTime.get();
        } else {
            return 0L;
        }
    }

    public Boolean isEmpty() {
        return keyframes.isEmpty();
    }

    public Keyframe getKeyframe(Long time) {
        Map.Entry<Long, Keyframe> floorKeyframeEntry = keyframes.floorEntry(time);
        Map.Entry<Long, Keyframe> ceilingKeyframeEntry = keyframes.ceilingEntry(time);
        if (floorKeyframeEntry != null && ceilingKeyframeEntry == null) {
            return floorKeyframeEntry.getValue();
        } else if (floorKeyframeEntry != null && (floorKeyframeEntry.getValue() == ceilingKeyframeEntry.getValue())) {
            return floorKeyframeEntry.getValue();
        } else if (floorKeyframeEntry != null && (floorKeyframeEntry.getValue() != ceilingKeyframeEntry.getValue())) {
            Keyframe floorKeyframe = floorKeyframeEntry.getValue();
            Keyframe ceilingKeyframe = ceilingKeyframeEntry.getValue();
            return floorKeyframe.interpolate((time - floorKeyframe.getTime()) / (double) (ceilingKeyframe.getTime() - floorKeyframe.getTime()), ceilingKeyframe);
        } else {
            return null;
        }
    }

    public Keyframe getNextKeyframe(Long time) {
        Map.Entry<Long, Keyframe> ceilingKeyframeEntry = keyframes.higherEntry(time);
        if (ceilingKeyframeEntry == null) {
            return null;
        } else {
            return ceilingKeyframeEntry.getValue();
        }
    }
}
