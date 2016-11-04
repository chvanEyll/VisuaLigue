package ca.ulaval.glo2004.visualigue.ui.converters;

import ca.ulaval.glo2004.visualigue.domain.play.actorinstance.ActorInstance;
import ca.ulaval.glo2004.visualigue.domain.play.actorinstance.BallInstance;
import ca.ulaval.glo2004.visualigue.domain.play.actorinstance.ObstacleInstance;
import ca.ulaval.glo2004.visualigue.domain.play.actorinstance.PlayerInstance;
import ca.ulaval.glo2004.visualigue.domain.play.actorstate.ActorState;
import ca.ulaval.glo2004.visualigue.domain.play.frame.Frame;
import ca.ulaval.glo2004.visualigue.ui.models.ActorModel;
import ca.ulaval.glo2004.visualigue.ui.models.FrameModel;
import ca.ulaval.glo2004.visualigue.ui.models.PlayModel;
import java.util.Map;
import java.util.stream.Collectors;
import javax.inject.Inject;

public class FrameModelConverter {

    private ActorModelConverter actorModelConverter;

    @Inject
    public FrameModelConverter(ActorModelConverter actorModelConverter) {
        this.actorModelConverter = actorModelConverter;
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
            actorModelConverter.updatePlayer(frame, actorModel, actorInstance, actorState);
        } else if (actorInstance instanceof BallInstance) {
            actorModelConverter.updateBall(actorModel, playModel, actorState);
        } else if (actorInstance instanceof ObstacleInstance) {
            actorModelConverter.updateObstacle(actorModel, actorInstance, actorState);
        }
    }

}
