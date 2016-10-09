package ca.ulaval.glo2004.visualigue.utils;

import java.io.File;

public class FilenameUtils extends org.apache.commons.io.FilenameUtils {

    public static String getURIString(String fileName) {
        File file = new File(fileName);
        return file.toURI().toString();
    }
}
