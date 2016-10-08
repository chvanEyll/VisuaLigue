package ca.ulaval.glo2004.visualigue.domain.playingsurface;

public class PlayingSurface {

    private Double width = 100.0;
    private Double length = 200.0;
    private PlayingSurfaceUnit widthUnits = PlayingSurfaceUnit.METER;
    private PlayingSurfaceUnit lengthUnits = PlayingSurfaceUnit.METER;
    private String imageFileName;

    public PlayingSurface() {

    }

    public PlayingSurface(Double width, PlayingSurfaceUnit widthUnits, Double length, PlayingSurfaceUnit lengthUnits, String imageFileName) {
        this.width = width;
        this.widthUnits = widthUnits;
        this.length = length;
        this.lengthUnits = lengthUnits;
        this.imageFileName = imageFileName;
    }

    public Double getWidth() {
        return width;
    }

    public Double getLength() {
        return length;
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

    public String getImageFileName() {
        return imageFileName;
    }

}
