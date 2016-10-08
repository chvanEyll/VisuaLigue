package ca.ulaval.glo2004.visualigue.domain.playingsurface;

import javax.inject.Singleton;

@Singleton
public class PlayingSurfaceFactory {

    public PlayingSurface create(Double width, Double length, PlayingSurfaceUnit widthUnits, PlayingSurfaceUnit lengthUnits, String imageFileName) {
        PlayingSurface playingSurface = new PlayingSurface(width, length, imageFileName);
        playingSurface.setWidthUnits(widthUnits);
        playingSurface.setLengthUnits(lengthUnits);
        return playingSurface;
    }
}
