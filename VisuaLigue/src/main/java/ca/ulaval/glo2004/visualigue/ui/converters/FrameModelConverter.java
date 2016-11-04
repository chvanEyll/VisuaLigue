package ca.ulaval.glo2004.visualigue.ui.converters;

import ca.ulaval.glo2004.visualigue.domain.image.ImageRepository;
import ca.ulaval.glo2004.visualigue.domain.obstacle.Obstacle;
import ca.ulaval.glo2004.visualigue.domain.play.actorinstance.ActorInstance;
import ca.ulaval.glo2004.visualigue.domain.play.actorinstance.BallInstance;
import ca.ulaval.glo2004.visualigue.domain.play.actorinstance.ObstacleInstance;
import ca.ulaval.glo2004.visualigue.domain.play.actorinstance.PlayerInstance;
import ca.ulaval.glo2004.visualigue.domain.play.actorstate.ActorState;
import ca.ulaval.glo2004.visualigue.domain.play.actorstate.BallState;
import ca.ulaval.glo2004.visualigue.domain.play.actorstate.ObstacleState;
import ca.ulaval.glo2004.visualigue.domain.play.actorstate.PlayerState;
import ca.ulaval.glo2004.visualigue.domain.play.frame.Frame;
import ca.ulaval.glo2004.visualigue.ui.models.ActorModel;
import ca.ulaval.glo2004.visualigue.ui.models.FrameModel;
import ca.ulaval.glo2004.visualigue.ui.models.PlayModel;
import java.util.Map;
import java.util.stream.Collectors;
import javax.inject.Inject;

public class FrameModelConverter {

    private ImageRepository imageRepository;

    @Inject
    public FrameModelConverter(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    public FrameModel update(Frame frame, FrameModel frameModel, PlayModel playModel) {
        frameModel.setUUID(frame.getUUID());
        frameModel.setIsNew(false);
        frameModel.time.set(frame.getTime());
        updateExistingActorInstances(frame, frameModel, playModel, frame.getCurrentActorStates());
        removeOldActorInstances(frameModel, frame.getCurrentActorStates());
        return frameModel;
    }
    
    private void updateExistingActorInstances(Frame frame, FrameModel frameModel, PlayModel playModel, Map<ActorInstance, ActorState> actorStates) {
        actorStates.entrySet().forEach(e -> {
            updateActorInstance(frame, frameModel, playModel, e.getKey(), e.getValue());
        });
    }

    private void removeOldActorInstances(FrameModel frameModel, Map<ActorInstance, ActorState> actorStates) {
        frameModel.actorModels.keySet().stream().collect(Collectors.toList()).forEach(actorModelUUID -> {
            if (!actorStates.keySet().stream().anyMatch(actorInstance -> actorInstance.getUUID().equals(actorModelUUID))) {
                removeActorInstance(frameModel, actorModelUUID);
            }
        });
    }

    private void updateActorInstance(Frame frame, FrameModel frameModel, PlayModel playModel, ActorInstance actorInstance, ActorState actorState) {
        if (frameModel.actorModels.containsKey(actorInstance.getUUID())) {
            ActorModel actorModel = frameModel.actorModels.get(actorInstance.getUUID());
            updateActorModel(frame, actorModel, playModel, actorInstance, actorState);
        } else {
            ActorModel actorModel = new ActorModel();
            updateActorModel(frame, actorModel, playModel, actorInstance, actorState);
            frameModel.actorModels.put(actorInstance.getUUID(), actorModel);
        }
    }

    private void removeActorInstance(FrameModel frameModel, String actorInstanceUUID) {
        frameModel.actorModels.remove(actorInstanceUUID);
    }

    private void updateActorModel(Frame frame, ActorModel actorModel, PlayModel playModel, ActorInstance actorInstance, ActorState actorState) {
        if (actorInstance instanceof PlayerInstance) {
            updatePlayerModel(frame, actorModel, actorInstance, actorState);
        } else if (actorInstance instanceof BallInstance) {
            updateBallModel(actorModel, playModel, actorState);
        } else if (actorInstance instanceof ObstacleInstance) {
            updateObstacleModel(actorModel, actorInstance, actorState);
        }
    }

    private void updatePlayerModel(Frame frame, ActorModel actorModel, ActorInstance actorInstance, ActorState actorState) {
        PlayerState playerState = (PlayerState) actorState;
        PlayerInstance playerInstance = (PlayerInstance) actorInstance;
        actorModel.type.set(ActorModel.Type.PLAYER);
        actorModel.position.set(playerState.getPosition());
        ActorState nextActorState = frame.getNextActorState(actorInstance);
        if (nextActorState != null) {
            actorModel.nextPosition.set(nextActorState.getPosition());
        } else {
            actorModel.nextPosition.set(null);
        }
        actorModel.color.set(playerInstance.getPlayerCategory().getColor(playerInstance.getTeamSide()));
        actorModel.orientation.set(playerState.getOrientation());
        actorModel.label.set(playerInstance.getPlayerCategory().getAbbreviation());
        actorModel.hoverText.set(playerInstance.getPlayerCategory().getName());
    }

    private void updateBallModel(ActorModel actorModel, PlayModel playModel, ActorState actorState) {
        BallState ballState = (BallState) actorState;
        actorModel.type.set(ActorModel.Type.BALL);
        actorModel.position.set(ballState.getPosition());
        actorModel.hoverText.set(playModel.ballModel.name.get());
        if (playModel.ballModel.imagePathName.isNotEmpty().get()) {
            actorModel.imagePathName.set(playModel.ballModel.imagePathName.get());
        } else {
            actorModel.builtInImagePathName.set(playModel.ballModel.builtInImagePathName.get());
        }
    }

    private void updateObstacleModel(ActorModel actorModel, ActorInstance actorInstance, ActorState actorState) {
        ObstacleState obstacleState = (ObstacleState) actorState;
        ObstacleInstance obstacleInstance = (ObstacleInstance) actorInstance;
        actorModel.type.set(ActorModel.Type.OBSTACLE);
        actorModel.position.set(obstacleState.getPosition());
        actorModel.hoverText.set(obstacleInstance.getObstacle().getName());
        Obstacle obstacle = obstacleInstance.getObstacle();
        if (obstacle.hasCustomImage()) {
            actorModel.imagePathName.set(imageRepository.get(obstacle.getCustomImageUUID()));
        } else {
            actorModel.builtInImagePathName.set(obstacle.getBuiltInImagePathName());
        }
    }

}
