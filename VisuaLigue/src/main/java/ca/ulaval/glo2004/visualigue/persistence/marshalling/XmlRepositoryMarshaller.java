package ca.ulaval.glo2004.visualigue.persistence.marshalling;

import java.io.*;
import javax.inject.Inject;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.adapters.XmlAdapter;
import org.apache.commons.io.FileUtils;

public class XmlRepositoryMarshaller<T> extends XmlMarshaller<T> {

    private String pathName;

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

    private void init(final String pathName) {
        this.pathName = pathName;
    }

    @SuppressWarnings("rawtypes")
    public void setMarshallingAdapters(XmlAdapter xmlAdapter) {
        marshaller.setAdapter(xmlAdapter);
        unmarshaller.setAdapter(xmlAdapter);
    }

    public synchronized T unmarshal(T defaultObject) {
        T unmarshalledObject;
        try {
            File file = new File(pathName);
            String t = file.getAbsolutePath();
            InputStream inputStream = FileUtils.openInputStream(file);
            unmarshalledObject = super.unmarshal(inputStream);
            inputStream.close();
        } catch (FileNotFoundException e) {
            return defaultObject;
        } catch (IOException e) {
            throw new UncheckedIOException(String.format("An I/O exception occured while trying to read file '%s'.", pathName), e);
        }
        return unmarshalledObject;
    }

    public synchronized void marshal(T object) {
        try {
            File file = new File(pathName);
            file.getParentFile().mkdirs();
            OutputStream outputStream = FileUtils.openOutputStream(file);
            super.marshal(object, outputStream);
            outputStream.close();
        } catch (IOException e) {
            throw new UncheckedIOException(String.format("An I/O exception occured while trying to write file '%s'.", pathName), e);
        }
    }

    public String getResourceName() {
        return pathName;
    }
}
