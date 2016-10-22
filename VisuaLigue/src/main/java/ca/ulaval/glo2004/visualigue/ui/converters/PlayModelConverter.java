package ca.ulaval.glo2004.visualigue.ui.converters;

import ca.ulaval.glo2004.visualigue.domain.image.ImageRepository;
import ca.ulaval.glo2004.visualigue.domain.play.Play;
import ca.ulaval.glo2004.visualigue.ui.models.PlayModel;
import javax.inject.Inject;

public class PlayModelConverter {

    ImageRepository imageRepository;

    @Inject
    public PlayModelConverter(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    public PlayModel convert(Play play) {
        PlayModel model = new PlayModel(play.getTitle());
        model.setUUID(play.getUUID());
        model.setIsNew(false);
        if (play.hasThumbnail()) {
            model.thumbnailImagePathName.set(imageRepository.get(play.getThumbnailImageUUID()));
        }
        model.defaultThumbnailImagePathName.set(play.getDefaultThumbnailImage());
        model.sportUUID.set(play.getSport().getUUID());
        return model;
    }

}
