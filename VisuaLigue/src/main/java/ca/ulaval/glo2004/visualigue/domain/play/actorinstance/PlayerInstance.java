package ca.ulaval.glo2004.visualigue.domain.play.actorinstance;

import ca.ulaval.glo2004.visualigue.domain.sport.playercategory.PlayerCategory;
import ca.ulaval.glo2004.visualigue.domain.xmladapters.XmlPlayerInstanceAdapter;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlJavaTypeAdapter(XmlPlayerInstanceAdapter.class)
public class PlayerInstance extends ActorInstance {

    private String playerCategoryUUID;
    @XmlTransient
    private PlayerCategory playerCategory;
    private TeamSide teamSide = TeamSide.ALLIES;

    public PlayerInstance(PlayerCategory playerCategory, TeamSide teamSide) {
        this.playerCategory = playerCategory;
        this.teamSide = teamSide;
    }

    public String getPlayerCategoryUUID() {
        return playerCategoryUUID;
    }

    public void setPlayerCategoryUUID(String playerCategoryUUID) {
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
