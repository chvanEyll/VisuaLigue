package ca.ulaval.glo2004.visualigue.ui.converters;

import ca.ulaval.glo2004.visualigue.ui.models.actors.ObstacleActorModel;
import ca.ulaval.glo2004.visualigue.ui.models.actors.BallActorModel;
import ca.ulaval.glo2004.visualigue.ui.models.actors.PlayerActorModel;
import ca.ulaval.glo2004.visualigue.ui.models.actors.ActorModel;
import ca.ulaval.glo2004.visualigue.domain.play.actorinstance.ActorInstance;
import ca.ulaval.glo2004.visualigue.domain.play.actorinstance.BallInstance;
import ca.ulaval.glo2004.visualigue.domain.play.actorinstance.ObstacleInstance;
import ca.ulaval.glo2004.visualigue.domain.play.actorinstance.PlayerInstance;
import ca.ulaval.glo2004.visualigue.domain.play.actorstate.ActorState;
import ca.ulaval.glo2004.visualigue.domain.play.actorstate.BallState;
import ca.ulaval.glo2004.visualigue.domain.play.actorstate.ObstacleState;
import ca.ulaval.glo2004.visualigue.domain.play.actorstate.PlayerState;
import ca.ulaval.glo2004.visualigue.domain.play.frame.Frame;
import ca.ulaval.glo2004.visualigue.ui.models.*;
import java.util.Map;
import java.util.stream.Collectors;
import javax.inject.Inject;

public class FrameModelConverter {

    private PlayerActorModelConverter playerActorModelConverter;
    private ObstacleActorModelConverter obstacleActorModelConverter;
    private BallActorModelConverter ballActorModelConverter;

    @Inject
    public FrameModelConverter(PlayerActorModelConverter playerActorModelConverter, ObstacleActorModelConverter obstacleActorModelConverter, BallActorModelConverter ballActorModelConverter) {
        this.playerActorModelConverter = playerActorModelConverter;
        this.obstacleActorModelConverter = obstacleActorModelConverter;
        this.ballActorModelConverter = ballActorModelConverter;
    }

    public FrameModel update(Frame frame, FrameModel frameModel, PlayModel playModel) {
        frameModel.setUUID(frame.getUUID());
        frameModel.setIsNew(false);
        frameModel.time.set(frame.getTime());
        updateActorInstances(frame, frameModel, playModel, frame.getCurrentActorStates());
        removeOldActorInstances(frameModel, frame.getCurrentActorStates());
        return frameModel;
    }

    private void updateActorInstances(Frame frame, FrameModel frameModel, PlayModel playModel, Map<ActorInstance, ActorState> actorStates) {
        actorStates.entrySet().forEach(e -> {
            updateActorInstance(frame, frameModel, playModel, e.getKey(), e.getValue());
        });
    }

    private void updateActorInstance(Frame frame, FrameModel frameModel, PlayModel playModel, ActorInstance actorInstance, ActorState actorState) {
        if (frameModel.actorModels.containsKey(actorInstance.getUUID())) {
            ActorModel actorModel = frameModel.actorModels.get(actorInstance.getUUID());
            updateActorModel(frame, actorModel, playModel, actorInstance, actorState);
        } else {
            ActorModel actorModel = createActorModel(frame, playModel, actorInstance, actorState);
            frameModel.actorModels.put(actorInstance.getUUID(), actorModel);
        }
    }

    private ActorModel createActorModel(Frame frame, PlayModel playModel, ActorInstance actorInstance, ActorState actorState) {
        if (actorInstance instanceof PlayerInstance) {
            return playerActorModelConverter.convert(frame, (PlayerInstance) actorInstance, (PlayerState) actorState);
        } else if (actorInstance instanceof BallInstance) {
            return ballActorModelConverter.convert(playModel, (BallState) actorState);
        } else if (actorInstance instanceof ObstacleInstance) {
            return obstacleActorModelConverter.convert((ObstacleInstance) actorInstance, (ObstacleState) actorState);
        } else {
            throw new RuntimeException("Unsupported ActorInstance subclass.");
        }
    }

    private void updateActorModel(Frame frame, ActorModel actorModel, PlayModel playModel, ActorInstance actorInstance, ActorState actorState) {
        if (actorInstance instanceof PlayerInstance) {
            playerActorModelConverter.update(frame, (PlayerActorModel) actorModel, (PlayerInstance) actorInstance, (PlayerState) actorState);
        } else if (actorInstance instanceof BallInstance) {
            ballActorModelConverter.update((BallActorModel) actorModel, playModel, (BallState) actorState);
        } else if (actorInstance instanceof ObstacleInstance) {
            obstacleActorModelConverter.update((ObstacleActorModel) actorModel, (ObstacleInstance) actorInstance, (ObstacleState) actorState);
        }
    }

    private void removeOldActorInstances(FrameModel frameModel, Map<ActorInstance, ActorState> actorStates) {
        frameModel.actorModels.keySet().stream().collect(Collectors.toList()).forEach(actorModelUUID -> {
            if (!actorStates.keySet().stream().anyMatch(actorInstance -> actorInstance.getUUID().equals(actorModelUUID))) {
                removeActorInstance(frameModel, actorModelUUID);
            }
        });
    }

    private void removeActorInstance(FrameModel frameModel, String actorInstanceUUID) {
        frameModel.actorModels.remove(actorInstanceUUID);
    }

}
