package ca.ulaval.glo2004.visualigue.utils;

import java.io.FileNotFoundException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

public class ResourceUtils {

    public static String getResourcePathName(String resourceName) throws FileNotFoundException {
        return getResourceURI(resourceName).toString();
    }

    public static URI getResourceURI(String resourceName) throws FileNotFoundException {
        try {
            URL url = getResourceURL(resourceName);
            return url.toURI();
        } catch (URISyntaxException e) {
            throw new RuntimeException(String.format("Failed to get resource URI of '%s'.", resourceName), e);
        }
    }

    public static URL getResourceURL(String resourceName) throws FileNotFoundException {
        URL url = ResourceUtils.class.getResource(resourceName);
        if (url == null) {
            throw new FileNotFoundException(String.format("Resource '%s' does not exist.", resourceName));
        }
        return url;
    }

}
