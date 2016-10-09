package ca.ulaval.glo2004.visualigue.persistence.marshalling;

import ca.ulaval.glo2004.visualigue.utils.FileUtils;
import java.io.*;
import java.util.Collection;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import javax.inject.Inject;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import org.apache.commons.io.IOUtils;

public class XmlRepositoryMarshaller<T> extends XmlMarshaller<T> {

    private String repositoryName;

    @Inject
    public XmlRepositoryMarshaller(final Class<T> type, final String pathName) {
        super(type);
        init(pathName);
    }

    @Inject
    public XmlRepositoryMarshaller(final Marshaller marshaller, final Unmarshaller unmarshaller, final String pathName) {
        super(marshaller, unmarshaller);
        init(pathName);
    }

    private void init(final String respositoryName) {
        this.repositoryName = respositoryName;
    }

    public synchronized Map<UUID, T> unmarshalAll() {
        Map<UUID, T> objects = new ConcurrentHashMap<>();
        Collection<File> files = FileUtils.listFiles(new File(repositoryName), new String[]{}, false);
        files.forEach(file -> {
            objects.put(UUID.fromString(file.getName()), unmarshal(file));
        });
        return objects;
    }

    private synchronized T unmarshal(File file) {
        T unmarshalledObject;
        InputStream inputStream = null;
        try {
            inputStream = ca.ulaval.glo2004.visualigue.utils.FileUtils.openInputStream(file);
            return (T) super.unmarshal(inputStream);
        } catch (IOException e) {
            throw new UncheckedIOException(String.format("An I/O exception occured while trying to read file '%s'.", repositoryName), e);
        } finally {
            IOUtils.closeQuietly(inputStream);
        }
    }

    public synchronized void marshal(T object, UUID uuid) {
        OutputStream outputStream = null;
        try {
            File file = FileUtils.createFile(String.format("%s/%s.xml", repositoryName, uuid));
            outputStream = FileUtils.openOutputStream(file);
            super.marshal(object, outputStream);
        } catch (IOException e) {
            throw new UncheckedIOException(String.format("An I/O exception occured while trying to write file '%s'.", repositoryName), e);
        } finally {
            IOUtils.closeQuietly(outputStream);
        }
    }

    public String getResourceName() {
        return repositoryName;
    }
}
