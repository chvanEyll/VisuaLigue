package ca.ulaval.glo2004.visualigue.domain.play.actor.obstacle;

import ca.ulaval.glo2004.visualigue.domain.play.actor.Actor;
import java.util.UUID;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "obstacle")
@XmlAccessorType(XmlAccessType.FIELD)
public class Obstacle extends Actor {

    private String name;
    private Boolean isBuiltIn = false;
    private String builtInImagePathName = "/images/built-in-obstacle-icons/cone-icon.png";
    private UUID customImageUUID;

    public Obstacle() {
        //Required for JAXB instanciation.
    }

    public Obstacle(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getIsBuiltIn() {
        return isBuiltIn;
    }

    public void setIsBuiltIn(Boolean isBuiltIn) {
        this.isBuiltIn = isBuiltIn;
    }

    public String getBuiltInImagePathName() {
        return builtInImagePathName;
    }

    public void setBuiltInImagePathName(String builtInImagePathName) {
        this.builtInImagePathName = builtInImagePathName;
    }

    public UUID getCustomImageUUID() {
        return customImageUUID;
    }

    public void setCustomImageUUID(UUID customImageUUID) {
        this.customImageUUID = customImageUUID;
    }

    public Boolean hasCustomImage() {
        return customImageUUID != null;
    }

}
