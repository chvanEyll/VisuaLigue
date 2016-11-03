package ca.ulaval.glo2004.visualigue.domain.play;

import ca.ulaval.glo2004.visualigue.domain.DomainObject;
import ca.ulaval.glo2004.visualigue.domain.play.actorinstance.ActorInstance;
import ca.ulaval.glo2004.visualigue.domain.play.actorstate.ActorState;
import ca.ulaval.glo2004.visualigue.domain.play.actortimeline.ActorTimeline;
import ca.ulaval.glo2004.visualigue.domain.play.frame.Frame;
import ca.ulaval.glo2004.visualigue.domain.play.keyframe.Keyframe;
import ca.ulaval.glo2004.visualigue.domain.sport.Sport;
import ca.ulaval.glo2004.visualigue.domain.xmladapters.XmlSportRefAdapter;
import java.util.Optional;
import java.util.TreeMap;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlRootElement(name = "play")
public class Play extends DomainObject {

    private static final Integer NEXT_KEYFRAME_LOOKAHEAD_TIME = 50;
    private String title;
    private String defaultThumbnailImage = "/images/generic-play-thumbnail.png";
    private String thumbnailImageUUID;
    @XmlJavaTypeAdapter(XmlSportRefAdapter.class)
    private Sport sport;
    private final TreeMap<ActorInstance, ActorTimeline> actorTimelines = new TreeMap();

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

    public String getThumbnailImageUUID() {
        return thumbnailImageUUID;
    }

    public void setThumbnailImageUUID(String thumbnailImageUUID) {
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

    public Sport getSport() {
        return sport;
    }

    public void setSport(Sport sport) {
        this.sport = sport;
    }

    public ActorInstance getActorInstance(String actorInstanceUUID) {
        return actorTimelines.keySet().stream().filter(a -> a.getUUID().equals(actorInstanceUUID)).findFirst().get();
    }

    public ActorState mergeKeyframe(Integer time, ActorInstance actorInstance, ActorState actorState) {
        ActorTimeline timeline;
        if (actorTimelines.containsKey(actorInstance)) {
            timeline = actorTimelines.get(actorInstance);
        } else {
            timeline = new ActorTimeline(actorInstance);
            actorTimelines.put(actorInstance, timeline);
        }
        return timeline.mergeKeyframe(time, actorInstance, actorState);
    }

    public void unmergeKeyframe(Integer time, ActorInstance actorInstance, ActorState oldState) {
        ActorTimeline actorTimeline = actorTimelines.get(actorInstance);
        if (oldState != null) {
            actorTimeline.unmergeKeyframe(time, actorInstance, oldState);
        } else {
            actorTimelines.remove(actorInstance);
        }
    }

    public Integer getLength() {
        Optional<ActorTimeline> longestTimeline = actorTimelines.values().stream().max((t1, t2) -> t1.getLength().compareTo(t2.getLength()));
        if (longestTimeline.isPresent()) {
            return longestTimeline.get().getLength();
        } else {
            return 0;
        }
    }

    public Frame getFrame(Integer time) {
        Frame frame = new Frame();
        actorTimelines.entrySet().stream().forEach(e -> {
            ActorInstance actorInstance = e.getKey();
            ActorTimeline timeline = e.getValue();
            Keyframe keyframe = timeline.getKeyframe(time);
            if (keyframe != null) {
                frame.setCurrentActorState(actorInstance, keyframe.getActorState());
            }
            Keyframe nextKeyframe = timeline.getNextKeyframe(time + NEXT_KEYFRAME_LOOKAHEAD_TIME);
            if (nextKeyframe != null) {
                frame.setNextActorState(actorInstance, nextKeyframe.getActorState());
            } else {
                frame.removeNextActorState(actorInstance);
            }
        });
        return frame;
    }
}
