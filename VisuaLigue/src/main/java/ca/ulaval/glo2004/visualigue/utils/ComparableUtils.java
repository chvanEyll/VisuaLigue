package ca.ulaval.glo2004.visualigue.utils;

public class ComparableUtils {

    public static <T> Boolean lessThan(Comparable<T> value1, T value2) {
        return value1.compareTo(value2) < 0;
    }

    public static <T> Boolean lessThanOrEqual(Comparable<T> value1, T value2) {
        return value1.compareTo(value2) < 0 || value1.compareTo(value2) == 0;
    }

    public static <T> Boolean greaterThan(Comparable<T> value1, T value2) {
        return value1.compareTo(value2) > 0;
    }

    public static <T> Boolean greaterThanOrEqual(Comparable<T> value1, T value2) {
        return value1.compareTo(value2) > 0 || value1.compareTo(value2) == 0;
    }
}
