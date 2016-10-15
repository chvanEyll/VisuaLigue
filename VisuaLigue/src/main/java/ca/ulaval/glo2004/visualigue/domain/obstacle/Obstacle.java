package ca.ulaval.glo2004.visualigue.domain.obstacle;

import ca.ulaval.glo2004.visualigue.domain.DomainObject;
import java.util.UUID;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)
public class Obstacle extends DomainObject {

    private String name;
    private Boolean isBuiltIn = false;
    private String builtInIconPathName = "/images/built-in-sport-icons/cone-icon.fxml";
    private UUID customImageUUID;

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

    public String getBuiltInIconPathName() {
        return builtInIconPathName;
    }

    public void setBuiltInIconPathName(String builtInIconPathName) {
        this.builtInIconPathName = builtInIconPathName;
    }

    public UUID getCustomImageUUID() {
        return customImageUUID;
    }

    public void setCustomImageUUID(UUID customImageUUID) {
        this.customImageUUID = customImageUUID;
    }

}
