package ca.ulaval.glo2004.visualigue.domain;

public class Sport implements Comparable {

    private String name;
    private String builtInIconFileName = "/images/generic-sport-icon";
    private PlayingSurface playingSurface = new PlayingSurface();

    public Sport(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
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

    @Override
    public int compareTo(Object obj) {
        if (!(obj instanceof Sport)) {
            return 0;
        }
        Sport sport = (Sport) obj;
        return name.compareTo(sport.getName());
    }
}
