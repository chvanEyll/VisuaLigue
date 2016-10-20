package ca.ulaval.glo2004.visualigue.domain.play;

import ca.ulaval.glo2004.visualigue.domain.DomainObject;
import ca.ulaval.glo2004.visualigue.domain.play.actor.Actor;
import ca.ulaval.glo2004.visualigue.domain.play.actorstate.ActorState;
import ca.ulaval.glo2004.visualigue.domain.play.keyframe.Keyframe;
import ca.ulaval.glo2004.visualigue.domain.sport.Sport;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.UUID;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@XmlRootElement(name = "play")
@XmlAccessorType(XmlAccessType.FIELD)
public class Play extends DomainObject {

    private String title;
    private String defaultThumbnailImage = "/images/generic-play-thumbnail.png";
    private UUID thumbnailImageUUID;
    private UUID sportUUID;
    @XmlTransient
    private Sport sport;
    private SortedMap<Integer, Keyframe> keyframes = new TreeMap<>();

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

    public Sport getSport() {
        return sport;
    }

    public void mergeActorState(Integer time, Actor actor, ActorState state) {
        Keyframe keyframe;
        if (keyframes.containsKey(time)) {
            keyframe = keyframes.get(time);
        } else {
            keyframe = new Keyframe();
            keyframes.put(time, keyframe);
        }
        keyframe.mergeActorState(actor, state);
    }

    public void removeActorState(Integer time, Actor actor, ActorState state) {
        Keyframe keyframe = keyframes.get(time);
        keyframe.removeActorState(actor, state);
    }

}
