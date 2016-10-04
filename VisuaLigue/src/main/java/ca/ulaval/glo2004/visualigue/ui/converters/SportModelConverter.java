package ca.ulaval.glo2004.visualigue.ui.converters;

import ca.ulaval.glo2004.visualigue.domain.Sport;
import ca.ulaval.glo2004.visualigue.ui.models.SportModel;

public class SportModelConverter {

    public SportModel Convert(Sport sport) {
        return new SportModel(sport.getName(), sport.getBuiltInIconFileName());
    }

    public Sport Convert(SportModel model) {
        Sport sport = new Sport(model.name.get());
        sport.setBuiltInIconFileName(model.builtInIconFileName.get());
        return sport;
    }
}
