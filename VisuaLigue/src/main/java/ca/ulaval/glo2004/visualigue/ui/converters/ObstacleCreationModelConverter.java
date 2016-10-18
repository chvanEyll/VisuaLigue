package ca.ulaval.glo2004.visualigue.ui.converters;

import ca.ulaval.glo2004.visualigue.domain.image.ImageRepository;
import ca.ulaval.glo2004.visualigue.domain.obstacle.Obstacle;
import ca.ulaval.glo2004.visualigue.ui.models.ObstacleCreationModel;
import javax.inject.Inject;

public class ObstacleCreationModelConverter {

    ImageRepository imageRepository;

    @Inject
    public ObstacleCreationModelConverter(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    public ObstacleCreationModel convert(Obstacle obstacle) {
        ObstacleCreationModel model = new ObstacleCreationModel();
        model.setUUID(obstacle.getUUID());
        model.setIsNew(false);
        model.name.set(obstacle.getName());
        if (obstacle.hasCustomImage()) {
            model.currentImagePathName.set(imageRepository.get(obstacle.getCustomImageUUID()));
        }
        model.builtInImagePathName.set(obstacle.getBuiltInImagePathName());
        return model;
    }
}
