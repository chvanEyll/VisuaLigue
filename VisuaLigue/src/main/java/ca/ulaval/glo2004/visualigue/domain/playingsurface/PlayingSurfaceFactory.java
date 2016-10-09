package ca.ulaval.glo2004.visualigue.domain.playingsurface;

import ca.ulaval.glo2004.visualigue.domain.resource.PersistentResource;
import javax.inject.Singleton;

@Singleton
public class PlayingSurfaceFactory {

    public PlayingSurface create(Double width, Double length, PlayingSurfaceUnit widthUnits, PlayingSurfaceUnit lengthUnits, PersistentResource imageResource) {
        return new PlayingSurface(width, widthUnits, length, lengthUnits, imageResource);
    }
}
