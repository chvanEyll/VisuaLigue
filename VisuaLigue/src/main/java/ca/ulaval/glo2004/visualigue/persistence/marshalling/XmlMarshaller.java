package ca.ulaval.glo2004.visualigue.persistence.marshalling;

import java.io.InputStream;
import java.io.OutputStream;
import javax.inject.Inject;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

public class XmlMarshaller<T> {

    protected Marshaller marshaller;
    protected Unmarshaller unmarshaller;
    private Class<T> type;

    @Inject
    public XmlMarshaller(final Class<T> type) {
        this.type = type;
        initDefaultMarshallers();
    }

    @Inject
    public XmlMarshaller(final Marshaller marshaller, final Unmarshaller unmarshaller) {
        this.marshaller = marshaller;
        this.unmarshaller = unmarshaller;
    }

    private void initDefaultMarshallers() {
        JAXBContext jaxbContext = initializeJAXBContext();
        initDefaultMarshaller(jaxbContext);
        initDefaultUnmarshaller(jaxbContext);
    }

    private JAXBContext initializeJAXBContext() {
        try {
            return JAXBContext.newInstance(type);
        } catch (JAXBException e) {
            throw new MarshallingException("JAXB context initialization failed.", e);
        }
    }

    private void initDefaultMarshaller(JAXBContext jaxbContext) {
        try {
            marshaller = jaxbContext.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        } catch (JAXBException e) {
            throw new MarshallingException("Marshaller initialization failed.", e);
        }
    }

    private void initDefaultUnmarshaller(JAXBContext jaxbContext) {
        try {
            unmarshaller = jaxbContext.createUnmarshaller();
        } catch (JAXBException e) {
            throw new MarshallingException("Unmarshaller initialization failed.", e);
        }
    }

    public void marshal(T element, OutputStream outputStream) {
        try {
            marshaller.marshal(element, outputStream);
        } catch (JAXBException e) {
            throw new MarshallingException("Failed to marshall objects to the specified output stream.", e);
        }
    }

    @SuppressWarnings("unchecked")
    public T unmarshal(InputStream inputStream) {
        try {
            return (T) unmarshaller.unmarshal(inputStream);
        } catch (JAXBException e) {
            throw new MarshallingException("Failed to unmarshall objects from the specified input stream.", e);
        }
    }

    public Marshaller getMarshaller() {
        return marshaller;
    }

    public Unmarshaller getUnmarshaller() {
        return unmarshaller;
    }
}