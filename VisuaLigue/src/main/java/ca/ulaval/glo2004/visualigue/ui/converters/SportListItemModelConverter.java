package ca.ulaval.glo2004.visualigue.ui.converters;

import ca.ulaval.glo2004.visualigue.domain.Sport;
import ca.ulaval.glo2004.visualigue.ui.models.SportListItemModel;

public class SportListItemModelConverter {

    public SportListItemModel Convert(Sport sport) {
        SportListItemModel model = new SportListItemModel(sport, sport.getName());
        model.builtInIconFileName.set(sport.getBuiltInIconFileName());
        return model;
    }

}
