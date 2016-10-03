package ca.ulaval.glo2004.visualigue.domain;

public class Sport implements Comparable {

    private String name;
    private String builtInIconFileName = "/images/sport-hockey-icon";

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

    @Override
    public int compareTo(Object obj) {
        if (!(obj instanceof Sport)) {
            return 0;
        }
        Sport sport = (Sport) obj;
        return name.compareTo(sport.getName());
    }
}
