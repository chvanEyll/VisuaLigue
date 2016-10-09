package ca.ulaval.glo2004.visualigue.domain.sport;

import java.util.List;
import java.util.function.Function;
import javax.swing.SortOrder;

public interface SportRepository {

    void persist(Sport sport) throws SportAlreadyExistsException;

    void update(Sport sport) throws SportAlreadyExistsException;

    List<Sport> getAll(Function<Sport, Comparable> sortFunction, SortOrder sortOrder);

    Boolean isEmpty();
}
