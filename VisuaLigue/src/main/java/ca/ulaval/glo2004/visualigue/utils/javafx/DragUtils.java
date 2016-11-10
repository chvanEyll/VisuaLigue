package ca.ulaval.glo2004.visualigue.utils.javafx;

public class DragUtils {

    private static Object dragSource;

    public static Object getSource() {
        return dragSource;
    }

    public static void setSource(Object dragSource) {
        DragUtils.dragSource = dragSource;
    }

    public static void clearSource() {
        DragUtils.dragSource = null;
    }

}
