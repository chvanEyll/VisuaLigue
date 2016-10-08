package ca.ulaval.glo2004.visualigue.persistence.sport;

import ca.ulaval.glo2004.visualigue.domain.sport.Sport;
import java.util.ArrayList;
import java.util.Collection;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlRootElement(name = "visualigue")
public class XmlSportRootElement {

    private Collection<Sport> sports = new ArrayList<>();

    @XmlElementWrapper(name = "sports")
    @XmlElement(name = "sport")
    @XmlJavaTypeAdapter(XmlSportAdapter.class)
    public Collection<Sport> getSports() {
        return this.sports;
    }

    public void setSports(Collection<Sport> sports) {
        this.sports = sports;
    }
}
