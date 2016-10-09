package ca.ulaval.glo2004.visualigue.domain.playercategory;

import ca.ulaval.glo2004.visualigue.domain.DomainObject;
import ca.ulaval.glo2004.visualigue.domain.xmladapters.XmlColorAdapter;
import javafx.scene.paint.Color;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

public class PlayerCategory extends DomainObject {

    private String name;
    private Color allyColor = Color.web("#001A80");
    private Color opponentColor = Color.web("#990000");
    private Integer defaultNumberOfPlayers = 0;

    protected PlayerCategory() {
        //Required for JAXB instanciation.
    }

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

    @XmlJavaTypeAdapter(value = XmlColorAdapter.class)
    public Color getAllyColor() {
        return allyColor;
    }

    public void setAllyColor(Color allyColor) {
        this.allyColor = allyColor;
    }

    @XmlJavaTypeAdapter(value = XmlColorAdapter.class)
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
