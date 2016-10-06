package ca.ulaval.glo2004.visualigue.ui.converters;

import ca.ulaval.glo2004.visualigue.domain.sport.Sport;
import ca.ulaval.glo2004.visualigue.ui.models.SportCreationModel;

public class SportCreationModelConverter {

    public SportCreationModel convert(Sport sport) {
        SportCreationModel model = new SportCreationModel(sport.getName());
        model.associatedSport = sport;
        model.builtInIconFileName.set(sport.getBuiltInIconFileName());
        model.playingSurfaceWidth.set(sport.getPlayingSurface().getWidth());
        model.playingSurfaceLength.set(sport.getPlayingSurface().getLength());
        model.playingSurfaceWidthUnits.set(sport.getPlayingSurface().getWidthUnits());
        model.playingSurfaceLengthUnits.set(sport.getPlayingSurface().getLengthUnits());
        model.playingSurfaceImageFileName.set(sport.getPlayingSurface().getImageFileName());
        return model;
    }
}
