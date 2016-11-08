package ca.ulaval.glo2004.visualigue.domain.play.actor;

import ca.ulaval.glo2004.visualigue.domain.play.actorstate.ActorState;
import ca.ulaval.glo2004.visualigue.domain.play.actorstate.PlayerState;
import ca.ulaval.glo2004.visualigue.domain.sport.playercategory.PlayerCategory;
import ca.ulaval.glo2004.visualigue.domain.xmladapters.XmlPlayerCategoryRefAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

public class PlayerActor extends Actor {

    @XmlJavaTypeAdapter(XmlPlayerCategoryRefAdapter.class)
    private PlayerCategory playerCategory;
    private TeamSide teamSide = TeamSide.ALLIES;

    public PlayerActor() {
        //Required for JAXB instanciation.
    }

    public PlayerActor(PlayerCategory playerCategory, TeamSide teamSide) {
        this.playerCategory = playerCategory;
        this.teamSide = teamSide;
    }

    public PlayerCategory getPlayerCategory() {
        return playerCategory;
    }

    public TeamSide getTeamSide() {
        return teamSide;
    }

    @Override
    public ActorState getBaseState() {
        return new PlayerState();
    }

}
