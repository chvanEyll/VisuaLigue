package ca.ulaval.glo2004.visualigue.domain.xmladapters;

import ca.ulaval.glo2004.visualigue.domain.play.Play;
import ca.ulaval.glo2004.visualigue.domain.sport.SportRepository;
import javax.inject.Inject;
import javax.xml.bind.annotation.adapters.XmlAdapter;

public class XmlPlayAdapter extends XmlAdapter<Play, Play> {

    @Inject SportRepository sportRepository;

    @Override
    public Play unmarshal(Play play) throws Exception {
        play.setSport(sportRepository.get(play.getSportUUID()));
        return play;
    }

    @Override
    public Play marshal(Play play) throws Exception {
        play.setSportUUID(play.getSport().getUUID());
        return play;
    }

}
