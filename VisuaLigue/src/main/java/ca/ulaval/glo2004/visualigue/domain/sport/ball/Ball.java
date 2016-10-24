package ca.ulaval.glo2004.visualigue.domain.sport.ball;

import ca.ulaval.glo2004.visualigue.domain.DomainObject;
import java.util.UUID;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)
public class Ball extends DomainObject {

    private String name = "Ballon";
    private UUID customImageUUID;
    private String builtInImagePathName = "/images/built-in-ball-icons/generic-ball-icon.fxml";

    public Ball() {
        //Required for JAXB instanciation.
    }

    public Ball(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UUID getCustomImageUUID() {
        return customImageUUID;
    }

    public void setCustomImageUUID(UUID customImageUUID) {
        this.customImageUUID = customImageUUID;
    }

    public String getBuiltInImagePathName() {
        return builtInImagePathName;
    }

    public void setBuiltInImagePathName(String builtInImagePathName) {
        this.builtInImagePathName = builtInImagePathName;
    }

    public Boolean hasCustomImage() {
        return customImageUUID != null;
    }
}
