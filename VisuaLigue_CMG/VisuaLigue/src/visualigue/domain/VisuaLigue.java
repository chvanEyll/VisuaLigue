package visualigue.domain;

import ca.ulaval.glo2004.visualigue.utils.EventHandler;
import java.util.ArrayList;
import java.util.List;

public class VisuaLigue {

    public static final VisuaLigue INSTANCE = new VisuaLigue();
    public EventHandler<String> onSportCreated = new EventHandler();

    public static VisuaLigue getInstance() {
        return INSTANCE;
    }

    List<Sport> sports = new ArrayList();

    public Sport createSport(String name) {
        Sport sport = new Sport(name);
        sports.add(sport);
        onSportCreated.fire(this, name);
        return sport;
    }
    
    public List<Sport> getListeSports() {
        
        return sports;
        
    }

    public void save() {

    }

}
