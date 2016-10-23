package ca.ulaval.glo2004.visualigue.domain;

import java.util.Objects;
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

    @Override
    public int hashCode() {
        return this.uuid.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final DomainObject other = (DomainObject) obj;
        return Objects.equals(this.uuid, other.uuid);
    }
}
