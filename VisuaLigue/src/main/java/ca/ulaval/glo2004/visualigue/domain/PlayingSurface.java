package ca.ulaval.glo2004.visualigue.domain;

public class PlayingSurface {

    private Double width = 100.0;
    private Double length = 200.0;
    private PlayingSurfaceUnit widthUnits = PlayingSurfaceUnit.METER;
    private PlayingSurfaceUnit lengthUnits = PlayingSurfaceUnit.METER;
    private String imageFileName;

    public PlayingSurface() {
    }

    public PlayingSurface(Double width, Double length, String imageFileName) {
        this.width = width;
        this.length = length;
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
