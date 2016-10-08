package ca.ulaval.glo2004.visualigue.persistence.sport;

import ca.ulaval.glo2004.visualigue.domain.playercategory.PlayerCategory;
import ca.ulaval.glo2004.visualigue.domain.playingsurface.PlayingSurface;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "sport")
public class XmlSport {

    public UUID uuid;
    public String name;
    public String builtInIconFileName;
    public PlayingSurface playingSurface;
    public Set<PlayerCategory> playerCategories = new HashSet<>();
}
