package ca.ulaval.glo2004.visualigue.utils;

import ca.ulaval.glo2004.visualigue.domain.resource.LocatedResource;
import ca.ulaval.glo2004.visualigue.utils.FilenameUtils;
import javafx.scene.image.Image;

public class ImageUtils {

    public static Image loadImageFromLocatedResource(LocatedResource locatedResource) {
        return new Image(getImageUrlFromLocatedResource(locatedResource));
    }

    public static String getImageUrlFromLocatedResource(LocatedResource locatedResource) {
        if (locatedResource.isExternalResource()) {
            return FilenameUtils.getURIString(locatedResource.getName());
        } else {
            return locatedResource.getName();
        }
    }

}
