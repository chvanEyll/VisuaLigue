package ca.ulaval.glo2004.visualigue.domain.play;

import java.util.List;
import java.util.UUID;
import java.util.function.Function;
import javax.swing.SortOrder;

public interface PlayRepository {

    UUID persist(Play play) throws PlayAlreadyExistsException;

    void update(Play play);

    Play get(UUID uuid) throws PlayNotFoundException;

    void delete(Play play) throws PlayNotFoundException;

    void discard(Play play) throws PlayNotFoundException;

    List<Play> getAll(Function<Play, Comparable> sortFunction, SortOrder sortOrder);

    void clear();
}
