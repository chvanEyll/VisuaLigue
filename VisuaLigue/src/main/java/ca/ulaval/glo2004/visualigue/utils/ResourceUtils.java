package ca.ulaval.glo2004.visualigue.utils;

import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.net.URL;

public class ResourceUtils {

    public static String getResourcePathName(String resourceName) throws FileNotFoundException {
        try {
            URL url = getResource(resourceName);
            String t = url.toURI().getPath();
            return t;
        } catch (URISyntaxException e) {
            throw new RuntimeException(String.format("Failed to get resource URI of '%s'.", resourceName), e);
        } catch (Exception e) {
            return "3";
        }
    }

    private static URL getResource(String resourceName) throws FileNotFoundException {
        URL url = ResourceUtils.class.getResource(resourceName);
        if (url == null) {
            throw new FileNotFoundException(String.format("Resource '%s' does not exist.", resourceName));
        }
        return url;
    }

}
