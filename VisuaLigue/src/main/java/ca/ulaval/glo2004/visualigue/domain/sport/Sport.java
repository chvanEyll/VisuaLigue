package ca.ulaval.glo2004.visualigue.domain.sport;

import ca.ulaval.glo2004.visualigue.domain.playercategory.PlayerCategory;
import ca.ulaval.glo2004.visualigue.domain.playingsurface.PlayingSurface;
import java.util.HashSet;
import java.util.Set;

public class Sport implements Comparable {

    private String name;
    private String builtInIconFileName = "/images/generic-sport-icon";
    private PlayingSurface playingSurface = new PlayingSurface();
    private Set<PlayerCategory> playerCategories = new HashSet<>();

    public Sport(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBuiltInIconFileName() {
        return builtInIconFileName;
    }

    public void setBuiltInIconFileName(String builtInIconFileName) {
        this.builtInIconFileName = builtInIconFileName;
    }

    public PlayingSurface getPlayingSurface() {
        return playingSurface;
    }

    public void setPlayingSurface(PlayingSurface playingSurface) {
        this.playingSurface = playingSurface;
    }

    public Set<PlayerCategory> getPlayerCategories() {
        return playerCategories;
    }

    public void setPlayerCategories(Set<PlayerCategory> playerCategories) {
        this.playerCategories = playerCategories;
    }

    public void addPlayerCategory(PlayerCategory playerCategory) {
        playerCategories.add(playerCategory);
    }

    @Override
    public int compareTo(Object obj) {
        if (!(obj instanceof Sport)) {
            return 0;
        }
        Sport sport = (Sport) obj;
        return name.compareTo(sport.getName());
    }
}
