package ca.ulaval.glo2004.visualigue.ui.converters;

import ca.ulaval.glo2004.visualigue.domain.play.actor.Actor;
import ca.ulaval.glo2004.visualigue.domain.play.actor.BallActor;
import ca.ulaval.glo2004.visualigue.domain.play.actor.ObstacleActor;
import ca.ulaval.glo2004.visualigue.domain.play.actor.PlayerActor;
import ca.ulaval.glo2004.visualigue.domain.play.actorstate.ActorState;
import ca.ulaval.glo2004.visualigue.domain.play.actorstate.BallState;
import ca.ulaval.glo2004.visualigue.domain.play.actorstate.ObstacleState;
import ca.ulaval.glo2004.visualigue.domain.play.actorstate.PlayerState;
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
import java.util.Map;
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
        model.isLocked.set(frame.isLocked());
        model.opacity.set(frame.getOpacity());
        addNewActors(model, frame, playModel, frame.getCurrentActorStates());
        updateActors(model, frame, playModel, frame.getCurrentActorStates());
        removeNonPresentActors(model, frame.getCurrentActorStates());
    }

    private void addNewActors(FrameModel model, Frame frame, PlayModel playModel, Map<Actor, ActorState> actorStates) {
        actorStates.entrySet().forEach(entry -> {
            if (!model.layerModels.containsKey(entry.getKey().getUUID())) {
                ActorLayerModel layerModel = createLayerModel(frame, playModel, entry.getKey(), entry.getValue());
                model.layerModels.put(entry.getKey().getUUID(), layerModel);
            }
        });
    }

    private ActorLayerModel createLayerModel(Frame frame, PlayModel playModel, Actor actor, ActorState actorState) {
        if (actor instanceof PlayerActor) {
            return playerLayerModelConverter.convert(frame, (PlayerActor) actor, (PlayerState) actorState);
        } else if (actor instanceof BallActor) {
            return ballLayerModelConverter.convert(playModel, (BallActor) actor, (BallState) actorState);
        } else if (actor instanceof ObstacleActor) {
            return obstacleLayerModelConverter.convert((ObstacleActor) actor, (ObstacleState) actorState);
        } else {
            throw new RuntimeException("Unsupported Actor subclass.");
        }
    }

    private void updateActors(FrameModel model, Frame frame, PlayModel playModel, Map<Actor, ActorState> actorStates) {
        actorStates.entrySet().forEach(entry -> {
            if (model.layerModels.containsKey(entry.getKey().getUUID())) {
                ActorLayerModel layerModel = model.layerModels.get(entry.getKey().getUUID());
                updateLayerModel(layerModel, frame, playModel, entry.getKey(), entry.getValue());
            }
        });
    }

    private void updateLayerModel(ActorLayerModel layerModel, Frame frame, PlayModel playModel, Actor actor, ActorState actorState) {
        if (actor instanceof PlayerActor) {
            playerLayerModelConverter.update(frame, (PlayerLayerModel) layerModel, (PlayerActor) actor, (PlayerState) actorState);
        } else if (actor instanceof BallActor) {
            ballLayerModelConverter.update((BallLayerModel) layerModel, playModel, (BallActor) actor, (BallState) actorState);
        } else if (actor instanceof ObstacleActor) {
            obstacleLayerModelConverter.update((ObstacleLayerModel) layerModel, (ObstacleActor) actor, (ObstacleState) actorState);
        }
    }

    private void removeNonPresentActors(FrameModel model, Map<Actor, ActorState> actorStates) {
        model.layerModels.keySet().stream().collect(Collectors.toList()).forEach(layerModelUUID -> {
            if (!actorStates.keySet().stream().anyMatch(actor -> actor.getUUID().equals(layerModelUUID))) {
                model.layerModels.remove(layerModelUUID);
            }
        });
    }

}
