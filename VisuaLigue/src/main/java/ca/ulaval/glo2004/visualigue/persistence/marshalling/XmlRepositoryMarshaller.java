package ca.ulaval.glo2004.visualigue.persistence.marshalling;

import java.io.*;
import javax.inject.Inject;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.adapters.XmlAdapter;
import org.apache.commons.io.FileUtils;

public class XmlRepositoryMarshaller<T> extends XmlMarshaller<T> {

    private String fileName;

    @Inject
    public XmlRepositoryMarshaller(final Class<T> type, final String fileName) {
        super(type);
        init(fileName);
    }

    @Inject
    public XmlRepositoryMarshaller(final Marshaller marshaller, final Unmarshaller unmarshaller, final String fileName) {
        super(marshaller, unmarshaller);
        init(fileName);
    }

    private void init(final String fileName) {
        this.fileName = fileName;
    }

    @SuppressWarnings("rawtypes")
    public void setMarshallingAdapters(XmlAdapter xmlAdapter) {
        marshaller.setAdapter(xmlAdapter);
        unmarshaller.setAdapter(xmlAdapter);
    }

    public synchronized T unmarshal(T defaultObject) {
        T unmarshalledObject;
        try {
            File file = new File(fileName);
            String t = file.getAbsolutePath();
            InputStream inputStream = FileUtils.openInputStream(file);
            unmarshalledObject = super.unmarshal(inputStream);
            inputStream.close();
        } catch (FileNotFoundException e) {
            return defaultObject;
        } catch (IOException e) {
            throw new UncheckedIOException(String.format("An I/O exception occured while trying to read file '%s'.", fileName), e);
        }
        return unmarshalledObject;
    }

    public synchronized void marshal(T object) {
        try {
            File file = new File(fileName);
            file.getParentFile().mkdirs();
            OutputStream outputStream = FileUtils.openOutputStream(file);
            super.marshal(object, outputStream);
            outputStream.close();
        } catch (IOException e) {
            throw new UncheckedIOException(String.format("An I/O exception occured while trying to write file '%s'.", fileName), e);
        }
    }

    public String getResourceName() {
        return fileName;
    }
}
