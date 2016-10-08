package ca.ulaval.glo2004.visualigue.persistence.image;

import ca.ulaval.glo2004.visualigue.VisuaLigue;
import ca.ulaval.glo2004.visualigue.domain.Image.ImagePersistenceException;
import ca.ulaval.glo2004.visualigue.domain.Image.ImageRepository;
import ca.ulaval.glo2004.visualigue.domain.Image.PersistentImageRef;
import ca.ulaval.glo2004.visualigue.utils.FileUtils;
import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.commons.io.FilenameUtils;

public class FileBasedImageRepository implements ImageRepository {

    private static final String PHOTO_DIRECTORY_NAME = VisuaLigue.getRepositoryDirectory() + "/images";

    private Map<UUID, String> images = new ConcurrentHashMap<>();
    private final File photoDirectory;

    public FileBasedImageRepository() {
        FileUtils.createDirectoryTree(PHOTO_DIRECTORY_NAME);
        photoDirectory = new File(PHOTO_DIRECTORY_NAME);
        Collection<File> files = FileUtils.listFiles(photoDirectory, new String[]{}, false);
        files.forEach(file -> {
            images.put(UUID.fromString(file.getName()), file.getAbsolutePath());
        });
    }

    @Override
    public void persist(PersistentImageRef imageRef) throws ImagePersistenceException {
        if (!imageRef.isStored() && imageRef.getSourceImagePathName() != null) {
            UUID uuid = UUID.randomUUID();
            String sourceFileExtension = FilenameUtils.getExtension(imageRef.getSourceImagePathName());
            try {
                FileUtils.copyFile(new File(imageRef.getSourceImagePathName()), new File(String.format("%s/%s.%s", PHOTO_DIRECTORY_NAME, uuid.toString(), sourceFileExtension)));
            } catch (IOException ex) {
                throw new ImagePersistenceException(String.format("Failed to persist image '%s' to '%s'.", imageRef.getSourceImagePathName(), imageRef.getStoredImagePathName()));
            }
            FileUtils.deleteQuietly(new File(imageRef.getStoredImagePathName()));
            imageRef.setStored(true);
        }
    }

}
