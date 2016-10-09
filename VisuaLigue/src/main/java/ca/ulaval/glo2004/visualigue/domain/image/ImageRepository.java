package ca.ulaval.glo2004.visualigue.domain.image;

import java.awt.image.BufferedImage;

public interface ImageRepository {

    String persist(BufferedImage image);

    void delete(String filePath);
}
