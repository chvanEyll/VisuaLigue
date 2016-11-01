package ca.ulaval.glo2004.visualigue.domain.xmladapters;

import ca.ulaval.glo2004.visualigue.domain.sport.Sport;
import ca.ulaval.glo2004.visualigue.domain.sport.SportRepository;
import javax.inject.Inject;
import javax.xml.bind.annotation.adapters.XmlAdapter;

public class XmlSportRefAdapter extends XmlAdapter<String, Sport> {

    @Inject private SportRepository sportRepository;

    @Override
    public Sport unmarshal(String sportUUID) throws Exception {
        return sportRepository.get(sportUUID);
    }

    @Override
    public String marshal(Sport sport) throws Exception {
        return sport.getUUID();
    }

}
