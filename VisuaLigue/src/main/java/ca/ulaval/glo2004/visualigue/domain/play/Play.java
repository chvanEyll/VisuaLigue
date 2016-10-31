package ca.ulaval.glo2004.visualigue.domain.play;

import ca.ulaval.glo2004.visualigue.domain.DomainObject;
import ca.ulaval.glo2004.visualigue.domain.play.actorinstance.ActorInstance;
import ca.ulaval.glo2004.visualigue.domain.play.actorstate.ActorState;
import ca.ulaval.glo2004.visualigue.domain.play.frame.Frame;
import ca.ulaval.glo2004.visualigue.domain.play.keyframe.Keyframe;
import ca.ulaval.glo2004.visualigue.domain.play.timeline.Timeline;
import ca.ulaval.glo2004.visualigue.domain.sport.Sport;
import ca.ulaval.glo2004.visualigue.domain.xmladapters.XmlPlayAdapter;
import java.util.Optional;
import java.util.TreeMap;
import java.util.UUID;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlRootElement(name = "play")
@XmlJavaTypeAdapter(XmlPlayAdapter.class)
public class Play extends DomainObject {

    private String title;
    private String defaultThumbnailImage = "/images/generic-play-thumbnail.png";
    private UUID thumbnailImageUUID;
    private UUID sportUUID;
    @XmlTransient
    private Sport sport;
    private final TreeMap<ActorInstance, Timeline> timelines = new TreeMap();
    private Integer definedLength = 0;

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
        return timelines.keySet().stream().filter(a -> a.getUUID() == actorInstanceUUID).findFirst().get();
    }

    public ActorState mergeKeyframe(Integer time, ActorInstance actorInstance, ActorState actorState) {
        if (timelines.containsKey(actorInstance)) {
            Timeline timeline = timelines.get(actorInstance);
            return timeline.mergeKeyframe(time, actorInstance, actorState);
        } else {
            Timeline timeline = new Timeline();
            timelines.put(actorInstance, timeline);
            return null;
        }
    }

    public void unmergeKeyframe(Integer time, ActorInstance actorInstance, ActorState oldState) {
        Timeline timeline = timelines.get(actorInstance);
        if (oldState != null) {
            timeline.unmergeKeyframe(time, actorInstance, oldState);
        } else {
            timelines.remove(actorInstance);
        }
    }

    public Integer getLength() {
        Optional<Timeline> longestTimeline = timelines.values().stream().max((t1, t2) -> t1.getLength().compareTo(t2.getLength()));
        if (longestTimeline.isPresent()) {
            return longestTimeline.get().getLength();
        } else {
            return 0;
        }
    }

    public Frame getFrame(Integer time) {
        Frame frame = new Frame();
        timelines.entrySet().stream().forEach(e -> {
            Timeline timeline = e.getValue();
            Keyframe keyframe = timeline.getKeyframe(time);
            if (keyframe != null) {
                frame.addActorState(keyframe.getActorInstance(), keyframe.getActorState());
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
