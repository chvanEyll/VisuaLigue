package ca.ulaval.glo2004.visualigue.utils;

import java.io.File;

public class FileUtils extends org.apache.commons.io.FileUtils {

    public static void createDirectoryTree(String directoryName) {
        File file = new File(directoryName);
        file.mkdir();
    }

    public static void createDirectoryTree(File file) {
        file.getParentFile().mkdirs();
    }

    public static File createFile(String pathName) {
        File file = new File(pathName);
        FileUtils.createDirectoryTree(file);
        return file;
    }

}
