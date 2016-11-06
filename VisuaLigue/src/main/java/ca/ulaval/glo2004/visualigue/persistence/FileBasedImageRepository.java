package ca.ulaval.glo2004.visualigue.persistence;

import ca.ulaval.glo2004.visualigue.VisuaLigueFX;
import ca.ulaval.glo2004.visualigue.domain.image.ImageRepository;
import ca.ulaval.glo2004.visualigue.utils.FileUtils;
import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.Collection;
import java.util.UUID;
import javax.inject.Singleton;

@Singleton
public class FileBasedImageRepository implements ImageRepository {

    private static final String REPOSITORY_NAME = VisuaLigueFX.getRepositoryDirectory() + "/images";
    private static final String IMAGE_FORMAT = "jpg";

    public FileBasedImageRepository() {
        FileUtils.createDirectoryTree(REPOSITORY_NAME);
    }

    @Override
    public String persist(String sourceImagePathName) {
        String uuid = UUID.randomUUID().toString();
        String storedFileName = getStoredFileName(uuid);
        try {
            FileUtils.copyFile(new File(sourceImagePathName), new File(storedFileName));
        } catch (IOException ex) {
            throw new UncheckedIOException(String.format("Failed to persist image from '%s' to '%s'.", sourceImagePathName, storedFileName), ex);
        }
        return uuid;
    }

    @Override
    public String replace(String oldUUID, String newSourceImagePathName) {
        if (oldUUID != null) {
            delete(oldUUID);
        }
        return persist(newSourceImagePathName);
    }

    @Override
    public String get(String uuid) {
        return new File(getStoredFileName(uuid)).getAbsolutePath();
    }

    @Override
    public void delete(String uuid) {
        FileUtils.deleteQuietly(new File(getStoredFileName(uuid)));
    }

    @Override
    public void clear() {
        Collection<File> files = FileUtils.listFiles(new File(REPOSITORY_NAME), null, false);
        files.forEach(file -> {
            FileUtils.deleteQuietly(file);
        });
    }

    private String getStoredFileName(String uuid) {
        return String.format("%s/%s.%s", REPOSITORY_NAME, uuid, IMAGE_FORMAT);
    }
}
