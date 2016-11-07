package ca.ulaval.glo2004.visualigue.ui.converters;

import ca.ulaval.glo2004.visualigue.domain.image.ImageRepository;
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

    public BallActorModel convert(PlayModel playModel, BallActor ballActor, BallState ballState) {
        BallActorModel model = new BallActorModel();
        update(model, playModel, ballActor, ballState);
        return model;
    }

    public void update(BallActorModel model, PlayModel playModel, BallActor ballActor, BallState ballState) {
        model.setUUID(ballActor.getUUID());
        model.position.set(ballState.getPosition());
        model.hoverText.set(playModel.ballModel.name.get());
        if (playModel.ballModel.imagePathName.isNotEmpty().get()) {
            model.imagePathName.set(playModel.ballModel.imagePathName.get());
        } else {
            model.builtInImagePathName.set(playModel.ballModel.builtInImagePathName.get());
        }
    }
}
