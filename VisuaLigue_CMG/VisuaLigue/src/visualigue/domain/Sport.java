package visualigue.domain;

import java.util.HashSet;
import java.util.Set;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

public class Sport {

    private String name;
    private Boolean isBuiltIn = false;
    private PlayingSurface playingSurface;
    //@XmlJavaTypeAdapter(XmlPlayerCategoryRefAdapter.class)
    private Set<String> teams = new HashSet();

    public Sport(String name) {
        this.name = name;
    }
    
   public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean isBuiltIn() {
        return isBuiltIn;
    }

    public void setIsBuiltIn(Boolean isBuiltIn) {
        this.isBuiltIn = isBuiltIn;
    }

    public PlayingSurface getPlayingSurface() {
        return playingSurface;
    }

    public void setPlayingSurface(PlayingSurface playingSurface) {
        this.playingSurface = playingSurface;
    }

    public Set<String> getPlayerCategories() {
        return teams;
    }

    public void addPlayerCategory(String team) {
        teams.add(team);
    }

    public void removePlayerCategory(String team) {
        teams.remove(team);
    }

}
