package ca.ulaval.glo2004.visualigue.domain;

public class Sport implements Comparable {
    
    private String name;
    
    public Sport(String name) {
        this.name = name;   
    }
    
    public String getName() {
        return name;
    }

    @Override
    public int compareTo(Object obj) {
        if (!(obj instanceof Sport)) {
            return 0;
        }
        Sport sport = (Sport)obj;
        return name.compareTo(sport.getName());
    }
}
