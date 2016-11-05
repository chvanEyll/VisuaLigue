package ca.ulaval.glo2004.visualigue.domain.play;

import java.util.List;
import java.util.function.Function;
import javax.swing.SortOrder;

public interface PlayRepository {

    String persist(Play play) throws PlayAlreadyExistsException;

    void update(Play play);

    Play get(String uuid) throws PlayNotFoundException;

    void delete(Play play) throws PlayNotFoundException;

    Play revert(Play play) throws PlayNotFoundException;

    List<Play> getAll();

    List<Play> getAll(Function<Play, Comparable> sortFunction, SortOrder sortOrder);

    void clear();
}
