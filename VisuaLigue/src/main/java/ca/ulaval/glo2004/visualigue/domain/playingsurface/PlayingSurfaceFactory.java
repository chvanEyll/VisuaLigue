package ca.ulaval.glo2004.visualigue.domain.playingsurface;

import ca.ulaval.glo2004.visualigue.domain.resource.LocatedResource;
import javax.inject.Singleton;

@Singleton
public class PlayingSurfaceFactory {

    public PlayingSurface create(Double width, Double length, PlayingSurfaceUnit widthUnits, PlayingSurfaceUnit lengthUnits, LocatedResource imageResource) {
        return new PlayingSurface(width, widthUnits, length, lengthUnits, imageResource);
    }
}
