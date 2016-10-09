package ca.ulaval.glo2004.visualigue.domain.image;

import java.awt.image.BufferedImage;
import java.io.File;

public interface ImageRepository {

    File persist(BufferedImage image);

    void delete(String filePath);
}
