package ca.ulaval.glo2004.visualigue.domain.image;

import java.awt.image.BufferedImage;
import java.util.UUID;

public interface ImageRepository {

    UUID persist(BufferedImage image);

    UUID persistFromResource(String resourceName);

    BufferedImage get(UUID uuid) throws ImageLoadException;

    void delete(UUID uuid);
}
