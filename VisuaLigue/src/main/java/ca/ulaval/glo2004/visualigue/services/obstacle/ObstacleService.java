package ca.ulaval.glo2004.visualigue.services.obstacle;

import ca.ulaval.glo2004.visualigue.domain.image.ImageRepository;
import ca.ulaval.glo2004.visualigue.domain.obstacle.*;
import ca.ulaval.glo2004.visualigue.utils.EventHandler;
import java.util.List;
import java.util.UUID;
import java.util.function.Function;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.swing.SortOrder;

@Singleton
public class ObstacleService {

    private final ObstacleRepository obstacleRepository;
    private final ObstacleFactory obstacleFactory;
    private final ImageRepository imageRepository;

    public EventHandler<Obstacle> onObstacleCreated = new EventHandler();
    public EventHandler<Obstacle> onObstacleUpdated = new EventHandler();
    public EventHandler<Obstacle> onObstacleDeleted = new EventHandler();

    @Inject
    public ObstacleService(final ObstacleRepository obstacleRepository, final ImageRepository imageRepository, final ObstacleFactory obstacleFactory) {
        this.obstacleRepository = obstacleRepository;
        this.imageRepository = imageRepository;
        this.obstacleFactory = obstacleFactory;
    }

    public UUID createObstacle(String name) throws ObstacleAlreadyExistsException {
        Obstacle obstacle = obstacleFactory.create(name);
        obstacleRepository.persist(obstacle);
        onObstacleCreated.fire(this, obstacle);
        return obstacle.getUUID();
    }

    public void updateObstacle(UUID obstacleInstanceUUID, String name) throws ObstacleNotFoundException {
        Obstacle obstacle = obstacleRepository.get(obstacleInstanceUUID);
        obstacle.setName(name);
        obstacleRepository.update(obstacle);
        onObstacleUpdated.fire(this, obstacle);
    }

    public void updateObstacleImage(UUID sportUUID, String sourceImagePathName) throws ObstacleNotFoundException {
        Obstacle obstacle = obstacleRepository.get(sportUUID);
        UUID imageUuid = imageRepository.persist(sourceImagePathName);
        if (obstacle.hasCustomImage()) {
            imageRepository.delete(obstacle.getCustomImageUUID());
        }
        obstacle.setCustomImageUUID(imageUuid);
        obstacleRepository.update(obstacle);
    }

    public void deleteObstacle(UUID obstacleInstanceUUID) throws ObstacleNotFoundException {
        Obstacle obstacle = obstacleRepository.get(obstacleInstanceUUID);
        obstacleRepository.delete(obstacle);
        onObstacleDeleted.fire(this, obstacle);
    }

    public Obstacle getObstacle(UUID obstacleInstanceUUID) throws ObstacleNotFoundException {
        return obstacleRepository.get(obstacleInstanceUUID);
    }

    public List<Obstacle> getObstacles(Function<Obstacle, Comparable> sortFunction, SortOrder sortOrder) {
        return obstacleRepository.getAll(sortFunction, sortOrder);
    }
}
