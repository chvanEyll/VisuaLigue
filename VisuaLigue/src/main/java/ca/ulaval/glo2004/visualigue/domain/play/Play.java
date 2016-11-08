package ca.ulaval.glo2004.visualigue.domain.play;

import ca.ulaval.glo2004.visualigue.domain.DomainObject;
import ca.ulaval.glo2004.visualigue.domain.obstacle.Obstacle;
import ca.ulaval.glo2004.visualigue.domain.play.actor.Actor;
import ca.ulaval.glo2004.visualigue.domain.play.actor.ObstacleActor;
import ca.ulaval.glo2004.visualigue.domain.play.actor.PlayerActor;
import ca.ulaval.glo2004.visualigue.domain.play.actorstate.ActorState;
import ca.ulaval.glo2004.visualigue.domain.play.actortimeline.ActorTimeline;
import ca.ulaval.glo2004.visualigue.domain.play.frame.Frame;
import ca.ulaval.glo2004.visualigue.domain.play.keyframe.Keyframe;
import ca.ulaval.glo2004.visualigue.domain.sport.Sport;
import ca.ulaval.glo2004.visualigue.domain.sport.playercategory.PlayerCategory;
import ca.ulaval.glo2004.visualigue.domain.xmladapters.XmlSportRefAdapter;
import ca.ulaval.glo2004.visualigue.utils.math.MathUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.TreeMap;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlRootElement(name = "play")
public class Play extends DomainObject {

    private static final Integer NEXT_KEYFRAME_LOOKAHEAD_TIME = 50;
    private String title = "Nouveau jeu";
    private String defaultThumbnailImage = "/images/generic-play-thumbnail.png";
    private String thumbnailImageUUID;
    @XmlJavaTypeAdapter(XmlSportRefAdapter.class)
    private Sport sport;
    private Long timelineLength = 0L;
    private Long keyPointInterval = 1000L;
    @XmlTransient
    protected Boolean isDirty = false;
    private final TreeMap<Actor, ActorTimeline> actorTimelines = new TreeMap();

    public Play() {
        //Required for JAXB instanciation.
    }

    public Play(Sport sport) {
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

    public Boolean hasThumbnail() {
        return thumbnailImageUUID != null;
    }

    public String getDefaultThumbnailImage() {
        return defaultThumbnailImage;
    }

    public Sport getSport() {
        return sport;
    }

    public Boolean isDirty() {
        return isDirty;
    }

    public void setDirty(Boolean isDirty) {
        this.isDirty = isDirty;
    }

    public Boolean containsPlayerCategory(PlayerCategory playerCategory) {
        return getActors().stream().anyMatch(a -> a instanceof PlayerActor && ((PlayerActor) a).getPlayerCategory().equals(playerCategory));
    }

    public Boolean containsObstacle(Obstacle obstacle) {
        return getActors().stream().anyMatch(a -> a instanceof ObstacleActor && ((ObstacleActor) a).getObstacle().equals(obstacle));
    }

    private List<Actor> getActors() {
        return new ArrayList(actorTimelines.keySet());
    }

    public Actor getActor(String actorUUID) {
        return actorTimelines.keySet().stream().filter(a -> a.getUUID().equals(actorUUID)).findFirst().get();
    }

    public Integer getNumberOfKeyPoints() {
        return (int) (MathUtils.roundUp(timelineLength, keyPointInterval) / keyPointInterval) + 1;
    }

    public void addKeyPoint() {
        this.timelineLength = timelineLength + keyPointInterval;
    }

    public void removeKeyPoint() {
        this.timelineLength = timelineLength - keyPointInterval;
    }

    public Long getTimelineLength() {
        return this.timelineLength;
    }

    public Long getKeyPointInterval() {
        return keyPointInterval;
    }

    public ActorState mergeKeyframe(Long time, Actor actor, ActorState actorState) {
        ActorTimeline timeline;
        if (actorTimelines.containsKey(actor)) {
            timeline = actorTimelines.get(actor);
        } else {
            timeline = new ActorTimeline();
            actorTimelines.put(actor, timeline);
        }
        timelineLength = Math.max(time, timelineLength);
        return timeline.mergeKeyframe(time, actor, actorState, keyPointInterval);
    }

    public void unmergeKeyframe(Long time, Actor actor, ActorState oldState) {
        ActorTimeline actorTimeline = actorTimelines.get(actor);
        actorTimeline.unmergeKeyframe(time, actor, oldState);
        if (actorTimeline.isEmpty()) {
            actorTimelines.remove(actor);
        }
    }

    public Long getLength() {
        Optional<ActorTimeline> longestTimeline = actorTimelines.values().stream().max((t1, t2) -> t1.getLength().compareTo(t2.getLength()));
        if (longestTimeline.isPresent()) {
            return longestTimeline.get().getLength();
        } else {
            return 0L;
        }
    }

    public Frame getFrame(Long time) {
        Frame frame = new Frame(time);
        actorTimelines.entrySet().stream().forEach(e -> {
            Actor actor = e.getKey();
            ActorTimeline timeline = e.getValue();
            Keyframe keyframe = timeline.getKeyframe(time);
            if (keyframe != null) {
                frame.setCurrentActorState(actor, keyframe.getActorState());
            }
            Keyframe nextKeyframe = timeline.getNextKeyframe(time + NEXT_KEYFRAME_LOOKAHEAD_TIME);
            if (nextKeyframe != null) {
                frame.setNextActorState(actor, nextKeyframe.getActorState());
            } else {
                frame.removeNextActorState(actor);
            }
        });
        return frame;
    }
}
