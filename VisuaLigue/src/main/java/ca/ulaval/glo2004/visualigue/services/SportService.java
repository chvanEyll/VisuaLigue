package ca.ulaval.glo2004.visualigue.services;

import ca.ulaval.glo2004.visualigue.domain.Sport;
import java.util.HashSet;
import java.util.Set;
import javax.inject.Singleton;

@Singleton
public class SportService {
    
    private Set<Sport> sports = new HashSet<>();
    
    public SportService() {
        sports.add(new Sport("Sport 1"));
        sports.add(new Sport("Sport 2"));
    }
    
    public void createSport(Sport sport) {
        sports.add(sport);
    }
}
