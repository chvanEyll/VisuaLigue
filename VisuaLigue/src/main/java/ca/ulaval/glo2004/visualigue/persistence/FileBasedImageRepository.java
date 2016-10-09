package ca.ulaval.glo2004.visualigue.persistence;

import ca.ulaval.glo2004.visualigue.VisuaLigue;
import ca.ulaval.glo2004.visualigue.domain.image.ImageLoadException;
import ca.ulaval.glo2004.visualigue.domain.image.ImageRepository;
import ca.ulaval.glo2004.visualigue.utils.FileUtils;
import ca.ulaval.glo2004.visualigue.utils.ResourceUtils;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.UUID;
import javax.imageio.ImageIO;
import javax.inject.Singleton;

@Singleton
public class FileBasedImageRepository implements ImageRepository {

    private static final String REPOSITORY_NAME = VisuaLigue.getRepositoryDirectory() + "/images";
    private static final String IMAGE_FORMAT = "jpg";

    public FileBasedImageRepository() {
        FileUtils.createDirectoryTree(REPOSITORY_NAME);
    }

    @Override
    public UUID persist(BufferedImage image) {
        UUID uuid = UUID.randomUUID();
        String storedFileName = getStoredFileName(uuid);
        File outputFile = new File(storedFileName);
        try {
            ImageIO.write(image, IMAGE_FORMAT, outputFile);
        } catch (IOException ex) {
            throw new UncheckedIOException(String.format("Failed to persist image to '%s'.", storedFileName), ex);
        }
        return uuid;
    }

    @Override
    public UUID persistFromResource(String resourceName) {
        UUID uuid = UUID.randomUUID();
        String storedFileName = getStoredFileName(uuid);
        try {
            ResourceUtils.copyTo(resourceName, storedFileName);
        } catch (IOException ex) {
            throw new UncheckedIOException(String.format("Failed to copy image resource from '%s' to '%s'.", resourceName, storedFileName), ex);
        }
        return uuid;
    }

    @Override
    public BufferedImage get(UUID uuid) throws ImageLoadException {
        try {
            return ImageIO.read(new File(getStoredFileName(uuid)));
        } catch (IOException ex) {
            throw new ImageLoadException(String.format("Failed to load image with UUID '%s'.", uuid.toString()), ex);
        }
    }

    @Override
    public void delete(UUID uuid) {
        FileUtils.deleteQuietly(new File(getStoredFileName(uuid)));
    }

    private String getStoredFileName(UUID uuid) {
        return String.format("%s/%s.%s", REPOSITORY_NAME, uuid, IMAGE_FORMAT);
    }
}
