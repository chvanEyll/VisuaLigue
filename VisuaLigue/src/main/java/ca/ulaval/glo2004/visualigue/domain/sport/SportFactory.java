package ca.ulaval.glo2004.visualigue.domain.sport;

import ca.ulaval.glo2004.visualigue.domain.playercategory.PlayerCategory;
import java.util.HashSet;
import java.util.Set;
import javafx.scene.paint.Color;

public class SportFactory {

    public Sport create(String name) {
        Sport sport = new Sport(name);
        Set<PlayerCategory> playerCategories = new HashSet<>();
        playerCategories.add(new PlayerCategory("Category 1", Color.AQUA, Color.BISQUE, 3));
        playerCategories.add(new PlayerCategory("Category 2", Color.AQUA, Color.BISQUE, 3));
        sport.setPlayerCategories(playerCategories);
        return sport;
    }
}
