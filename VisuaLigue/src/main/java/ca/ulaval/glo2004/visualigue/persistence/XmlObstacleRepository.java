package ca.ulaval.glo2004.visualigue.persistence;

import ca.ulaval.glo2004.visualigue.domain.obstacle.Obstacle;
import ca.ulaval.glo2004.visualigue.domain.obstacle.ObstacleAlreadyExistsException;
import ca.ulaval.glo2004.visualigue.domain.obstacle.ObstacleNotFoundException;
import ca.ulaval.glo2004.visualigue.domain.obstacle.ObstacleRepository;
import ca.ulaval.glo2004.visualigue.persistence.marshalling.XmlRepositoryMarshaller;
import ca.ulaval.glo2004.visualigue.utils.EventHandler;
import ca.ulaval.glo2004.visualigue.utils.ListUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.swing.SortOrder;

@Singleton
public class XmlObstacleRepository implements ObstacleRepository {

    public final EventHandler<Obstacle> onObstacleDelete = new EventHandler();
    private final XmlRepositoryMarshaller<Obstacle> xmlRepositoryMarshaller;
    private final Map<String, Obstacle> obstacles;

    @Inject
    public XmlObstacleRepository(XmlRepositoryMarshaller<Obstacle> xmlRepositoryMarshaller) {
        this.xmlRepositoryMarshaller = xmlRepositoryMarshaller;
        obstacles = xmlRepositoryMarshaller.unmarshalAll();
    }

    @Override
    public String persist(Obstacle obstacle) throws ObstacleAlreadyExistsException {
        if (obstacles.containsValue(obstacle)) {
            throw new ObstacleAlreadyExistsException(String.format("An obstacle with UUID '%s' already exists.", obstacle.getUUID()));
        }
        obstacles.put(obstacle.getUUID(), obstacle);
        xmlRepositoryMarshaller.marshal(obstacle, obstacle.getUUID());
        return obstacle.getUUID();
    }

    @Override
    public void update(Obstacle obstacle) {
        if (!obstacles.containsValue(obstacle)) {
            throw new IllegalStateException("Update requested for an object that is not persisted.");
        }
        xmlRepositoryMarshaller.marshal(obstacle, obstacle.getUUID());
    }

    @Override
    public void delete(Obstacle obstacle) throws ObstacleNotFoundException {
        if (!obstacles.containsKey(obstacle.getUUID())) {
            throw new ObstacleNotFoundException(String.format("Cannot find obstacle with UUID '%s'.", obstacle.getUUID()));
        }
        onObstacleDelete.fire(this, obstacle);
        xmlRepositoryMarshaller.remove(obstacle.getUUID());
        obstacles.remove(obstacle.getUUID());
    }

    @Override
    public Obstacle get(String uuid) throws ObstacleNotFoundException {
        Obstacle obstacle = obstacles.get(uuid);
        if (obstacle == null) {
            throw new ObstacleNotFoundException(String.format("Cannot find obstacle with UUID '%s'.", uuid));
        }
        return obstacle;
    }

    @Override
    public List<Obstacle> getAll(Function<Obstacle, Comparable> sortFunction, SortOrder sortOrder) {
        List<Obstacle> obstacleList = new ArrayList(obstacles.values());
        return ListUtils.sort(obstacleList, sortFunction, sortOrder);
    }

    @Override
    public void clear() {
        obstacles.values().stream().collect(Collectors.toList()).forEach(uuid -> {
            delete(uuid);
        });
    }

}
