package ca.ulaval.glo2004.visualigue.persistence;

import ca.ulaval.glo2004.visualigue.VisuaLigue;
import ca.ulaval.glo2004.visualigue.domain.image.ImagePersistenceException;
import ca.ulaval.glo2004.visualigue.domain.image.ImageRepository;
import ca.ulaval.glo2004.visualigue.domain.resource.PersistentResource;
import ca.ulaval.glo2004.visualigue.utils.FileUtils;
import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.commons.io.FilenameUtils;

public class FileBasedImageRepository implements ImageRepository {

    private static final String REPOSITORY_NAME = VisuaLigue.getRepositoryDirectory() + "/images";

    private Map<UUID, String> images = new ConcurrentHashMap<>();

    public FileBasedImageRepository() {
        FileUtils.createDirectoryTree(REPOSITORY_NAME);
        Collection<File> files = FileUtils.listFiles(new File(REPOSITORY_NAME), new String[]{}, false);
        files.forEach(file -> {
            images.put(UUID.fromString(file.getName()), file.getAbsolutePath());
        });
    }

    @Override
    public void persist(PersistentResource imageResource) throws ImagePersistenceException {
        if (!imageResource.isPersisted() && imageResource.isSourcePathNameSet()) {
            UUID uuid = UUID.randomUUID();
            String sourceFileExtension = FilenameUtils.getExtension(imageResource.getSourcePathName());
            String destinationPathName = String.format("%s/%s.%s", REPOSITORY_NAME, uuid.toString(), sourceFileExtension);
            try {
                FileUtils.copyFile(new File(imageResource.getSourcePathName()), new File(destinationPathName));
            } catch (IOException ex) {
                throw new ImagePersistenceException(String.format("Failed to persist image '%s' to '%s'.", imageResource.getSourcePathName(), destinationPathName));
            }
            imageResource.setPersistedPathName(destinationPathName);
            imageResource.setPersisted(true);
        }
    }

    @Override
    public void delete(PersistentResource imageResource) {
        if (!imageResource.isResource() && imageResource.isPersistedPathNameSet()) {
            FileUtils.deleteQuietly(new File(imageResource.getPersistedPathName()));
        }
    }

}
