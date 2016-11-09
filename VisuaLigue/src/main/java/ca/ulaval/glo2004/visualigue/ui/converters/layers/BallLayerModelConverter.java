package ca.ulaval.glo2004.visualigue.ui.converters.layers;

import ca.ulaval.glo2004.visualigue.domain.image.ImageRepository;
import ca.ulaval.glo2004.visualigue.domain.play.actor.BallActor;
import ca.ulaval.glo2004.visualigue.domain.play.actorstate.BallState;
import ca.ulaval.glo2004.visualigue.ui.models.BallModel;
import ca.ulaval.glo2004.visualigue.ui.models.PlayModel;
import ca.ulaval.glo2004.visualigue.ui.models.layers.BallLayerModel;
import javax.inject.Inject;

public class BallLayerModelConverter {

    private ImageRepository imageRepository;

    @Inject
    public BallLayerModelConverter(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    public BallLayerModel convert(BallModel ballModel) {
        BallLayerModel model = new BallLayerModel();
        model.imagePathName.set(ballModel.imagePathName.get());
        model.builtInImagePathName.set(ballModel.builtInImagePathName.get());
        return model;
    }

    public BallLayerModel convert(PlayModel playModel, BallActor ballActor, BallState ballState) {
        BallLayerModel model = new BallLayerModel();
        update(model, playModel, ballActor, ballState);
        return model;
    }

    public void update(BallLayerModel model, PlayModel playModel, BallActor ballActor, BallState ballState) {
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
