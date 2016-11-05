package ca.ulaval.glo2004.visualigue.ui.converters;

import ca.ulaval.glo2004.visualigue.domain.play.actorinstance.ActorInstance;
import ca.ulaval.glo2004.visualigue.domain.play.actorinstance.BallInstance;
import ca.ulaval.glo2004.visualigue.domain.play.actorinstance.ObstacleInstance;
import ca.ulaval.glo2004.visualigue.domain.play.actorinstance.PlayerInstance;
import ca.ulaval.glo2004.visualigue.domain.play.actorstate.ActorState;
import ca.ulaval.glo2004.visualigue.domain.play.actorstate.BallState;
import ca.ulaval.glo2004.visualigue.domain.play.actorstate.ObstacleState;
import ca.ulaval.glo2004.visualigue.domain.play.actorstate.PlayerState;
import ca.ulaval.glo2004.visualigue.domain.play.frame.Frame;
import ca.ulaval.glo2004.visualigue.ui.models.FrameModel;
import ca.ulaval.glo2004.visualigue.ui.models.PlayModel;
import ca.ulaval.glo2004.visualigue.ui.models.actors.ActorModel;
import ca.ulaval.glo2004.visualigue.ui.models.actors.BallActorModel;
import ca.ulaval.glo2004.visualigue.ui.models.actors.ObstacleActorModel;
import ca.ulaval.glo2004.visualigue.ui.models.actors.PlayerActorModel;
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

    public FrameModel convert(Frame frame, PlayModel playModel) {
        FrameModel model = new FrameModel();
        update(model, frame, playModel);
        return model;
    }

    public void update(FrameModel model, Frame frame, PlayModel playModel) {
        model.setUUID(frame.getUUID());
        model.setIsNew(false);
        model.time.set(frame.getTime());
        addNewActorInstances(model, frame, playModel, frame.getCurrentActorStates());
        updateActorInstances(model, frame, playModel, frame.getCurrentActorStates());
        removeNonPresentActorInstances(model, frame.getCurrentActorStates());
    }

    private void addNewActorInstances(FrameModel model, Frame frame, PlayModel playModel, Map<ActorInstance, ActorState> actorStates) {
        actorStates.entrySet().forEach(entry -> {
            if (!model.actorModels.containsKey(entry.getKey().getUUID())) {
                ActorModel actorModel = createActorModel(frame, playModel, entry.getKey(), entry.getValue());
                model.actorModels.put(entry.getKey().getUUID(), actorModel);
            }
        });
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

    private void updateActorInstances(FrameModel model, Frame frame, PlayModel playModel, Map<ActorInstance, ActorState> actorStates) {
        actorStates.entrySet().forEach(entry -> {
            if (model.actorModels.containsKey(entry.getKey().getUUID())) {
                ActorModel actorModel = model.actorModels.get(entry.getKey().getUUID());
                updateActorModel(actorModel, frame, playModel, entry.getKey(), entry.getValue());
            }
        });
    }

    private void updateActorModel(ActorModel actorModel, Frame frame, PlayModel playModel, ActorInstance actorInstance, ActorState actorState) {
        if (actorInstance instanceof PlayerInstance) {
            playerActorModelConverter.update(frame, (PlayerActorModel) actorModel, (PlayerInstance) actorInstance, (PlayerState) actorState);
        } else if (actorInstance instanceof BallInstance) {
            ballActorModelConverter.update((BallActorModel) actorModel, playModel, (BallState) actorState);
        } else if (actorInstance instanceof ObstacleInstance) {
            obstacleActorModelConverter.update((ObstacleActorModel) actorModel, (ObstacleInstance) actorInstance, (ObstacleState) actorState);
        }
    }

    private void removeNonPresentActorInstances(FrameModel model, Map<ActorInstance, ActorState> actorStates) {
        model.actorModels.keySet().stream().collect(Collectors.toList()).forEach(actorModelUUID -> {
            if (!actorStates.keySet().stream().anyMatch(actorInstance -> actorInstance.getUUID().equals(actorModelUUID))) {
                model.actorModels.remove(actorModelUUID);
            }
        });
    }

}
