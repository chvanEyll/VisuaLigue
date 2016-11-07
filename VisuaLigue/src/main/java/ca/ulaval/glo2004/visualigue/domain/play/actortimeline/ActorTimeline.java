package ca.ulaval.glo2004.visualigue.domain.play.actortimeline;

import ca.ulaval.glo2004.visualigue.domain.DomainObject;
import ca.ulaval.glo2004.visualigue.domain.play.actor.Actor;
import ca.ulaval.glo2004.visualigue.domain.play.actorstate.ActorState;
import ca.ulaval.glo2004.visualigue.domain.play.keyframe.Keyframe;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;

public class ActorTimeline extends DomainObject {

    private final TreeMap<Integer, Keyframe> keyframes = new TreeMap();

    public ActorTimeline() {
        //Required for JAXB instanciation.
    }

    public ActorState mergeKeyframe(Integer time, Actor actor, ActorState actorState) {
        Keyframe keyframe;
        if (keyframes.containsKey(time)) {
            keyframe = keyframes.get(time);

        } else {
            keyframe = new Keyframe(time, actor, actorState);
            keyframes.put(time, keyframe);
        }
        return keyframe.mergeActorState(actorState);
    }

    public void unmergeKeyframe(Integer time, Actor actor, ActorState oldState) {
        Keyframe keyframe = keyframes.get(time);
        if (oldState != null) {
            keyframe.unmergeActorState(actor, oldState);
        } else {
            keyframes.remove(time);
        }
    }

    public Integer getLength() {
        Optional<Integer> maxTime = keyframes.keySet().stream().max((k1, k2) -> {
            return k1.compareTo(k2);
        });
        if (maxTime.isPresent()) {
            return maxTime.get();
        } else {
            return 0;
        }
    }

    public Keyframe getKeyframe(Integer time) {
        Map.Entry<Integer, Keyframe> floorKeyframeEntry = keyframes.floorEntry(time);
        Map.Entry<Integer, Keyframe> ceilingKeyframeEntry = keyframes.ceilingEntry(time);
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

    public Keyframe getNextKeyframe(Integer time) {
        Map.Entry<Integer, Keyframe> ceilingKeyframeEntry = keyframes.higherEntry(time);
        if (ceilingKeyframeEntry == null) {
            return null;
        } else {
            return ceilingKeyframeEntry.getValue();
        }
    }
}
