package ca.ulaval.glo2004.visualigue.domain.obstacle;

import java.util.List;
import java.util.UUID;
import java.util.function.Function;
import javax.swing.SortOrder;

public interface ObstacleRepository {

    UUID persist(Obstacle obstacle) throws ObstacleAlreadyExistsException;

    void update(Obstacle obstacle);

    Obstacle get(UUID uuid) throws ObstacleNotFoundException;

    void delete(UUID uuid) throws ObstacleNotFoundException;

    List<Obstacle> getAll(Function<Obstacle, Comparable> sortFunction, SortOrder sortOrder);

    void clear();
}
