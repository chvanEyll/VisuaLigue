/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visualigue.domain;

/**
 *
 * @author Guillaume
 */
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
