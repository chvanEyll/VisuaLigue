package ca.ulaval.glo2004.visualigue.domain.playercategory;

import java.awt.Color;
import javax.inject.Singleton;

@Singleton
public class PlayerCategoryFactory {

    public PlayerCategory create(String name, Color allyColor, Color opponentColor, Integer defaultNumberOfPlayers) {
        return new PlayerCategory(name, allyColor, opponentColor, defaultNumberOfPlayers);
    }
}
