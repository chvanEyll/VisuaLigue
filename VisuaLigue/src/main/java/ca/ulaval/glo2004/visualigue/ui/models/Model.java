package ca.ulaval.glo2004.visualigue.ui.models;

public class Model {

    private Object associatedEntity;
    private Boolean isNew = true;
    private Boolean isDirty = false;
    private Boolean isDeleted = false;

    public Object getAssociatedEntity() {
        return associatedEntity;
    }

    public void setAssociatedEntity(Object associatedEntity) {
        this.associatedEntity = associatedEntity;
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

    public void markDirty() {
        isDirty = true;
    }

    public Boolean isDeleted() {
        return isDeleted;
    }

    public void delete() {
        isDeleted = true;
    }

}
