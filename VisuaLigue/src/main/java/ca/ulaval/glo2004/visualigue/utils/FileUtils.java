package ca.ulaval.glo2004.visualigue.utils;

import java.io.File;

public class FileUtils extends org.apache.commons.io.FileUtils {

    public static void createDirectoryTree(String directoryName) {
        File file = new File(directoryName);
        file.mkdir();
    }

}
