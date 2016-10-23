package ca.ulaval.glo2004.visualigue.domain.sport.playercategory;

import java.util.List;
import java.util.UUID;
import java.util.function.Function;
import javax.swing.SortOrder;

public interface PlayerCategoryRepository {

    UUID persist(PlayerCategory playerCategory) throws PlayerCategoryAlreadyExistsException;

    void update(PlayerCategory playerCategory);

    PlayerCategory get(UUID uuid) throws PlayerCategoryNotFoundException;

    void delete(PlayerCategory playerCategory) throws PlayerCategoryNotFoundException;

    List<PlayerCategory> getAll(Function<PlayerCategory, Comparable> sortFunction, SortOrder sortOrder);

    Boolean isEmpty();

    void clear();
}
