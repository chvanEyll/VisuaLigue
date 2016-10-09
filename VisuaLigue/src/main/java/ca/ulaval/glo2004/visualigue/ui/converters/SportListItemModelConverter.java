package ca.ulaval.glo2004.visualigue.ui.converters;

import ca.ulaval.glo2004.visualigue.domain.sport.Sport;
import ca.ulaval.glo2004.visualigue.ui.models.SportListItemModel;

public class SportListItemModelConverter {

    public SportListItemModel convert(Sport sport) {
        SportListItemModel model = new SportListItemModel(sport.getName());
        model.setUUID(sport.getUUID());
        model.setIsNew(false);
        model.builtInIconPathName.set(sport.getBuiltInIconPathName());
        return model;
    }

}
