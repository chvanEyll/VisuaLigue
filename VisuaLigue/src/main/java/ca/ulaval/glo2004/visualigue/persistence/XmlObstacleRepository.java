package ca.ulaval.glo2004.visualigue.persistence;

import ca.ulaval.glo2004.visualigue.domain.play.actor.obstacle.Obstacle;
import ca.ulaval.glo2004.visualigue.domain.play.actor.obstacle.ObstacleAlreadyExistsException;
import ca.ulaval.glo2004.visualigue.domain.play.actor.obstacle.ObstacleNotFoundException;
import ca.ulaval.glo2004.visualigue.domain.play.actor.obstacle.ObstacleRepository;
import ca.ulaval.glo2004.visualigue.persistence.marshalling.XmlRepositoryMarshaller;
import ca.ulaval.glo2004.visualigue.utils.ListUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.swing.SortOrder;

@Singleton
public class XmlObstacleRepository implements ObstacleRepository {

    private final XmlRepositoryMarshaller<Obstacle> xmlRepositoryMarshaller;
    private final Map<UUID, Obstacle> obstacles;

    @Inject
    public XmlObstacleRepository(XmlRepositoryMarshaller<Obstacle> xmlRepositoryMarshaller) {
        this.xmlRepositoryMarshaller = xmlRepositoryMarshaller;
        obstacles = xmlRepositoryMarshaller.unmarshalAll();
    }

    @Override
    public UUID persist(Obstacle obstacle) throws ObstacleAlreadyExistsException {
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
    public void delete(UUID uuid) throws ObstacleNotFoundException {
        if (!obstacles.containsKey(uuid)) {
            throw new ObstacleNotFoundException(String.format("Cannot find obstacle with UUID '%s'.", uuid.toString()));
        }
        xmlRepositoryMarshaller.remove(uuid);
        obstacles.remove(uuid);
    }

    @Override
    public Obstacle get(UUID uuid) throws ObstacleNotFoundException {
        Obstacle obstacle = obstacles.get(uuid);
        if (obstacle == null) {
            throw new ObstacleNotFoundException(String.format("Cannot find obstacle with UUID '%s'.", uuid.toString()));
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
        obstacles.keySet().stream().collect(Collectors.toList()).forEach(uuid -> {
            try {
                delete(uuid);
            } catch (ObstacleNotFoundException ex) {
                Logger.getLogger(XmlObstacleRepository.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

}
