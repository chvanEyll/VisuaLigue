package ca.ulaval.glo2004.visualigue.domain.resource;

import ca.ulaval.glo2004.visualigue.domain.DomainObject;
import ca.ulaval.glo2004.visualigue.utils.FilenameUtils;

public class PersistentResource extends DomainObject {

    private String persistedPathName;
    private String sourcePathName;
    private Boolean persisted = true;
    private TargetPersistenceType targetPersistenceType;

    public PersistentResource() {
        //Required for JAXB instanciation.
    }

    public static PersistentResource fromResource(String resourcePathName) {
        PersistentResource persistentResource = new PersistentResource();
        persistentResource.persistedPathName = resourcePathName;
        persistentResource.targetPersistenceType = TargetPersistenceType.RESOURCE;
        return persistentResource;
    }

    public static PersistentResource fromSource(String sourcePathName) {
        PersistentResource persistentResource = new PersistentResource();
        persistentResource.sourcePathName = sourcePathName;
        persistentResource.targetPersistenceType = TargetPersistenceType.DATA_STORE;
        persistentResource.persisted = false;
        return persistentResource;
    }

    public String getPersistedPathName() {
        return persistedPathName;
    }

    public void setPersistedPathName(String persistedPathName) {
        this.persistedPathName = persistedPathName;
    }

    public Boolean isPersistedPathNameSet() {
        return persistedPathName != null;
    }

    public String getSourcePathName() {
        return sourcePathName;
    }

    public void setSourcePathName(String sourcePathName) {
        this.sourcePathName = sourcePathName;
    }

    public Boolean isSourcePathNameSet() {
        return sourcePathName != null;
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

    public String getURIString() {
        if (targetPersistenceType == TargetPersistenceType.DATA_STORE) {
            return FilenameUtils.getURIString(persistedPathName);
        } else {
            return persistedPathName;
        }
    }
}
