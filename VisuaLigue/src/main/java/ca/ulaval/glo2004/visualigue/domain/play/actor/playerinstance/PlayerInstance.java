package ca.ulaval.glo2004.visualigue.domain.play.actor.playerinstance;

import ca.ulaval.glo2004.visualigue.domain.play.actor.ActorInstance;
import ca.ulaval.glo2004.visualigue.domain.playercategory.PlayerCategory;

public class PlayerInstance extends ActorInstance {

    private PlayerCategory playerCategory;
    private TeamSide teamSide = TeamSide.ALLIES;

    public PlayerInstance(PlayerCategory playerCategory, TeamSide teamSide) {
        this.playerCategory = playerCategory;
        this.teamSide = teamSide;
    }

}
