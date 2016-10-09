package ca.ulaval.glo2004.visualigue.domain.resource;

import ca.ulaval.glo2004.visualigue.domain.DomainObject;

public class PersistentResource extends DomainObject {

    private String persistedImagePathName;
    private String sourceImagePathName;
    private Boolean persisted = true;
    private TargetPersistenceType targetPersistenceType;

    public PersistentResource() {
        //Required for JAXB instanciation.
    }

    public static PersistentResource fromResource(String resourceImagePathName) {
        PersistentResource persistentImageResource = new PersistentResource();
        persistentImageResource.persistedImagePathName = resourceImagePathName;
        persistentImageResource.targetPersistenceType = TargetPersistenceType.RESOURCE;
        return persistentImageResource;
    }

    public static PersistentResource fromSource(String sourceImagePathName) {
        PersistentResource persistentImageResource = new PersistentResource();
        persistentImageResource.sourceImagePathName = sourceImagePathName;
        persistentImageResource.targetPersistenceType = TargetPersistenceType.DATA_STORE;
        persistentImageResource.persisted = false;
        return persistentImageResource;
    }

    public String getPersistedImagePathName() {
        return persistedImagePathName;
    }

    public void setPersistedImagePathName(String persistedImagePathName) {
        this.persistedImagePathName = persistedImagePathName;
    }

    public Boolean isPersistedImagePathNameSet() {
        return persistedImagePathName != null;
    }

    public String getSourceImagePathName() {
        return sourceImagePathName;
    }

    public void setSourceImagePathName(String sourceImagePathName) {
        this.sourceImagePathName = sourceImagePathName;
    }

    public Boolean isSourceImagePathNameSet() {
        return sourceImagePathName != null;
    }

    public Boolean isPersisted() {
        return persisted;
    }

    public void setPersisted(Boolean isStored) {
        this.persisted = isStored;
    }

    public TargetPersistenceType getTargetPersistenceType() {
        return targetPersistenceType;
    }

    public void setTargetPersistenceType(TargetPersistenceType targetPersistenceType) {
        this.targetPersistenceType = targetPersistenceType;
    }

    public Boolean isResource() {
        return targetPersistenceType == TargetPersistenceType.RESOURCE;
    }

}
