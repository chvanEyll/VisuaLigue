package ca.ulaval.glo2004.visualigue.domain;

import java.util.Objects;
import java.util.UUID;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlID;

@XmlAccessorType(XmlAccessType.FIELD)
public abstract class DomainObject implements Comparable<DomainObject> {

    @XmlID
    protected String uuid;

    public DomainObject() {
        this.uuid = UUID.randomUUID().toString();
    }

    public String getUUID() {
        return uuid;
    }

    public void setUUID(String uuid) {
        this.uuid = uuid;
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
    public int compareTo(DomainObject obj) {
        if (obj == this) {
            return 0;
        }
        return uuid.compareTo(obj.uuid);
    }
}
