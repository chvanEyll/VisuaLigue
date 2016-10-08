package ca.ulaval.glo2004.visualigue.persistence.sport;

import ca.ulaval.glo2004.visualigue.domain.sport.Sport;
import ca.ulaval.glo2004.visualigue.domain.sport.SportFactory;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.xml.bind.annotation.adapters.XmlAdapter;

@Singleton
public class XmlSportAdapter extends XmlAdapter<XmlSport, Sport> {

    private SportFactory sportFactory;

    @Inject
    public XmlSportAdapter(final SportFactory sportFactory) {
        this.sportFactory = sportFactory;
    }

    @Override
    public Sport unmarshal(XmlSport xmlSport) throws Exception {
        Sport sport = sportFactory.create(xmlSport.name);
        sport.setUUID(xmlSport.uuid);
        sport.setBuiltInIconPathName(xmlSport.builtInIconPathName);
        sport.setPlayingSurface(xmlSport.playingSurface);
        sport.setPlayerCategories(xmlSport.playerCategories);
        return sport;
    }

    @Override
    public XmlSport marshal(Sport sport) throws Exception {
        XmlSport xmlSport = new XmlSport();

        xmlSport.uuid = sport.getUUID();
        xmlSport.name = sport.getName();
        xmlSport.builtInIconPathName = sport.getBuiltInIconPathName();
        xmlSport.playingSurface = sport.getPlayingSurface();
        xmlSport.playerCategories = sport.getPlayerCategories();

        return xmlSport;
    }
}
