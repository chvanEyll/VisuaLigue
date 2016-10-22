package ca.ulaval.glo2004.visualigue.domain.sport.playingsurface;

public enum PlayingSurfaceUnit {
    METER("mètres", "m"),
    YARDS("verges", "yd"),
    FOOTS("pieds", "ft");

    private final String displayName;
    private final String abbreviation;

    private PlayingSurfaceUnit(String displayName, String abbreviation) {
        this.displayName = displayName;
        this.abbreviation = abbreviation;
    }

    @Override
    public String toString() {
        return displayName;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

}
