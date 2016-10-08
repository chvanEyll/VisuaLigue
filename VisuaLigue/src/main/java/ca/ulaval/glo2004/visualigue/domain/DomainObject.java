package ca.ulaval.glo2004.visualigue.domain;

import java.util.UUID;

public class DomainObject {

    protected UUID uuid;

    public DomainObject() {
        this.uuid = UUID.randomUUID();
    }

    public UUID getUUID() {
        return uuid;
    }

    public void setUUID(UUID uuid) {
        this.uuid = uuid;
    }
}
