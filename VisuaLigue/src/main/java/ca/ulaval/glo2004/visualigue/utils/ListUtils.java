package ca.ulaval.glo2004.visualigue.utils;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import javax.swing.SortOrder;

public class ListUtils {

    @SuppressWarnings({"rawtypes", "unchecked"})
    public static <T> List<T> sort(List<T> sports, Function<T, Comparable> sortFunction, SortOrder sortOrder) {
        if (sortFunction != null) {
            if (sortOrder == SortOrder.ASCENDING) {
                Collections.sort(sports, Comparator.<T, Comparable>comparing(sortFunction));
            } else if (sortOrder == SortOrder.DESCENDING) {
                Collections.sort(sports, Comparator.<T, Comparable>comparing(sortFunction).reversed());
            }
        }
        return sports;
    }

}
