package ca.ulaval.glo2004.visualigue.ui.converters;

import ca.ulaval.glo2004.visualigue.domain.PlayingSurface;
import ca.ulaval.glo2004.visualigue.domain.Sport;
import ca.ulaval.glo2004.visualigue.ui.models.SportCreationModel;

public class SportCreationModelConverter {

    public SportCreationModel Convert(Sport sport) {
        SportCreationModel model = new SportCreationModel(sport.getName());
        model.builtInIconFileName.set(sport.getBuiltInIconFileName());
        model.playingSurfaceWidth.set(sport.getPlayingSurface().getWidth());
        model.playingSurfaceLength.set(sport.getPlayingSurface().getLength());
        model.playingSurfaceWidthUnits.set(sport.getPlayingSurface().getWidthUnits());
        model.playingSurfaceLengthUnits.set(sport.getPlayingSurface().getLengthUnits());
        model.playingSurfaceImageFileName.set(sport.getPlayingSurface().getImageFileName());
        return model;
    }

    public Sport Convert(SportCreationModel model) {
        Sport sport = new Sport(model.name.get());
        sport.setBuiltInIconFileName(model.builtInIconFileName.get());
        PlayingSurface playingSurface = new PlayingSurface(model.playingSurfaceWidth.get(), model.playingSurfaceLength.get(), model.playingSurfaceImageFileName.get());
        playingSurface.setWidthUnits(model.playingSurfaceWidthUnits.get());
        playingSurface.setLengthUnits(model.playingSurfaceLengthUnits.get());
        sport.setPlayingSurface(playingSurface);
        return sport;
    }
}
