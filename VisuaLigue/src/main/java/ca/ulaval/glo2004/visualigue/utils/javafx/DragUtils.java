package ca.ulaval.glo2004.visualigue.utils.javafx;

public class DragUtils {

    private static Object dragSource;
    private static Boolean dragSuccess;

    public static Object getSource() {
        return dragSource;
    }

    public static void setSource(Object dragSource) {
        setResult(false);
        DragUtils.dragSource = dragSource;
    }

    public static void clearSource() {
        DragUtils.dragSource = null;
    }

    public static void setResult(Boolean dragSuccess) {
        DragUtils.dragSuccess = dragSuccess;
    }

    public static Boolean dragSucceeded() {
        return DragUtils.dragSuccess;
    }

}
