package ca.ulaval.glo2004.visualigue.domain.play.actorinstance;

import ca.ulaval.glo2004.visualigue.domain.sport.playercategory.PlayerCategory;
import ca.ulaval.glo2004.visualigue.domain.xmladapters.XmlPlayerCategoryRefAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

public class PlayerInstance extends ActorInstance {

    @XmlJavaTypeAdapter(XmlPlayerCategoryRefAdapter.class)
    private PlayerCategory playerCategory;
    private TeamSide teamSide = TeamSide.ALLIES;

    public PlayerInstance() {
        //Required for JAXB instanciation.
    }

    public PlayerInstance(PlayerCategory playerCategory, TeamSide teamSide) {
        this.playerCategory = playerCategory;
        this.teamSide = teamSide;
    }

    public PlayerCategory getPlayerCategory() {
        return playerCategory;
    }

    public TeamSide getTeamSide() {
        return teamSide;
    }

}
