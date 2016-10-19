package ca.ulaval.glo2004.visualigue.ui.converters;

import ca.ulaval.glo2004.visualigue.domain.image.ImageRepository;
import ca.ulaval.glo2004.visualigue.domain.play.Play;
import ca.ulaval.glo2004.visualigue.ui.models.PlayListItemModel;
import javax.inject.Inject;

public class PlayListItemModelConverter {

    ImageRepository imageRepository;

    @Inject
    public PlayListItemModelConverter(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    public PlayListItemModel convert(Play play) {
        PlayListItemModel model = new PlayListItemModel(play.getTitle());
        model.setUUID(play.getUUID());
        model.setIsNew(false);
        if (play.hasThumbnail()) {
            model.thumbnailImagePathName.set(imageRepository.get(play.getThumbnailImageUUID()));
        }
        model.defaultThumbnailImagePathName.set(play.getDefaultThumbnailImage());
        return model;
    }

}
