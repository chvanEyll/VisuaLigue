package ca.ulaval.glo2004.visualigue.ui.converters;

import ca.ulaval.glo2004.visualigue.domain.image.ImageRepository;
import ca.ulaval.glo2004.visualigue.domain.obstacle.Obstacle;
import ca.ulaval.glo2004.visualigue.domain.play.actorinstance.*;
import ca.ulaval.glo2004.visualigue.domain.play.actorstate.ActorState;
import ca.ulaval.glo2004.visualigue.domain.play.actorstate.BallState;
import ca.ulaval.glo2004.visualigue.domain.play.actorstate.ObstacleState;
import ca.ulaval.glo2004.visualigue.domain.play.actorstate.PlayerState;
import ca.ulaval.glo2004.visualigue.domain.play.frame.Frame;
import ca.ulaval.glo2004.visualigue.ui.models.ActorModel;
import ca.ulaval.glo2004.visualigue.ui.models.FrameModel;
import ca.ulaval.glo2004.visualigue.ui.models.PlayModel;
import java.util.Map;
import javax.inject.Inject;

public class FrameModelConverter {

    private ImageRepository imageRepository;

    @Inject
    public FrameModelConverter(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    public FrameModel update(PlayModel playModel, FrameModel model, Frame frame) {
        model.setUUID(frame.getUUID());
        model.setIsNew(false);
        Map<ActorInstance, ActorState> actorStates = frame.getActorStates();
        actorStates.entrySet().forEach(e -> {
            updateActorInstance(model, e.getKey(), e.getValue(), playModel);
        });
        model.actorStates.keySet().forEach(actorModelUUID -> {
            if (!actorStates.keySet().stream().anyMatch(actorInstance -> actorInstance.getUUID() == actorModelUUID)) {
                removeActorInstance(model, actorModelUUID);
            }
        });
        return model;
    }

    private void updateActorInstance(FrameModel model, ActorInstance actorInstance, ActorState actorState, PlayModel playModel) {
        if (model.actorStates.containsKey(actorInstance.getUUID())) {
            updateActorModel(model.actorStates.get(actorInstance.getUUID()), actorInstance, actorState, playModel);
        } else {
            ActorModel actorModel = new ActorModel();
            updateActorModel(actorModel, actorInstance, actorState, playModel);
        }
    }

    private void removeActorInstance(FrameModel model, String actorInstanceUUID) {
        model.actorStates.remove(actorInstanceUUID);
    }

    private void updateActorModel(ActorModel actorModel, ActorInstance actorInstance, ActorState actorState, PlayModel playModel) {
        if (actorInstance instanceof PlayerInstance) {
            updatePlayerModel(actorModel, actorInstance, actorState);
        } else if (actorInstance instanceof BallInstance) {
            updateBallModel(actorModel, actorInstance, actorState, playModel);
        } else if (actorInstance instanceof ObstacleInstance) {
            updateObstacleModel(actorModel, actorInstance, actorState);
        }
    }

    private void updatePlayerModel(ActorModel actorModel, ActorInstance actorInstance, ActorState actorState) {
        PlayerState playerState = (PlayerState) actorState;
        PlayerInstance playerInstance = (PlayerInstance) actorInstance;
        actorModel.type.set(ActorModel.Type.PLAYER);
        actorModel.x.set(playerState.getPosition().getX());
        actorModel.y.set(playerState.getPosition().getY());
        if (playerInstance.getTeamSide() == TeamSide.ALLIES) {
            actorModel.color.set(playerInstance.getPlayerCategory().getAllyColor());
        } else {
            actorModel.color.set(playerInstance.getPlayerCategory().getOpponentColor());
        }
        actorModel.orientation.set(playerState.getOrientation());
        actorModel.label.set(playerInstance.getPlayerCategory().getAbbreviation());
        actorModel.hoverText.set(playerInstance.getPlayerCategory().getName());
        actorModel.svgImagePathName.set("/images/player-icon.fxml");
    }

    private void updateBallModel(ActorModel actorModel, ActorInstance actorInstance, ActorState actorState, PlayModel playModel) {
        BallState ballState = (BallState) actorState;
        actorModel.type.set(ActorModel.Type.BALL);
        actorModel.x.set(ballState.getPosition().getX());
        actorModel.y.set(ballState.getPosition().getY());
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
        actorModel.x.set(obstacleState.getPosition().getX());
        actorModel.y.set(obstacleState.getPosition().getY());
        actorModel.hoverText.set(obstacleInstance.getObstacle().getName());
        Obstacle obstacle = obstacleInstance.getObstacle();
        if (obstacle.hasCustomImage()) {
            actorModel.imagePathName.set(imageRepository.get(obstacle.getCustomImageUUID()));
        } else {
            actorModel.builtInImagePathName.set(obstacle.getBuiltInImagePathName());
        }
    }

}
