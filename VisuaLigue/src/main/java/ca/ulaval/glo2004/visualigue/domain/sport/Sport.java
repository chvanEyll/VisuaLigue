package ca.ulaval.glo2004.visualigue.domain.sport;

import ca.ulaval.glo2004.visualigue.domain.DomainObject;
import ca.ulaval.glo2004.visualigue.domain.sport.ball.Ball;
import ca.ulaval.glo2004.visualigue.domain.sport.playercategory.PlayerCategory;
import ca.ulaval.glo2004.visualigue.domain.sport.playingsurface.PlayingSurface;
import ca.ulaval.glo2004.visualigue.domain.xmladapters.XmlSportAdapter;
import java.util.HashSet;
import java.util.Set;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlRootElement(name = "sport")
@XmlJavaTypeAdapter(XmlSportAdapter.class)
public class Sport extends DomainObject {

    private String name;
    private Boolean isBuiltIn = false;
    private String customIconUUID;
    private String builtInIconPathName = "/images/built-in-sport-icons/generic-icon.png";
    private Ball ball = new Ball();
    private PlayingSurface playingSurface = new PlayingSurface();
    private Set<String> playerCategoryUUIDs = new HashSet();
    @XmlTransient
    private Set<PlayerCategory> playerCategories = new HashSet();

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

    public Boolean isBuiltIn() {
        return isBuiltIn;
    }

    public void setIsBuiltIn(Boolean isBuiltIn) {
        this.isBuiltIn = isBuiltIn;
    }

    public Boolean hasCustomIcon() {
        return customIconUUID != null;
    }

    public String getCustomIconUUID() {
        return customIconUUID;
    }

    public void setCustomIconUUID(String customIconUUID) {
        this.customIconUUID = customIconUUID;
    }

    public String getBuiltInIconPathName() {
        return builtInIconPathName;
    }

    public void setBuiltInIconPathName(String builtInIconPathName) {
        this.builtInIconPathName = builtInIconPathName;
    }

    public Ball getBall() {
        return ball;
    }

    public void setBall(Ball ball) {
        this.ball = ball;
    }

    public PlayingSurface getPlayingSurface() {
        return playingSurface;
    }

    public void setPlayingSurface(PlayingSurface playingSurface) {
        this.playingSurface = playingSurface;
    }

    public void setPlayerCategoryUUIDs(Set<String> playerCategoryUUIDs) {
        this.playerCategoryUUIDs = playerCategoryUUIDs;
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

    public void removeCategory(PlayerCategory playerCategory) {
        playerCategories.remove(playerCategory);
    }

    public Set<String> getPlayerCategoryUUIDs() {
        return playerCategoryUUIDs;
    }
}
