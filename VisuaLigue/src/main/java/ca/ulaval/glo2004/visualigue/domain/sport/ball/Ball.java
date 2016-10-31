package ca.ulaval.glo2004.visualigue.domain.sport.ball;

import ca.ulaval.glo2004.visualigue.domain.DomainObject;

public class Ball extends DomainObject {

    private String name = "Ballon";
    private String customImageUUID;
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

    public String getCustomImageUUID() {
        return customImageUUID;
    }

    public void setCustomImageUUID(String customImageUUID) {
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
