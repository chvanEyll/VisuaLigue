package ca.ulaval.glo2004.visualigue.ui.models;

public class ModelBase {

    private String uuid;
    private Boolean isNew = true;
    private Boolean isDirty = false;
    private Boolean isDeleted = false;

    public String getUUID() {
        return uuid;
    }

    public void setUUID(String uuid) {
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
