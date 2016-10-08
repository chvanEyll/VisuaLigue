package ca.ulaval.glo2004.visualigue.domain.sport;

import java.util.Set;
import java.util.UUID;

public interface SportRepository {

    void persist(Sport sport) throws SportAlreadyExistsException;

    Sport getByUUID(UUID hashCode) throws SportNotFoundException;

    void update(Sport sport) throws SportAlreadyExistsException;

    Set<Sport> getAll();

    Boolean isEmpty();
}
