package ca.ulaval.glo2004.visualigue.persistence;

import ca.ulaval.glo2004.visualigue.VisuaLigue;
import ca.ulaval.glo2004.visualigue.domain.image.ImageRepository;
import ca.ulaval.glo2004.visualigue.utils.FileUtils;
import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.UUID;
import javax.inject.Singleton;

@Singleton
public class FileBasedImageRepository implements ImageRepository {

    private static final String REPOSITORY_NAME = VisuaLigue.getRepositoryDirectory() + "/images";
    private static final String IMAGE_FORMAT = "jpg";

    public FileBasedImageRepository() {
        FileUtils.createDirectoryTree(REPOSITORY_NAME);
    }

    @Override
    public UUID persist(String sourceImagePathName) {
        UUID uuid = UUID.randomUUID();
        String storedFileName = getStoredFileName(uuid);
        try {
            FileUtils.copyFile(new File(sourceImagePathName), new File(storedFileName));
        } catch (IOException ex) {
            throw new UncheckedIOException(String.format("Failed to persist image from '%s' to '%s'.", sourceImagePathName, storedFileName), ex);
        }
        return uuid;
    }

    @Override
    public String get(UUID uuid) {
        return new File(getStoredFileName(uuid)).getAbsolutePath();
    }

    @Override
    public void delete(UUID uuid) {
        FileUtils.deleteQuietly(new File(getStoredFileName(uuid)));
    }

    private String getStoredFileName(UUID uuid) {
        return String.format("%s/%s.%s", REPOSITORY_NAME, uuid, IMAGE_FORMAT);
    }
}