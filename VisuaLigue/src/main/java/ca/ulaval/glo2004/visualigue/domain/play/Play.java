package ca.ulaval.glo2004.visualigue.domain.play;

import ca.ulaval.glo2004.visualigue.domain.DomainObject;
import ca.ulaval.glo2004.visualigue.domain.play.actorinstance.ActorInstance;
import ca.ulaval.glo2004.visualigue.domain.play.actorstate.ActorState;
import ca.ulaval.glo2004.visualigue.domain.play.frame.Frame;
import ca.ulaval.glo2004.visualigue.domain.play.keyframe.Keyframe;
import ca.ulaval.glo2004.visualigue.domain.sport.Sport;
import ca.ulaval.glo2004.visualigue.domain.xmladapters.XmlPlayAdapter;
import java.util.Map.Entry;
import java.util.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

@XmlRootElement(name = "play")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlJavaTypeAdapter(XmlPlayAdapter.class)
public class Play extends DomainObject {

    private String title;
    private String defaultThumbnailImage = "/images/generic-play-thumbnail.png";
    private UUID thumbnailImageUUID;
    private UUID sportUUID;
    @XmlTransient
    private Sport sport;
    private final TreeMap<Pair<ActorInstance, Integer>, Keyframe> keyframes = new TreeMap();
    private final Map<UUID, ActorInstance> actorInstances = new HashMap();
    private Integer definedLength;

    public Play() {
        //Required for JAXB instanciation.
    }

    public Play(String title, Sport sport) {
        this.title = title;
        this.sport = sport;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public UUID getThumbnailImageUUID() {
        return thumbnailImageUUID;
    }

    public void setThumbnailImageUUID(UUID thumbnailImageUUID) {
        this.thumbnailImageUUID = thumbnailImageUUID;
    }

    public Boolean hasThumbnail() {
        return thumbnailImageUUID != null;
    }

    public String getDefaultThumbnailImage() {
        return defaultThumbnailImage;
    }

    public void setDefaultThumbnailImage(String defaultThumbnailImage) {
        this.defaultThumbnailImage = defaultThumbnailImage;
    }

    public UUID getSportUUID() {
        return sportUUID;
    }

    public void setSportUUID(UUID sportUUID) {
        this.sportUUID = sportUUID;
    }

    public Sport getSport() {
        return sport;
    }

    public void setSport(Sport sport) {
        this.sport = sport;
    }

    public ActorInstance getActorInstance(UUID actorInstanceUUID) {
        return actorInstances.get(actorInstanceUUID);
    }

    public Optional<ActorState> mergeKeyframe(Integer time, ActorInstance actorInstance, ActorState actorState) {
        actorInstances.put(actorInstance.getUUID(), actorInstance);
        if (keyframes.containsKey(new ImmutablePair(actorInstance, time))) {
            Keyframe keyframe = keyframes.get(new ImmutablePair(time, actorInstance));
            return Optional.of(keyframe.mergeActorState(actorState));
        } else {
            Keyframe keyframe = new Keyframe(time, actorInstance, actorState);
            keyframes.put(new ImmutablePair(actorInstance, time), keyframe);
            return Optional.empty();
        }
    }

    public void unmergeKeyframe(Integer time, ActorInstance actorInstance, Optional<ActorState> oldState) {
        Keyframe keyframe = keyframes.get(new ImmutablePair(actorInstance, time));
        if (oldState.isPresent()) {
            keyframe.unmergeActorState(actorInstance, oldState.get());
        } else {
            keyframes.remove(new ImmutablePair(actorInstance, time));
        }
    }

    public Integer getLength() {
        Optional<Keyframe> maxKeyframe = keyframes.values().stream().max((k1, k2) -> k1.getTime().compareTo(k2.getTime()));
        if (maxKeyframe.isPresent()) {
            return maxKeyframe.get().getTime();
        } else {
            return 0;
        }
    }

    public Frame getFrame(Integer time) {
        Frame frame = new Frame();
        actorInstances.values().forEach(actorInstance -> {
            Entry<Pair<ActorInstance, Integer>, Keyframe> floorKeyframeEntry = keyframes.ceilingEntry(new ImmutablePair(actorInstance, time));
            Entry<Pair<ActorInstance, Integer>, Keyframe> ceilingKeyframeEntry = keyframes.ceilingEntry(new ImmutablePair(actorInstance, time));
            if (floorKeyframeEntry != null && ceilingKeyframeEntry == null) {
                frame.addActorState(floorKeyframeEntry.getKey().getLeft(), floorKeyframeEntry.getValue().getActorState());
            } else if (floorKeyframeEntry != null) {
                Keyframe floorKeyframe = floorKeyframeEntry.getValue();
                Keyframe ceilingKeyframe = ceilingKeyframeEntry.getValue();
                Keyframe interpolatedKeyframe = floorKeyframe.interpolate((time - floorKeyframe.getTime()) / (ceilingKeyframe.getTime() - time), ceilingKeyframe);
                frame.addActorState(interpolatedKeyframe.getActorInstance(), interpolatedKeyframe.getActorState());
            }
        });
        return frame;
    }

    public int getDefinedLength() {
        return definedLength;
    }

    public void setDefinedLength(Integer definedLength) {
        this.definedLength = Math.max(definedLength, getLength());
    }
}
