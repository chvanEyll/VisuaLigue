package ca.ulaval.glo2004.visualigue.persistence.marshalling;

import ca.ulaval.glo2004.visualigue.utils.FileUtils;
import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collection;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import javax.inject.Inject;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.adapters.XmlAdapter;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;

public class XmlRepositoryMarshaller<T> extends XmlMarshaller<T> {

    private String repositoryName;
    private XmlAdapter<T, T> rootXmlAdapter;

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

    @SuppressWarnings("rawtypes")
    public void setRootAdapter(XmlAdapter<T, T> rootXmlAdapter) {
        this.rootXmlAdapter = rootXmlAdapter;
    }

    public synchronized Map<UUID, T> unmarshalAll() {
        Map<UUID, T> objects = new ConcurrentHashMap();
        if (FileUtils.directoryExists(repositoryName)) {
            Collection<File> files = FileUtils.listFiles(new File(repositoryName), null, false);
            files.forEach(file -> {
                UUID uuid = UUID.fromString(FilenameUtils.removeExtension(file.getName()));
                objects.put(uuid, unmarshal(uuid));
            });
        }
        return objects;
    }

    public synchronized T unmarshal(UUID uuid) {
        InputStream inputStream = null;
        try {
            File file = new File(getFileName(uuid));
            inputStream = FileUtils.openInputStream(file);
            T object = (T) super.unmarshal(inputStream);
            if (rootXmlAdapter != null) {
                return rootXmlAdapter.unmarshal(object);
            } else {
                return object;
            }
        } catch (Exception ex) {
            throw new RuntimeException(String.format("An exception occured while trying to unmarshal file '%s'.", repositoryName), ex);
        } finally {
            IOUtils.closeQuietly(inputStream);
        }
    }

    public synchronized void marshal(T object, UUID uuid) {
        OutputStream outputStream = null;
        try {
            File file = FileUtils.createFile(getFileName(uuid));
            outputStream = FileUtils.openOutputStream(file);
            if (rootXmlAdapter != null) {
                object = rootXmlAdapter.marshal(object);
            }
            super.marshal(object, outputStream);
        } catch (Exception ex) {
            throw new RuntimeException(String.format("An I/O exception occured while trying to write file '%s'.", repositoryName), ex);
        } finally {
            IOUtils.closeQuietly(outputStream);
        }
    }

    public synchronized void remove(UUID uuid) {
        File file = new File(getFileName(uuid));
        file.delete();
    }

    private String getFileName(UUID uuid) {
        return String.format("%s/%s.xml", repositoryName, uuid);
    }
}
