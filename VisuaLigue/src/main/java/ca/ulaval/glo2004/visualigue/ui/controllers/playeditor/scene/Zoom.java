package ca.ulaval.glo2004.visualigue.ui.controllers.playeditor.scene;

import java.text.DecimalFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Zoom implements Comparable<Zoom> {

    private static final String PARSE_REGEX = "(\\d+)%?";
    private Double value;
    private final DecimalFormat format = new DecimalFormat("#.#'%'");

    public Zoom(double value) {
        this.value = value;
    }

    public Double getValue() {
        return value;
    }

    public static Zoom percentParse(String value) {
        Pattern pattern = Pattern.compile(PARSE_REGEX);
        Matcher matcher = pattern.matcher(value);
        if (!matcher.matches()) {
            throw new IllegalArgumentException(String.format("Cannot parse '%s' to Zoom.", value));
        } else {
            return new Zoom(Integer.parseInt(matcher.group(1)) / 100.0);
        }
    }

    @Override
    public String toString() {
        return format.format(value * 100);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Zoom)) {
            return false;
        } else if (obj == this) {
            return true;
        }
        Zoom zoom = (Zoom) obj;
        return value.equals(zoom.value);
    }

    @Override
    public int compareTo(Zoom obj) {
        if (obj == this) {
            return 0;
        }
        return value.compareTo(obj.value);
    }

}
