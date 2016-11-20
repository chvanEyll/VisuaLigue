package visualigue.domain;

import java.util.HashSet;
import java.util.Set;

public class Sport {

    private String name;
    private Boolean isBuiltIn = false;
    private Ball ball = new Ball();
    private PlayingSurface playingSurface;
    //@XmlJavaTypeAdapter(XmlPlayerCategoryRefAdapter.class)
    private Set<String> teams = new HashSet();

    public Sport(String name) {
        this.name = name;
    }
    
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Ball getBall() {
        return this.ball;
    }

    public void setBall(Ball ball) {
        this.ball = ball;
    }
    
    public PlayingSurface getPlayingSurface() {
        return this.playingSurface;
    }

    public void setPlayingSurface(PlayingSurface playingSurface) {
        this.playingSurface = playingSurface;
    }

    public Set<String> getPlayerCategories() {
        return this.teams;
    }

    public void addPlayerCategory(String team) {
        teams.add(team);
    }

    public void removePlayerCategory(String team) {
        teams.remove(team);
    }

    public Boolean isBuiltIn() {
        return isBuiltIn;
    }

    public void setIsBuiltIn(Boolean isBuiltIn) {
        this.isBuiltIn = isBuiltIn;
    }

}
