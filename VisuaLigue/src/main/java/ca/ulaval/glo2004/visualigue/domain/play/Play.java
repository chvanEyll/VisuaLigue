package ca.ulaval.glo2004.visualigue.domain.play;

import ca.ulaval.glo2004.visualigue.domain.DomainObject;
import ca.ulaval.glo2004.visualigue.domain.obstacle.Obstacle;
import ca.ulaval.glo2004.visualigue.domain.play.actor.Actor;
import ca.ulaval.glo2004.visualigue.domain.play.frame.ActorInstance;
import ca.ulaval.glo2004.visualigue.domain.play.actor.ObstacleActor;
import ca.ulaval.glo2004.visualigue.domain.play.actor.PlayerActor;
import ca.ulaval.glo2004.visualigue.domain.play.actorstate.ActorProperty;
import ca.ulaval.glo2004.visualigue.domain.play.actorstate.ActorState;
import ca.ulaval.glo2004.visualigue.domain.play.actortimeline.ActorTimeline;
import ca.ulaval.glo2004.visualigue.domain.play.frame.Frame;
import ca.ulaval.glo2004.visualigue.domain.play.keyframe.Keyframe;
import ca.ulaval.glo2004.visualigue.domain.play.keyframe.transition.KeyframeTransition;
import ca.ulaval.glo2004.visualigue.domain.sport.Sport;
import ca.ulaval.glo2004.visualigue.domain.sport.playercategory.PlayerCategory;
import ca.ulaval.glo2004.visualigue.domain.xmladapters.XmlSportRefAdapter;
import ca.ulaval.glo2004.visualigue.utils.math.MathUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.TreeMap;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlRootElement(name = "play")
public class Play extends DomainObject {

    private static final Long NEXT_KEYFRAME_LOOKAHEAD_TIME = 50L;
    private String title = "Nouveau jeu";
    private String defaultThumbnailImage = "/images/generic-play-thumbnail.png";
    private String thumbnailImageUUID;
    @XmlJavaTypeAdapter(XmlSportRefAdapter.class)
    private Sport sport;
    private Long timelineLength = 0L;
    private Long keyPointInterval = 1000L;
    @XmlTransient
    protected Boolean isDirty = false;
    @XmlTransient
    protected Integer pendingUpdateCount = 0;
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

    public Integer getPendingUpdateCount() {
        return pendingUpdateCount;
    }

    public void setPendingUpdateCount(Integer pendingUpdateCount) {
        this.pendingUpdateCount = pendingUpdateCount;
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

    public Long addKeyPoint() {
        this.timelineLength = timelineLength + keyPointInterval;
        return this.timelineLength;
    }

    public Long removeKeyPoint() {
        this.timelineLength = timelineLength - keyPointInterval;
        return this.timelineLength;
    }

    public Boolean previousKeyPointExists(Long time) {
        return time - keyPointInterval > 0;
    }

    public Long getPreviousKeyPointTime(Long time) {
        if (previousKeyPointExists(time)) {
            return time - keyPointInterval;
        } else {
            return null;
        }
    }

    public Long getTimelineLength() {
        return this.timelineLength;
    }

    public Long getKeyPointInterval() {
        return keyPointInterval;
    }

    public Keyframe merge(Long time, Actor actor, ActorProperty actorProperty, Object value, KeyframeTransition stateTransition) {
        ActorTimeline timeline;
        if (actorTimelines.containsKey(actor)) {
            timeline = actorTimelines.get(actor);
        } else {
            timeline = new ActorTimeline(actor);
            actorTimelines.put(actor, timeline);
        }
        timelineLength = Math.max(time, timelineLength);
        return timeline.merge(time, actorProperty, value, stateTransition);
    }

    public void unmerge(Long time, Actor actor, ActorProperty actorProperty, Keyframe oldKeyframe) {
        ActorTimeline actorTimeline = actorTimelines.get(actor);
        actorTimeline.unmerge(time, actorProperty, oldKeyframe);
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

    public Object getActorPropertyValue(Long time, Actor actor, ActorProperty actorProperty) {
        ActorTimeline actorTimeline = actorTimelines.get(actor);
        return actorTimeline.getPropertyValue(time, actorProperty);
    }

    public Object getActorLowerPropertyValue(Long time, Actor actor, ActorProperty actorProperty) {
        ActorTimeline actorTimeline = actorTimelines.get(actor);
        return actorTimeline.getLowerPropertyValue(time, actorProperty);
    }

    public Frame getFrame(Long time, Boolean showPlayerTrails) {
        Frame frame = new Frame(time);
        actorTimelines.entrySet().stream().forEach(e -> {
            ActorTimeline actorTimeline = e.getValue();
            ActorState actorState = actorTimeline.getActorState(time, NEXT_KEYFRAME_LOOKAHEAD_TIME);
            actorState.setIsLocked(time % keyPointInterval != 0);
            frame.addActorInstance(new ActorInstance(actorTimeline.getActor(), actorState, 0));
            if (showPlayerTrails && time.equals(timelineLength) && actorTimeline.getActor() instanceof PlayerActor) {
                addPlayerTrailsToFrame(frame, time, actorTimeline, actorState);
            }
        });
        return frame;
    }

    private void addPlayerTrailsToFrame(Frame frame, Long time, ActorTimeline actorTimeline, ActorState activeState) {
        Set<Long> positionKeyframeTimes = actorTimeline.getPropertyKeyframeTimes(ActorState.getPositionProperty());
        Integer instanceCount = 1;
        for (Long trailTime : positionKeyframeTimes) {
            if (!trailTime.equals(time)) {
                ActorState actorState = actorTimeline.getActorState(trailTime, NEXT_KEYFRAME_LOOKAHEAD_TIME);
                actorState.setIsLocked(true);
                actorState.setOpacity(0.5);
                actorState.setShowLabel(false);
                actorState.setZOrder(-instanceCount);
                frame.addActorInstance(new ActorInstance(actorTimeline.getActor(), actorState, instanceCount));
                instanceCount += 1;
            }
        }
    }

}
