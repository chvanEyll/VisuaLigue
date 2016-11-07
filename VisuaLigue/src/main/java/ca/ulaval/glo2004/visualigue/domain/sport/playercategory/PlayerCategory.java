package ca.ulaval.glo2004.visualigue.domain.sport.playercategory;

import ca.ulaval.glo2004.visualigue.domain.DomainObject;
import ca.ulaval.glo2004.visualigue.domain.play.actor.TeamSide;
import ca.ulaval.glo2004.visualigue.domain.xmladapters.XmlColorAdapter;
import javafx.scene.paint.Color;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlRootElement(name = "playerCategory")
public class PlayerCategory extends DomainObject {

    private String name;
    private String abbreviation;
    @XmlJavaTypeAdapter(value = XmlColorAdapter.class)
    private Color allyColor = Color.web("#001A80");
    @XmlJavaTypeAdapter(value = XmlColorAdapter.class)
    private Color opponentColor = Color.web("#990000");
    private Integer defaultNumberOfPlayers = 0;

    protected PlayerCategory() {
        //Required for JAXB instanciation.
    }

    public PlayerCategory(String name, String abbreviation, Color allyColor, Color opponentColor, Integer defaultNumberOfPlayers) {
        this.name = name;
        this.abbreviation = abbreviation;
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

    public String getAbbreviation() {
        return abbreviation;
    }

    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    public Color getColor(TeamSide teamSide) {
        if (teamSide == TeamSide.ALLIES) {
            return allyColor;
        } else {
            return opponentColor;
        }
    }

    public void setColor(Color color, TeamSide teamSide) {
        if (teamSide == TeamSide.ALLIES) {
            this.allyColor = color;
        } else {
            this.opponentColor = color;
        }
    }

    public Integer getDefaultNumberOfPlayers() {
        return defaultNumberOfPlayers;
    }

    public void setDefaultNumberOfPlayers(Integer defaultNumberOfPlayers) {
        this.defaultNumberOfPlayers = defaultNumberOfPlayers;
    }
}
