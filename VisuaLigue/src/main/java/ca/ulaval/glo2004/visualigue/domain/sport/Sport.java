package ca.ulaval.glo2004.visualigue.domain.sport;

import ca.ulaval.glo2004.visualigue.domain.DomainObject;
import ca.ulaval.glo2004.visualigue.domain.sport.ball.Ball;
import ca.ulaval.glo2004.visualigue.domain.sport.playercategory.PlayerCategory;
import ca.ulaval.glo2004.visualigue.domain.sport.playingsurface.PlayingSurface;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "sport")
@XmlAccessorType(XmlAccessType.FIELD)
public class Sport extends DomainObject {

    private String name;
    private Boolean isBuiltIn = false;
    private UUID customIconUUID;
    private String builtInIconPathName = "/images/built-in-sport-icons/generic-icon.png";
    private Ball ball = new Ball();
    private PlayingSurface playingSurface = new PlayingSurface();
    private Map<UUID, PlayerCategory> playerCategories = new ConcurrentHashMap();

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

    public UUID getCustomIconUUID() {
        return customIconUUID;
    }

    public void setCustomIconUUID(UUID customIconUUID) {
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

    public Map<UUID, PlayerCategory> getPlayerCategories() {
        return playerCategories;
    }

    public void addPlayerCategory(PlayerCategory playerCategory) {
        playerCategories.put(playerCategory.getUUID(), playerCategory);
    }

    public void removeCategory(UUID playerCategoryUUID) {
        playerCategories.remove(playerCategoryUUID);
    }

    public PlayerCategory getPlayerCategory(UUID playerCategoryUUID) {
        return playerCategories.get(playerCategoryUUID);
    }

    @Override
    public int hashCode() {
        return this.uuid.hashCode();
    }
}
