package ca.ulaval.glo2004.visualigue.ui.converters;

import ca.ulaval.glo2004.visualigue.domain.image.ImageRepository;
import ca.ulaval.glo2004.visualigue.domain.sport.Sport;
import ca.ulaval.glo2004.visualigue.ui.models.SportListItemModel;
import javax.inject.Inject;

public class SportListItemModelConverter {

    private ImageRepository imageRepository;

    @Inject
    public SportListItemModelConverter(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    public SportListItemModel convert(Sport sport) {
        SportListItemModel model = new SportListItemModel();
        update(model, sport);
        return model;
    }

    public void update(SportListItemModel model, Sport sport) {
        model.setUUID(sport.getUUID());
        model.setIsNew(false);
        model.name.set(sport.getName());
        if (sport.hasCustomIcon()) {
            model.customIconPathName.set(imageRepository.get(sport.getCustomIconUUID()));
        }
        model.builtInIconPathName.set(sport.getBuiltInIconPathName());
    }

}
