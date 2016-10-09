package ca.ulaval.glo2004.visualigue.persistence;

import ca.ulaval.glo2004.visualigue.VisuaLigue;
import ca.ulaval.glo2004.visualigue.domain.image.ImageRepository;
import ca.ulaval.glo2004.visualigue.utils.FileUtils;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import javax.imageio.ImageIO;

public class FileBasedImageRepository implements ImageRepository {

    private static final String REPOSITORY_NAME = VisuaLigue.getRepositoryDirectory() + "/images";
    private static final String IMAGE_FORMAT = "jpg";

    private Set<String> imagePathNames = new HashSet<>();

    public FileBasedImageRepository() {
        FileUtils.createDirectoryTree(REPOSITORY_NAME);
        Collection<File> files = FileUtils.listFiles(new File(REPOSITORY_NAME), new String[]{}, false);
        files.forEach(file -> {
            imagePathNames.add(file.getAbsolutePath());
        });
    }

    @Override
    public File persist(BufferedImage image) {
        String storedFileName = String.format("%s/%s.%s", REPOSITORY_NAME, UUID.randomUUID(), IMAGE_FORMAT);
        File outputFile = new File(storedFileName);
        try {
            ImageIO.write(image, IMAGE_FORMAT, outputFile);
        } catch (IOException ex) {
            throw new UncheckedIOException(String.format("Failed to persist image to '%s'.", storedFileName), ex);
        }
        imagePathNames.add(storedFileName);
        return outputFile;
    }

    @Override
    public void delete(String filePath) {
        FileUtils.deleteQuietly(new File(filePath));
        imagePathNames.remove(filePath);
    }
}
