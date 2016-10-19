package ca.ulaval.glo2004.visualigue.domain.play;

import ca.ulaval.glo2004.visualigue.domain.DomainObject;
import java.util.UUID;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "play")
@XmlAccessorType(XmlAccessType.FIELD)
public class Play extends DomainObject {

    private String title;
    private String defaultThumbnailImage = "/images/generic-play-thumbnail.png";
    private UUID thumbnailImageUUID;

    public Play() {
        //Required for JAXB instanciation.
    }

    public Play(String title) {
        this.title = title;
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
}
