package ca.ulaval.glo2004.visualigue.domain.sport.playercategory;

import javafx.scene.paint.Color;
import javax.inject.Singleton;

@Singleton
public class PlayerCategoryFactory {

    public PlayerCategory create(String name, String abbreviation, Color allyColor, Color opponentColor, Integer defaultNumberOfPlayers) {
        return new PlayerCategory(name, abbreviation, allyColor, opponentColor, defaultNumberOfPlayers);
    }
}
