package ca.ulaval.glo2004.visualigue.domain.sport.playingsurface;

import ca.ulaval.glo2004.visualigue.domain.DomainObject;
import java.util.UUID;

public class PlayingSurface extends DomainObject {

    private Double width = 100.0;
    private Double length = 200.0;
    private PlayingSurfaceUnit widthUnits = PlayingSurfaceUnit.METER;
    private PlayingSurfaceUnit lengthUnits = PlayingSurfaceUnit.METER;
    private UUID customImageUUID;
    private String builtInImagePathName = "/images/built-in-playing-surfaces/generic-playing-surface.png";

    public PlayingSurface() {
        //Required for JAXB instanciation.
    }

    public PlayingSurface(Double width, PlayingSurfaceUnit widthUnits, Double length, PlayingSurfaceUnit lengthUnits) {
        this.width = width;
        this.widthUnits = widthUnits;
        this.length = length;
        this.lengthUnits = lengthUnits;
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

    public UUID getCustomImageUUID() {
        return customImageUUID;
    }

    public void setCustomImageUUID(UUID customImageUUID) {
        this.customImageUUID = customImageUUID;
    }

    public Boolean hasCustomImage() {
        return customImageUUID != null;
    }

    public String getBuiltInImagePathName() {
        return builtInImagePathName;
    }

    public void setBuiltInImagePathName(String builtInImagePathName) {
        this.builtInImagePathName = builtInImagePathName;
    }
}
