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

    public Sport createSport(String name, Double width, Double length, 
            String units) {
        Sport sport = new Sport(name);
        PlayingSurface playingSurface = new PlayingSurface(width, length, units);
        sport.setPlayingSurface(playingSurface);
        sports.add(sport);
        onSportCreated.fire(this, name);
        return sport;
    }
    
    public List<Sport> getListeSports() {
        
        return sports;
        
    }

    public String getDefaultSportName()
    {
        return "Nouveau Sport2.0";
    }
    
    public Double getDefaultSportLongueur()
    {
        return 100.0;
    }
    
    public Double getDefaultSportLargeur()
    {
        return 200.0;
    }

    public String[] getDefaultSportSurfaceUnit()
    {
        String [] defaultSurfaceUnit = {"Mètres", "Pieds", "Verges"};
        return defaultSurfaceUnit;
    }
    
    public void save() {

    }

}
