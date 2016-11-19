package ca.ulaval.glo2004.visualigue.ui.converters;

import ca.ulaval.glo2004.visualigue.domain.play.frame.ActorInstance;
import ca.ulaval.glo2004.visualigue.domain.play.actor.BallActor;
import ca.ulaval.glo2004.visualigue.domain.play.actor.ObstacleActor;
import ca.ulaval.glo2004.visualigue.domain.play.actor.PlayerActor;
import ca.ulaval.glo2004.visualigue.domain.play.frame.Frame;
import ca.ulaval.glo2004.visualigue.ui.converters.actor.BallActorModelConverter;
import ca.ulaval.glo2004.visualigue.ui.converters.actor.ObstacleActorModelConverter;
import ca.ulaval.glo2004.visualigue.ui.converters.actor.PlayerActorModelConverter;
import ca.ulaval.glo2004.visualigue.ui.models.FrameModel;
import ca.ulaval.glo2004.visualigue.ui.models.PlayModel;
import ca.ulaval.glo2004.visualigue.ui.models.actors.ActorModel;
import ca.ulaval.glo2004.visualigue.ui.models.actors.BallActorModel;
import ca.ulaval.glo2004.visualigue.ui.models.actors.ObstacleActorModel;
import ca.ulaval.glo2004.visualigue.ui.models.actors.PlayerActorModel;
import java.util.Set;
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
        addNewActors(model, playModel, frame.getActorInstances());
        updateActors(model, playModel, frame.getActorInstances());
        removeNonPresentActors(model, frame.getActorInstances());
    }

    private void addNewActors(FrameModel model, PlayModel playModel, Set<ActorInstance> actorInstances) {
        actorInstances.forEach(actorInstance -> {
            if (!model.actorModels.containsKey(actorInstance.hashCode())) {
                ActorModel actorModel = createActorModel(playModel, actorInstance);
                model.actorModels.put(actorInstance.hashCode(), actorModel);
            }
        });
    }

    private ActorModel createActorModel(PlayModel playModel, ActorInstance actorInstance) {
        if (actorInstance.getActor() instanceof PlayerActor) {
            return playerActorModelConverter.convert(actorInstance);
        } else if (actorInstance.getActor() instanceof BallActor) {
            return ballActorModelConverter.convert(playModel, actorInstance);
        } else if (actorInstance.getActor() instanceof ObstacleActor) {
            return obstacleActorModelConverter.convert(actorInstance);
        } else {
            throw new RuntimeException("Unsupported Actor subclass.");
        }
    }

    private void updateActors(FrameModel model, PlayModel playModel, Set<ActorInstance> actorInstances) {
        actorInstances.forEach(actorInstance -> {
            if (model.actorModels.containsKey(actorInstance.hashCode())) {
                ActorModel actorModel = model.actorModels.get(actorInstance.hashCode());
                updateActorModel(actorModel, playModel, actorInstance);
            }
        });
    }

    private void updateActorModel(ActorModel actorModel, PlayModel playModel, ActorInstance actorInstance) {
        if (actorInstance.getActor() instanceof PlayerActor) {
            playerActorModelConverter.update((PlayerActorModel) actorModel, actorInstance);
        } else if (actorInstance.getActor() instanceof BallActor) {
            ballActorModelConverter.update((BallActorModel) actorModel, playModel, actorInstance);
        } else if (actorInstance.getActor() instanceof ObstacleActor) {
            obstacleActorModelConverter.update((ObstacleActorModel) actorModel, actorInstance);
        }
    }

    private void removeNonPresentActors(FrameModel model, Set<ActorInstance> actorInstances) {
        model.actorModels.keySet().stream().collect(Collectors.toList()).forEach(hashCode -> {
            if (!actorInstances.stream().anyMatch(actorInstance -> actorInstance.hashCode() == hashCode)) {
                model.actorModels.remove(hashCode);
            }
        });
    }

}
