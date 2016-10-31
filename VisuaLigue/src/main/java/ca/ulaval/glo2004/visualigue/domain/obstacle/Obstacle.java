package ca.ulaval.glo2004.visualigue.domain.obstacle;

import ca.ulaval.glo2004.visualigue.domain.DomainObject;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "obstacle")
public class Obstacle extends DomainObject {

    private String name;
    private Boolean isBuiltIn = false;
    private String builtInImagePathName = "/images/built-in-obstacle-icons/cone-icon.png";
    private String customImageUUID;

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

    public String getCustomImageUUID() {
        return customImageUUID;
    }

    public void setCustomImageUUID(String customImageUUID) {
        this.customImageUUID = customImageUUID;
    }

    public Boolean hasCustomImage() {
        return customImageUUID != null;
    }

}
