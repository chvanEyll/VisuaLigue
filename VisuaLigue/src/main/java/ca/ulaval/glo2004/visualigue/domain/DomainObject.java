package ca.ulaval.glo2004.visualigue.domain;

import java.util.UUID;

public class DomainObject {

    protected UUID uuid;
    protected Boolean isDirty = false;

    public DomainObject() {
        this.uuid = UUID.randomUUID();
    }

    public UUID getUUID() {
        return uuid;
    }

    public void setUUID(UUID uuid) {
        this.uuid = uuid;
    }

    public Boolean isDirty() {
        return isDirty;
    }

    public void setDirty(Boolean isDirty) {
        this.isDirty = isDirty;
    }

    public void makeDirty() {
        this.isDirty = true;
    }

    public void clean() {
        this.isDirty = true;
    }
}
