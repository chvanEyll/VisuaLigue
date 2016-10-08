package ca.ulaval.glo2004.visualigue.domain.sport;

import java.util.List;
import java.util.UUID;
import java.util.function.Function;
import javax.swing.SortOrder;

public interface SportRepository {

    void persist(Sport sport) throws SportAlreadyExistsException;

    Sport getByUUID(UUID hashCode) throws SportNotFoundException;

    void update(Sport sport) throws SportAlreadyExistsException;

    List<Sport> getAll(Function<Sport, Comparable> sortFunction, SortOrder sortOrder);

    Boolean isEmpty();
}
