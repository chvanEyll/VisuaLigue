package ca.ulaval.glo2004.visualigue.ui.converters.layers;

import ca.ulaval.glo2004.visualigue.domain.image.ImageRepository;
import ca.ulaval.glo2004.visualigue.domain.obstacle.Obstacle;
import ca.ulaval.glo2004.visualigue.domain.play.actor.ActorInstance;
import ca.ulaval.glo2004.visualigue.domain.play.actor.ObstacleActor;
import ca.ulaval.glo2004.visualigue.domain.play.actorstate.ObstacleState;
import ca.ulaval.glo2004.visualigue.ui.models.ObstacleModel;
import ca.ulaval.glo2004.visualigue.ui.models.layers.ObstacleLayerModel;
import javax.inject.Inject;

public class ObstacleLayerModelConverter {

    private ImageRepository imageRepository;

    @Inject
    public ObstacleLayerModelConverter(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    public ObstacleLayerModel convert(ObstacleModel obstacleModel) {
        ObstacleLayerModel model = new ObstacleLayerModel();
        model.imagePathName.set(obstacleModel.currentImagePathName.get());
        model.builtInImagePathName.set(obstacleModel.builtInImagePathName.get());
        return model;
    }

    public ObstacleLayerModel convert(ActorInstance actorInstance) {
        ObstacleLayerModel model = new ObstacleLayerModel();
        update(model, actorInstance);
        return model;
    }

    public void update(ObstacleLayerModel model, ActorInstance actorInstance) {
        ObstacleActor obstacleActor = (ObstacleActor) actorInstance.getActor();
        ObstacleState obstacleState = (ObstacleState) actorInstance.getActorState();
        model.setUUID(obstacleActor.getUUID());
        model.isLocked.set(obstacleState.isLocked());
        model.opacity.set(obstacleState.getOpacity());
        model.visible.set(obstacleState.isVisible());
        model.zOrder.set(obstacleState.getZOrder());
        model.showLabel.set(obstacleState.getShowLabel());
        model.position.set(obstacleState.getPosition());
        model.hoverText.set(obstacleActor.getObstacle().getName());
        Obstacle obstacle = obstacleActor.getObstacle();
        if (obstacle.hasCustomImage()) {
            model.imagePathName.set(imageRepository.get(obstacle.getCustomImageUUID()));
        } else {
            model.builtInImagePathName.set(obstacle.getBuiltInImagePathName());
        }
    }

}
