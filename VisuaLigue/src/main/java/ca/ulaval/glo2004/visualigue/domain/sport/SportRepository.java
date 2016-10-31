package ca.ulaval.glo2004.visualigue.domain.sport;

import java.util.List;
import java.util.function.Function;
import javax.swing.SortOrder;

public interface SportRepository {

    String persist(Sport sport) throws SportAlreadyExistsException;

    void update(Sport sport) throws SportAlreadyExistsException;

    Sport get(String uuid) throws SportNotFoundException;

    void delete(Sport sport) throws SportNotFoundException;

    List<Sport> getAll(Function<Sport, Comparable> sortFunction, SortOrder sortOrder);

    Boolean isEmpty();

    void clear();
}
