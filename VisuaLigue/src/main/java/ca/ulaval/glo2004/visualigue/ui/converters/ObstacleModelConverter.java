package ca.ulaval.glo2004.visualigue.ui.converters;

import ca.ulaval.glo2004.visualigue.domain.image.ImageRepository;
import ca.ulaval.glo2004.visualigue.domain.obstacle.Obstacle;
import ca.ulaval.glo2004.visualigue.ui.models.ObstacleModel;
import javax.inject.Inject;

public class ObstacleModelConverter {

    private ImageRepository imageRepository;

    @Inject
    public ObstacleModelConverter(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    public ObstacleModel convert(Obstacle obstacle) {
        ObstacleModel model = new ObstacleModel();
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
