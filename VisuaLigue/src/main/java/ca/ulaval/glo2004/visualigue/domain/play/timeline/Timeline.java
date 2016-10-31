package ca.ulaval.glo2004.visualigue.domain.play.timeline;

import ca.ulaval.glo2004.visualigue.domain.DomainObject;
import ca.ulaval.glo2004.visualigue.domain.play.actorinstance.ActorInstance;
import ca.ulaval.glo2004.visualigue.domain.play.actorstate.ActorState;
import ca.ulaval.glo2004.visualigue.domain.play.keyframe.Keyframe;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;

public class Timeline extends DomainObject {

    private final TreeMap<Integer, Keyframe> keyframes = new TreeMap();

    public ActorState mergeKeyframe(Integer time, ActorInstance actorInstance, ActorState actorState) {
        if (keyframes.containsKey(time)) {
            Keyframe keyframe = keyframes.get(time);
            return keyframe.mergeActorState(actorState);
        } else {
            Keyframe keyframe = new Keyframe(time, actorInstance, actorState);
            keyframes.put(time, keyframe);
            return null;
        }
    }

    public void unmergeKeyframe(Integer time, ActorInstance actorInstance, ActorState oldState) {
        Keyframe keyframe = keyframes.get(time);
        if (oldState != null) {
            keyframe.unmergeActorState(actorInstance, oldState);
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
        Map.Entry<Integer, Keyframe> floorKeyframeEntry = keyframes.ceilingEntry(time);
        Map.Entry<Integer, Keyframe> ceilingKeyframeEntry = keyframes.ceilingEntry(time);
        if (floorKeyframeEntry != null && ceilingKeyframeEntry == null) {
            return floorKeyframeEntry.getValue();
        } else if (floorKeyframeEntry != null) {
            Keyframe floorKeyframe = floorKeyframeEntry.getValue();
            Keyframe ceilingKeyframe = ceilingKeyframeEntry.getValue();
            return floorKeyframe.interpolate((time - floorKeyframe.getTime()) / (ceilingKeyframe.getTime() - time), ceilingKeyframe);
        } else {
            return null;
        }
    }
}
