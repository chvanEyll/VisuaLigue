package ca.ulaval.glo2004.visualigue.domain.playercategory;

import javafx.scene.paint.Color;

public class PlayerCategoryFactory {

    public PlayerCategory create(String name, Color allyColor, Color opponentColor, Integer defaultNumberOfPlayers) {
        return new PlayerCategory(name, allyColor, opponentColor, defaultNumberOfPlayers);
    }
}
