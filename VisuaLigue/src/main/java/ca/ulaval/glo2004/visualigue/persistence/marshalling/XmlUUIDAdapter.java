package ca.ulaval.glo2004.visualigue.persistence.marshalling;

import java.util.UUID;
import javax.xml.bind.annotation.adapters.XmlAdapter;

public class XmlUUIDAdapter extends XmlAdapter<String, UUID> {

    @Override
    public String marshal(UUID uuid) throws Exception {
        return uuid.toString();
    }

    @Override
    public UUID unmarshal(String stringUUID) throws Exception {
        return UUID.fromString(stringUUID);
    }

}
