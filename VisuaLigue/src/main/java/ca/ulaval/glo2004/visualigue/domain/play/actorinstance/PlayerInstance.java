package ca.ulaval.glo2004.visualigue.domain.play.actorinstance;

import ca.ulaval.glo2004.visualigue.domain.sport.playercategory.PlayerCategory;
import ca.ulaval.glo2004.visualigue.domain.xmladapters.XmlPlayerInstanceAdapter;
import java.util.UUID;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlRootElement(name = "playerinstance")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlJavaTypeAdapter(XmlPlayerInstanceAdapter.class)
public class PlayerInstance extends ActorInstance {

    private UUID playerCategoryUUID;
    @XmlTransient
    private PlayerCategory playerCategory;
    private TeamSide teamSide = TeamSide.ALLIES;

    public PlayerInstance(PlayerCategory playerCategory, TeamSide teamSide) {
        this.playerCategory = playerCategory;
        this.teamSide = teamSide;
    }

    public UUID getPlayerCategoryUUID() {
        return playerCategoryUUID;
    }

    public void setPlayerCategoryUUID(UUID playerCategoryUUID) {
        this.playerCategoryUUID = playerCategoryUUID;
    }

    public PlayerCategory getPlayerCategory() {
        return playerCategory;
    }

    public void setPlayerCategory(PlayerCategory playerCategory) {
        this.playerCategory = playerCategory;
    }

    public TeamSide getTeamSide() {
        return teamSide;
    }

    public void setTeamSide(TeamSide teamSide) {
        this.teamSide = teamSide;
    }

}
