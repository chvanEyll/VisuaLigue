package ca.ulaval.glo2004.visualigue.ui.models;

import java.util.UUID;

public class ModelBase {

    private UUID uuid;
    private Boolean isNew = true;
    private Boolean isDirty = false;
    private Boolean isDeleted = false;

    public UUID getUUID() {
        return uuid;
    }

    public void setUUID(UUID uuid) {
        this.uuid = uuid;
    }

    public Boolean isNew() {
        return isNew;
    }

    public void setIsNew(Boolean isNew) {
        this.isNew = isNew;
    }

    public Boolean isDirty() {
        return isDirty;
    }

    public void makeDirty() {
        isDirty = true;
    }

    public Boolean isDeleted() {
        return isDeleted;
    }

    public void markAsDeleted() {
        isDeleted = true;
    }

}
