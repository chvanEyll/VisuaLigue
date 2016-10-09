package ca.ulaval.glo2004.visualigue.domain.playingsurface;

import ca.ulaval.glo2004.visualigue.domain.DomainObject;
import ca.ulaval.glo2004.visualigue.domain.resource.LocatedResource;

public class PlayingSurface extends DomainObject {

    private Double width = 100.0;
    private Double length = 200.0;
    private PlayingSurfaceUnit widthUnits = PlayingSurfaceUnit.METER;
    private PlayingSurfaceUnit lengthUnits = PlayingSurfaceUnit.METER;
    private LocatedResource imageResource = new LocatedResource();

    public PlayingSurface() {

    }

    public PlayingSurface(Double width, PlayingSurfaceUnit widthUnits, Double length, PlayingSurfaceUnit lengthUnits, LocatedResource imageRef) {
        this.width = width;
        this.widthUnits = widthUnits;
        this.length = length;
        this.lengthUnits = lengthUnits;
        this.imageResource = imageRef;
    }

    public Double getWidth() {
        return width;
    }

    public void setWidth(Double width) {
        this.width = width;
    }

    public Double getLength() {
        return length;
    }

    public void setLength(Double length) {
        this.length = length;
    }

    public PlayingSurfaceUnit getWidthUnits() {
        return widthUnits;
    }

    public void setWidthUnits(PlayingSurfaceUnit widthUnits) {
        this.widthUnits = widthUnits;
    }

    public PlayingSurfaceUnit getLengthUnits() {
        return lengthUnits;
    }

    public void setLengthUnits(PlayingSurfaceUnit lengthUnits) {
        this.lengthUnits = lengthUnits;
    }

    public LocatedResource getImageResource() {
        return imageResource;
    }

    public void setImageResource(LocatedResource imageResource) {
        this.imageResource = imageResource;
    }

}
