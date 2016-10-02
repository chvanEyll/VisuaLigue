package ca.ulaval.glo2004.visualigue.services;

import java.util.List;
import ca.ulaval.glo2004.visualigue.domain.Sport;

public class SportService {
    
    private List<Sport> sports;
    
    public void createSport(Sport sport) {
        sports.add(sport);
    }
}
