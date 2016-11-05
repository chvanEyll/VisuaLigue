package ca.ulaval.glo2004.visualigue.domain;

import java.util.Objects;
import java.util.UUID;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlTransient;

@XmlAccessorType(XmlAccessType.FIELD)
public abstract class DomainObject implements Comparable<DomainObject> {

    @XmlID
    protected String uuid;
    @XmlTransient
    protected Boolean isDirty = false;

    public DomainObject() {
        this.uuid = UUID.randomUUID().toString();
    }

    public String getUUID() {
        return uuid;
    }

    public void setUUID(String uuid) {
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

    @Override
    public int compareTo(DomainObject domainObject) {
        if (domainObject == this) {
            return 0;
        }
        return uuid.compareTo(domainObject.uuid);
    }
}
