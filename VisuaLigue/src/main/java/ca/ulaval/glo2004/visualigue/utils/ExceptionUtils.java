package ca.ulaval.glo2004.visualigue.utils;

import java.io.PrintWriter;
import java.io.StringWriter;

public class ExceptionUtils {

    public static String getExceptionStackTrace(Exception exception) {
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        exception.printStackTrace(printWriter);
        return stringWriter.toString();
    }
}
