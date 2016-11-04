package ca.ulaval.glo2004.visualigue.ui.converters;

import ca.ulaval.glo2004.visualigue.domain.image.ImageRepository;
import ca.ulaval.glo2004.visualigue.domain.obstacle.Obstacle;
import ca.ulaval.glo2004.visualigue.domain.play.actorinstance.ActorInstance;
import ca.ulaval.glo2004.visualigue.domain.play.actorinstance.ObstacleInstance;
import ca.ulaval.glo2004.visualigue.domain.play.actorinstance.PlayerInstance;
import ca.ulaval.glo2004.visualigue.domain.play.actorinstance.TeamSide;
import ca.ulaval.glo2004.visualigue.domain.play.actorstate.ActorState;
import ca.ulaval.glo2004.visualigue.domain.play.actorstate.BallState;
import ca.ulaval.glo2004.visualigue.domain.play.actorstate.ObstacleState;
import ca.ulaval.glo2004.visualigue.domain.play.actorstate.PlayerState;
import ca.ulaval.glo2004.visualigue.domain.play.frame.Frame;
import ca.ulaval.glo2004.visualigue.ui.models.ActorModel;
import ca.ulaval.glo2004.visualigue.ui.models.ObstacleModel;
import ca.ulaval.glo2004.visualigue.ui.models.PlayModel;
import ca.ulaval.glo2004.visualigue.ui.models.PlayerCategoryModel;
import javax.inject.Inject;

public class ActorModelConverter {

    private ImageRepository imageRepository;

    @Inject
    public ActorModelConverter(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    public ActorModel convertPlayer(PlayerCategoryModel playerCategoryModel, TeamSide teamSide) {
        ActorModel actorModel = new ActorModel();
        actorModel.type.set(ActorModel.Type.PLAYER);
        if (teamSide == TeamSide.ALLIES) {
            actorModel.color.set(playerCategoryModel.allyPlayerColor.get());
        } else {
            actorModel.color.set(playerCategoryModel.opponentPlayerColor.get());
        }
        actorModel.label.set(playerCategoryModel.abbreviation.get());
        return actorModel;
    }

    public ActorModel convertObstacle(ObstacleModel obstacleModel) {
        ActorModel actorModel = new ActorModel();
        actorModel.type.set(ActorModel.Type.OBSTACLE);
        actorModel.imagePathName.set(obstacleModel.currentImagePathName.get());
        actorModel.builtInImagePathName.set(obstacleModel.builtInImagePathName.get());
        return actorModel;
    }

    public void updatePlayer(Frame frame, ActorModel actorModel, ActorInstance actorInstance, ActorState actorState) {
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

    public void updateBall(ActorModel actorModel, PlayModel playModel, ActorState actorState) {
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

    public void updateObstacle(ActorModel actorModel, ActorInstance actorInstance, ActorState actorState) {
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
