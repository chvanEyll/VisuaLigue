package ca.ulaval.glo2004.visualigue.domain.playercategory;

import javafx.scene.paint.Color;

public class PlayerCategory {

    private String name;
    private Color allyColor = Color.web("#0071BC");
    private Color opponentColor = Color.web("#C1272D");
    private Integer defaultNumberOfPlayers = 0;

    public PlayerCategory(String name, Color allyColor, Color opponentColor, Integer defaultNumberOfPlayers) {
        this.name = name;
        this.allyColor = allyColor;
        this.opponentColor = opponentColor;
        this.defaultNumberOfPlayers = defaultNumberOfPlayers;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Color getAllyColor() {
        return allyColor;
    }

    public void setAllyColor(Color allyColor) {
        this.allyColor = allyColor;
    }

    public Color getOpponentColor() {
        return opponentColor;
    }

    public void setOpponentColor(Color opponentColor) {
        this.opponentColor = opponentColor;
    }

    public Integer getDefaultNumberOfPlayers() {
        return defaultNumberOfPlayers;
    }

    public void setDefaultNumberOfPlayers(Integer defaultNumberOfPlayers) {
        this.defaultNumberOfPlayers = defaultNumberOfPlayers;
    }
}
