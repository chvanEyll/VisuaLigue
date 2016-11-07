package ca.ulaval.glo2004.visualigue.services.obstacle;

import ca.ulaval.glo2004.visualigue.domain.image.ImageRepository;
import ca.ulaval.glo2004.visualigue.domain.obstacle.Obstacle;
import ca.ulaval.glo2004.visualigue.domain.obstacle.ObstacleFactory;
import ca.ulaval.glo2004.visualigue.domain.obstacle.ObstacleNotFoundException;
import ca.ulaval.glo2004.visualigue.domain.obstacle.ObstacleRepository;
import ca.ulaval.glo2004.visualigue.utils.EventHandler;
import java.util.List;
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

    public String createObstacle(String name) {
        Obstacle obstacle = obstacleFactory.create(name);
        obstacleRepository.persist(obstacle);
        onObstacleCreated.fire(this, obstacle);
        return obstacle.getUUID();
    }

    public void updateObstacle(String obstacleUUID, String name) throws ObstacleNotFoundException {
        Obstacle obstacle = obstacleRepository.get(obstacleUUID);
        obstacle.setName(name);
        obstacleRepository.update(obstacle);
        onObstacleUpdated.fire(this, obstacle);
    }

    public void updateObstacleImage(String obstacleUUID, String sourceImagePathName) throws ObstacleNotFoundException {
        Obstacle obstacle = obstacleRepository.get(obstacleUUID);
        String imageUuid = imageRepository.replace(obstacle.getCustomImageUUID(), sourceImagePathName);
        obstacle.setCustomImageUUID(imageUuid);
        obstacleRepository.update(obstacle);
    }

    public void deleteObstacle(String obstacleUUID) throws ObstacleNotFoundException {
        Obstacle obstacle = obstacleRepository.get(obstacleUUID);
        obstacleRepository.delete(obstacle);
        onObstacleDeleted.fire(this, obstacle);
    }

    public Obstacle getObstacle(String obstacleUUID) throws ObstacleNotFoundException {
        return obstacleRepository.get(obstacleUUID);
    }

    public List<Obstacle> getObstacles(Function<Obstacle, Comparable> sortFunction, SortOrder sortOrder) {
        return obstacleRepository.getAll(sortFunction, sortOrder);
    }
}
