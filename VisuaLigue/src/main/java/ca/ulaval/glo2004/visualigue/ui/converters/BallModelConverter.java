package ca.ulaval.glo2004.visualigue.ui.converters;

import ca.ulaval.glo2004.visualigue.domain.image.ImageRepository;
import ca.ulaval.glo2004.visualigue.domain.sport.ball.Ball;
import ca.ulaval.glo2004.visualigue.ui.models.BallModel;
import javax.inject.Inject;

public class BallModelConverter {

    ImageRepository imageRepository;

    @Inject
    public BallModelConverter(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    public BallModel convert(Ball ball) {
        BallModel model = new BallModel();
        model.setUUID(ball.getUUID());
        model.setIsNew(false);
        model.name.set(ball.getName());
        if (ball.hasCustomImage()) {
            model.imagePathName.set(imageRepository.get(ball.getCustomImageUUID()));
        }
        model.builtInImagePathName.set(ball.getBuiltInImagePathName());
        return model;
    }
}
