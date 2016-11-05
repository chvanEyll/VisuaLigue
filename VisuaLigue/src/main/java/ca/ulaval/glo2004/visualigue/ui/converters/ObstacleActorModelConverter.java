package ca.ulaval.glo2004.visualigue.ui.converters;

import ca.ulaval.glo2004.visualigue.domain.image.ImageRepository;
import ca.ulaval.glo2004.visualigue.domain.obstacle.Obstacle;
import ca.ulaval.glo2004.visualigue.domain.play.actorinstance.ObstacleInstance;
import ca.ulaval.glo2004.visualigue.domain.play.actorstate.ObstacleState;
import ca.ulaval.glo2004.visualigue.ui.models.ObstacleModel;
import ca.ulaval.glo2004.visualigue.ui.models.actors.ObstacleActorModel;
import javax.inject.Inject;

public class ObstacleActorModelConverter {

    private ImageRepository imageRepository;

    @Inject
    public ObstacleActorModelConverter(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    public ObstacleActorModel convert(ObstacleModel obstacleModel) {
        ObstacleActorModel model = new ObstacleActorModel();
        model.imagePathName.set(obstacleModel.currentImagePathName.get());
        model.builtInImagePathName.set(obstacleModel.builtInImagePathName.get());
        return model;
    }

    public ObstacleActorModel convert(ObstacleInstance obstacleInstance, ObstacleState obstacleState) {
        ObstacleActorModel model = new ObstacleActorModel();
        update(model, obstacleInstance, obstacleState);
        return model;
    }

    public void update(ObstacleActorModel model, ObstacleInstance obstacleInstance, ObstacleState obstacleState) {
        model.setUUID(obstacleInstance.getUUID());
        model.position.set(obstacleState.getPosition());
        model.hoverText.set(obstacleInstance.getObstacle().getName());
        Obstacle obstacle = obstacleInstance.getObstacle();
        if (obstacle.hasCustomImage()) {
            model.imagePathName.set(imageRepository.get(obstacle.getCustomImageUUID()));
        } else {
            model.builtInImagePathName.set(obstacle.getBuiltInImagePathName());
        }
    }

}
