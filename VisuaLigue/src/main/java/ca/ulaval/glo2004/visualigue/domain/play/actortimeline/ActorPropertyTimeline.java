package ca.ulaval.glo2004.visualigue.domain.play.actortimeline;

import ca.ulaval.glo2004.visualigue.domain.DomainObject;
import ca.ulaval.glo2004.visualigue.domain.play.actorstate.ActorProperty;
import ca.ulaval.glo2004.visualigue.domain.play.keyframe.Keyframe;
import ca.ulaval.glo2004.visualigue.domain.play.keyframe.transition.KeyframeTransition;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.TreeMap;

public class ActorPropertyTimeline extends DomainObject {

    private final TreeMap<Long, Keyframe> keyframes = new TreeMap();
    private ActorProperty actorProperty;

    public ActorPropertyTimeline() {
        //Required for JAXB instanciation.
    }

    public ActorPropertyTimeline(ActorProperty actorProperty) {
        this.actorProperty = actorProperty;
    }

    public Set<Long> getTimes() {
        return keyframes.keySet();
    }

    public ActorProperty getActorProperty() {
        return actorProperty;
    }

    public Keyframe merge(Long time, Object value, KeyframeTransition transition) {
        Keyframe oldKeyframe = null;
        if (keyframes.containsKey(time)) {
            oldKeyframe = keyframes.get(time);
        }
        Keyframe keyframe = new Keyframe(value, transition);
        keyframes.put(time, keyframe);
        return oldKeyframe;
    }

    public void unmerge(Long time, Keyframe oldKeyframe) {
        if (oldKeyframe == null) {
            keyframes.remove(time);
        } else {
            keyframes.put(time, oldKeyframe);
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

    public Object getValue(Long time) {
        Map.Entry<Long, Keyframe> floorKeyframeEntry = keyframes.floorEntry(time);
        Map.Entry<Long, Keyframe> ceilingKeyframeEntry = keyframes.ceilingEntry(time);
        if (floorKeyframeEntry != null && ceilingKeyframeEntry == null) {
            return floorKeyframeEntry.getValue().getValue();
        } else if (floorKeyframeEntry != null && (floorKeyframeEntry.getValue() == ceilingKeyframeEntry.getValue())) {
            return floorKeyframeEntry.getValue().getValue();
        } else if (floorKeyframeEntry != null && (floorKeyframeEntry.getValue() != ceilingKeyframeEntry.getValue())) {
            Keyframe floorKeyframe = floorKeyframeEntry.getValue();
            Keyframe ceilingKeyframe = ceilingKeyframeEntry.getValue();
            Double interpolatedTime = (time - floorKeyframeEntry.getKey()) / (double) (ceilingKeyframeEntry.getKey() - floorKeyframeEntry.getKey());
            return floorKeyframe.interpolate(interpolatedTime, ceilingKeyframe);
        } else {
            return null;
        }
    }

    public Object getNextValue(Long time) {
        Map.Entry<Long, Keyframe> higherKeyframeEntry = keyframes.higherEntry(time);
        if (higherKeyframeEntry != null) {
            return higherKeyframeEntry.getValue().getValue();
        } else {
            return null;
        }
    }
}
