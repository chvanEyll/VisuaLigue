package ca.ulaval.glo2004.visualigue.ui.converters;

import ca.ulaval.glo2004.visualigue.domain.play.actor.ActorInstance;
import ca.ulaval.glo2004.visualigue.domain.play.actor.BallActor;
import ca.ulaval.glo2004.visualigue.domain.play.actor.ObstacleActor;
import ca.ulaval.glo2004.visualigue.domain.play.actor.PlayerActor;
import ca.ulaval.glo2004.visualigue.domain.play.frame.Frame;
import ca.ulaval.glo2004.visualigue.ui.converters.layers.BallLayerModelConverter;
import ca.ulaval.glo2004.visualigue.ui.converters.layers.ObstacleLayerModelConverter;
import ca.ulaval.glo2004.visualigue.ui.converters.layers.PlayeLayerModelConverter;
import ca.ulaval.glo2004.visualigue.ui.models.FrameModel;
import ca.ulaval.glo2004.visualigue.ui.models.PlayModel;
import ca.ulaval.glo2004.visualigue.ui.models.layers.ActorLayerModel;
import ca.ulaval.glo2004.visualigue.ui.models.layers.BallLayerModel;
import ca.ulaval.glo2004.visualigue.ui.models.layers.ObstacleLayerModel;
import ca.ulaval.glo2004.visualigue.ui.models.layers.PlayerLayerModel;
import java.util.Set;
import java.util.stream.Collectors;
import javax.inject.Inject;

public class FrameModelConverter {

    private PlayeLayerModelConverter playerLayerModelConverter;
    private ObstacleLayerModelConverter obstacleLayerModelConverter;
    private BallLayerModelConverter ballLayerModelConverter;

    @Inject
    public FrameModelConverter(PlayeLayerModelConverter playerLayerModelConverter, ObstacleLayerModelConverter obstacleLayerModelConverter, BallLayerModelConverter ballLayerModelConverter) {
        this.playerLayerModelConverter = playerLayerModelConverter;
        this.obstacleLayerModelConverter = obstacleLayerModelConverter;
        this.ballLayerModelConverter = ballLayerModelConverter;
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
            if (!model.layerModels.containsKey(actorInstance.hashCode())) {
                ActorLayerModel layerModel = createLayerModel(playModel, actorInstance);
                model.layerModels.put(actorInstance.hashCode(), layerModel);
            }
        });
    }

    private ActorLayerModel createLayerModel(PlayModel playModel, ActorInstance actorInstance) {
        if (actorInstance.getActor() instanceof PlayerActor) {
            return playerLayerModelConverter.convert(actorInstance);
        } else if (actorInstance.getActor() instanceof BallActor) {
            return ballLayerModelConverter.convert(playModel, actorInstance);
        } else if (actorInstance.getActor() instanceof ObstacleActor) {
            return obstacleLayerModelConverter.convert(actorInstance);
        } else {
            throw new RuntimeException("Unsupported Actor subclass.");
        }
    }

    private void updateActors(FrameModel model, PlayModel playModel, Set<ActorInstance> actorInstances) {
        actorInstances.forEach(actorInstance -> {
            if (model.layerModels.containsKey(actorInstance.hashCode())) {
                ActorLayerModel layerModel = model.layerModels.get(actorInstance.hashCode());
                updateLayerModel(layerModel, playModel, actorInstance);
            }
        });
    }

    private void updateLayerModel(ActorLayerModel layerModel, PlayModel playModel, ActorInstance actorInstance) {
        if (actorInstance.getActor() instanceof PlayerActor) {
            playerLayerModelConverter.update((PlayerLayerModel) layerModel, actorInstance);
        } else if (actorInstance.getActor() instanceof BallActor) {
            ballLayerModelConverter.update((BallLayerModel) layerModel, playModel, actorInstance);
        } else if (actorInstance.getActor() instanceof ObstacleActor) {
            obstacleLayerModelConverter.update((ObstacleLayerModel) layerModel, actorInstance);
        }
    }

    private void removeNonPresentActors(FrameModel model, Set<ActorInstance> actorInstances) {
        model.layerModels.keySet().stream().collect(Collectors.toList()).forEach(hashCode -> {
            if (!actorInstances.stream().anyMatch(actorInstance -> actorInstance.hashCode() == hashCode)) {
                model.layerModels.remove(hashCode);
            }
        });
    }

}
