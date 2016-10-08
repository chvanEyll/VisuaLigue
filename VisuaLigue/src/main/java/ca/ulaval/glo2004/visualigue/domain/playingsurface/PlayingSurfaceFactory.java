package ca.ulaval.glo2004.visualigue.domain.playingsurface;

import javax.inject.Singleton;

@Singleton
public class PlayingSurfaceFactory {

    public PlayingSurface create(Double width, Double length, PlayingSurfaceUnit widthUnits, PlayingSurfaceUnit lengthUnits, String imageFileName) {
        return new PlayingSurface(width, widthUnits, length, lengthUnits, imageFileName);
    }
}
