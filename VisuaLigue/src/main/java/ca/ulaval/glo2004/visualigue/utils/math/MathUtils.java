package ca.ulaval.glo2004.visualigue.utils.math;

public class MathUtils {

    public static <T extends Comparable> Boolean inRange(T value, T minValue, T maxValue) {
        return value.compareTo(minValue) >= 0 && value.compareTo(maxValue) <= 0;
    }

    public static <T extends Comparable> Boolean greaterOrEqual(T value, T minValue) {
        return value.compareTo(minValue) >= 0;
    }

    public static <T extends Comparable> Boolean greaterThan(T value, T minValue) {
        return value.compareTo(minValue) > 0;
    }

    public static <T extends Comparable> Boolean lessOrEqual(T value, T maxValue) {
        return value.compareTo(maxValue) <= 0;
    }

    public static <T extends Comparable> Boolean lessThan(T value, T maxValue) {
        return value.compareTo(maxValue) < 0;
    }
}
