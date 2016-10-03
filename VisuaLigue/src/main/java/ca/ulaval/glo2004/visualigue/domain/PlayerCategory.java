package ca.ulaval.glo2004.visualigue.domain;

import javafx.scene.paint.Color;

public class PlayerCategory {
    
    private String name;
    private Color allyColor = Color.web("#0071BC");
    private Color opponentColor = Color.web("#C1272D");
    private int defaultNumberOfPlayers = 0;
    
    public PlayerCategory(String name) {
        this.name = name;
    }
    
}
