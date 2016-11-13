package ca.ulaval.glo2004.visualigue.ui.converters.actor;

import ca.ulaval.glo2004.visualigue.domain.image.ImageRepository;
import ca.ulaval.glo2004.visualigue.domain.play.actor.ActorInstance;
import ca.ulaval.glo2004.visualigue.domain.play.actor.BallActor;
import ca.ulaval.glo2004.visualigue.domain.play.actorstate.BallState;
import ca.ulaval.glo2004.visualigue.ui.models.BallModel;
import ca.ulaval.glo2004.visualigue.ui.models.PlayModel;
import ca.ulaval.glo2004.visualigue.ui.models.actors.BallActorModel;
import javax.inject.Inject;

public class BallActorModelConverter {

    private ImageRepository imageRepository;

    @Inject
    public BallActorModelConverter(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    public BallActorModel convert(BallModel ballModel) {
        BallActorModel model = new BallActorModel();
        model.imagePathName.set(ballModel.imagePathName.get());
        model.builtInImagePathName.set(ballModel.builtInImagePathName.get());
        return model;
    }

    public BallActorModel convert(PlayModel playModel, ActorInstance actorInstance) {
        BallActorModel model = new BallActorModel();
        update(model, playModel, actorInstance);
        return model;
    }

    public void update(BallActorModel model, PlayModel playModel, ActorInstance actorInstance) {
        BallActor ballActor = (BallActor) actorInstance.getActor();
        BallState ballState = (BallState) actorInstance.getActorState();
        model.setUUID(ballActor.getUUID());
        model.instanceID.set(actorInstance.getInstanceID());
        model.isLocked.set(ballState.isLocked());
        model.opacity.set(ballState.getOpacity());
        model.visible.set(ballState.isVisible());
        model.zOrder.set(ballState.getZOrder());
        model.showLabel.set(ballState.getShowLabel());
        model.position.set(ballState.getPosition());
        model.hoverText.set(playModel.ballModel.name.get());
        if (playModel.ballModel.imagePathName.isNotEmpty().get()) {
            model.imagePathName.set(playModel.ballModel.imagePathName.get());
        } else {
            model.builtInImagePathName.set(playModel.ballModel.builtInImagePathName.get());
        }
        if (ballState.hasOwnerPlayer()) {
            model.playerOwnerUUID.set(ballState.getOwnerPlayer().getUUID());
        }
    }
}
