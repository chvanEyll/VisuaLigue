package ca.ulaval.glo2004.visualigue.domain.playingsurface;

public enum PlayingSurfaceUnit {
    METER("mètres"),
    YARDS("verges"),
    FOOTS("pieds");

    private final String displayName;

    private PlayingSurfaceUnit(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }

}
