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
        addNewActors(model, frame, playModel, frame.getCurrentActorStates());
        updateActors(model, frame, playModel, frame.getCurrentActorStates());
        removeNonPresentActors(model, frame.getCurrentActorStates());
    }

    private void addNewActors(FrameModel model, Frame frame, PlayModel playModel, Map<Actor, ActorState> actorStates) {
        actorStates.entrySet().forEach(entry -> {
            if (!model.actorModels.containsKey(entry.getKey().getUUID())) {
                ActorModel actorModel = createActorModel(frame, playModel, entry.getKey(), entry.getValue());
                model.actorModels.put(entry.getKey().getUUID(), actorModel);
            }
        });
    }

    private ActorModel createActorModel(Frame frame, PlayModel playModel, Actor actor, ActorState actorState) {
        if (actor instanceof PlayerActor) {
            return playerActorModelConverter.convert(frame, (PlayerActor) actor, (PlayerState) actorState);
        } else if (actor instanceof BallActor) {
            return ballActorModelConverter.convert(playModel, (BallActor) actor, (BallState) actorState);
        } else if (actor instanceof ObstacleActor) {
            return obstacleActorModelConverter.convert((ObstacleActor) actor, (ObstacleState) actorState);
        } else {
            throw new RuntimeException("Unsupported Actor subclass.");
        }
    }

    private void updateActors(FrameModel model, Frame frame, PlayModel playModel, Map<Actor, ActorState> actorStates) {
        actorStates.entrySet().forEach(entry -> {
            if (model.actorModels.containsKey(entry.getKey().getUUID())) {
                ActorModel actorModel = model.actorModels.get(entry.getKey().getUUID());
                updateActorModel(actorModel, frame, playModel, entry.getKey(), entry.getValue());
            }
        });
    }

    private void updateActorModel(ActorModel actorModel, Frame frame, PlayModel playModel, Actor actor, ActorState actorState) {
        if (actor instanceof PlayerActor) {
            playerActorModelConverter.update(frame, (PlayerActorModel) actorModel, (PlayerActor) actor, (PlayerState) actorState);
        } else if (actor instanceof BallActor) {
            ballActorModelConverter.update((BallActorModel) actorModel, playModel, (BallActor) actor, (BallState) actorState);
        } else if (actor instanceof ObstacleActor) {
            obstacleActorModelConverter.update((ObstacleActorModel) actorModel, (ObstacleActor) actor, (ObstacleState) actorState);
        }
    }

    private void removeNonPresentActors(FrameModel model, Map<Actor, ActorState> actorStates) {
        model.actorModels.keySet().stream().collect(Collectors.toList()).forEach(actorModelUUID -> {
            if (!actorStates.keySet().stream().anyMatch(actor -> actor.getUUID().equals(actorModelUUID))) {
                model.actorModels.remove(actorModelUUID);
            }
        });
    }

}
