package ca.ulaval.glo2004.visualigue.domain.obstacle;

import java.util.List;
import java.util.function.Function;
import javax.swing.SortOrder;

public interface ObstacleRepository {

    String persist(Obstacle obstacle) throws ObstacleAlreadyExistsException;

    void update(Obstacle obstacle);

    Obstacle get(String uuid) throws ObstacleNotFoundException;

    void delete(Obstacle obstacle) throws ObstacleNotFoundException;

    List<Obstacle> getAll(Function<Obstacle, Comparable> sortFunction, SortOrder sortOrder);

    void clear();
}
