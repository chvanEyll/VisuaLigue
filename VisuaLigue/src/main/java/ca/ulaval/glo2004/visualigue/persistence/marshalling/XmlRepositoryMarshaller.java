package ca.ulaval.glo2004.visualigue.persistence.marshalling;

import ca.ulaval.glo2004.visualigue.utils.FileUtils;
import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.inject.Inject;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.adapters.XmlAdapter;
import org.apache.commons.io.FilenameUtils;
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

    @SuppressWarnings("rawtypes")
    public void setMarshallingAdapters(XmlAdapter xmlAdapter) {
        marshaller.setAdapter(xmlAdapter);
        unmarshaller.setAdapter(xmlAdapter);
    }

    public synchronized Map<String, T> unmarshalAll() {
        Map<String, T> objects = new ConcurrentHashMap();
        if (FileUtils.directoryExists(repositoryName)) {
            Collection<File> files = FileUtils.listFiles(new File(repositoryName), null, false);
            files.forEach(file -> {
                String uuid = FilenameUtils.removeExtension(file.getName());
                objects.put(uuid, unmarshal(uuid));
            });
        }
        return objects;
    }

    public synchronized T unmarshal(String uuid) {
        InputStream inputStream = null;
        try {
            File file = new File(getFileName(uuid));
            inputStream = FileUtils.openInputStream(file);
            return (T) super.unmarshal(inputStream);
        } catch (Exception ex) {
            throw new RuntimeException(String.format("An exception occured while trying to unmarshal file '%s'.", repositoryName), ex);
        } finally {
            IOUtils.closeQuietly(inputStream);
        }
    }

    public synchronized void marshal(T object, String uuid) {
        OutputStream outputStream = null;
        try {
            File file = FileUtils.createFile(getFileName(uuid));
            outputStream = FileUtils.openOutputStream(file);
            super.marshal(object, outputStream);
        } catch (Exception ex) {
            throw new RuntimeException(String.format("An I/O exception occured while trying to write file '%s'.", repositoryName), ex);
        } finally {
            IOUtils.closeQuietly(outputStream);
        }
    }

    public synchronized void remove(String uuid) {
        File file = new File(getFileName(uuid));
        file.delete();
    }

    private String getFileName(String uuid) {
        return String.format("%s/%s.xml", repositoryName, uuid);
    }
}
