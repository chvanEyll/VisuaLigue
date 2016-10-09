package ca.ulaval.glo2004.visualigue.domain.sport;

import ca.ulaval.glo2004.visualigue.domain.DomainObject;
import ca.ulaval.glo2004.visualigue.domain.playercategory.PlayerCategory;
import ca.ulaval.glo2004.visualigue.domain.playingsurface.PlayingSurface;
import java.util.HashSet;
import java.util.Set;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "sport")
public class Sport extends DomainObject {

    private String name;
    private String builtInIconPathName = "/images/built-in-sport-icons/generic-icon";
    private PlayingSurface playingSurface = new PlayingSurface();
    private Set<PlayerCategory> playerCategories = new HashSet<>();

    public Sport() {
        //Required for JAXB instanciation.
    }

    public Sport(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBuiltInIconPathName() {
        return builtInIconPathName;
    }

    public void setBuiltInIconPathName(String builtInIconPathName) {
        this.builtInIconPathName = builtInIconPathName;
    }

    public PlayingSurface getPlayingSurface() {
        return playingSurface;
    }

    public void setPlayingSurface(PlayingSurface playingSurface) {
        this.playingSurface = playingSurface;
    }

    public Set<PlayerCategory> getPlayerCategories() {
        return playerCategories;
    }

    public void setPlayerCategories(Set<PlayerCategory> playerCategories) {
        this.playerCategories = playerCategories;
    }

    public void addPlayerCategory(PlayerCategory playerCategory) {
        playerCategories.add(playerCategory);
    }

    @Override
    public int hashCode() {
        return this.uuid.hashCode();
    }
}
