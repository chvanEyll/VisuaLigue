package main.java.services;

import java.util.List;
import main.java.domain.Sport;

public class SportService {
    
    private List<Sport> sports;
    
    public void createSport(Sport sport) {
        sports.add(sport);
    }
}
