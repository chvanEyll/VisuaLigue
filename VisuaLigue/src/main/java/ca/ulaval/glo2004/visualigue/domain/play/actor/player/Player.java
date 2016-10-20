package ca.ulaval.glo2004.visualigue.domain.play.actor.player;

import ca.ulaval.glo2004.visualigue.domain.play.actor.Actor;
import ca.ulaval.glo2004.visualigue.domain.playercategory.PlayerCategory;

public class Player extends Actor {

    private PlayerCategory playerCategory;
    private TeamSide teamSide = TeamSide.ALLIES;

    public Player(PlayerCategory playerCategory, TeamSide teamSide) {
        this.playerCategory = playerCategory;
        this.teamSide = teamSide;
    }

}
