package ca.ulaval.glo2004.visualigue.utils;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import javax.swing.SortOrder;

public class ListUtils {

    @SuppressWarnings({"rawtypes", "unchecked"})
    public static <T> List<T> sort(List<T> list, Function<T, Comparable> sortFunction, SortOrder sortOrder) {
        if (sortFunction != null) {
            if (sortOrder == SortOrder.ASCENDING) {
                Collections.sort(list, Comparator.<T, Comparable>comparing(sortFunction));
            } else if (sortOrder == SortOrder.DESCENDING) {
                Collections.sort(list, Comparator.<T, Comparable>comparing(sortFunction).reversed());
            }
        }
        return list;
    }

    public static <T extends Comparable> Integer higherIndex(List<T> list, T value) {
        Integer index = null;
        for (Integer i = list.size() - 1; i >= 0; i--) {
            if (ComparableUtils.lessThanOrEqual(list.get(i), value)) {
                break;
            }
            index = i;
        }
        return index;
    }

}
