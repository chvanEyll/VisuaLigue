package ca.ulaval.visualigue.ui.converters;

import ca.ulaval.glo2004.visualigue.domain.Sport;
import ca.ulaval.glo2004.visualigue.ui.models.SportModel;

public class SportModelConverter {

    public SportModel Convert(Sport sport) {
        return new SportModel(sport.getName(), sport.getBuiltInIconFileName());
    }
}
