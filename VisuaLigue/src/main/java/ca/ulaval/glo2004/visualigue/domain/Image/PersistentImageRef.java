package ca.ulaval.glo2004.visualigue.domain.Image;

import ca.ulaval.glo2004.visualigue.domain.DomainObject;

public class PersistentImageRef extends DomainObject {

    private String storedImagePathName;
    private String sourceImagePathName;
    private Boolean isStored = false;

    public PersistentImageRef() {
        //Required for JAXB instanciation.
    }

    public PersistentImageRef(String sourceImagePathName) {
        this.sourceImagePathName = sourceImagePathName;
    }

    public void replace(String sourceImagePathName) {
        this.sourceImagePathName = sourceImagePathName;
        isStored = false;
    }

    public String getStoredImagePathName() {
        return storedImagePathName;
    }

    public void setStoredImagePathName(String storedImagePathName) {
        this.storedImagePathName = storedImagePathName;
    }

    public String getSourceImagePathName() {
        return sourceImagePathName;
    }

    public void setSourceImagePathName(String sourceImagePathName) {
        this.sourceImagePathName = sourceImagePathName;
    }

    public Boolean isStored() {
        return isStored;
    }

    public void setStored(Boolean isStored) {
        this.isStored = isStored;
    }
}
