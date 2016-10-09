package ca.ulaval.glo2004.visualigue.utils;

import java.io.*;
import org.apache.commons.io.IOUtils;

public class ResourceUtils {

    public static void copyTo(String resourceName, String destination) throws IOException {
        InputStream inputStream = ResourceUtils.class.getResourceAsStream(resourceName);
        File file = FileUtils.createFile(destination);
        OutputStream outputStream = new FileOutputStream(file);
        IOUtils.copy(inputStream, outputStream);
        inputStream.close();
        outputStream.close();
    }
}
