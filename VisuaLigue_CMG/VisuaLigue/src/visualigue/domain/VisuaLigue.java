package visualigue.domain;

import ca.ulaval.glo2004.visualigue.utils.EventHandler;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

public class VisuaLigue {

    public static final VisuaLigue INSTANCE = new VisuaLigue();
    public EventHandler<String> onSportCreated = new EventHandler();
    
    HashMap<String, Sport> sports = new HashMap();
    HashMap<String, Jeu> jeux = new HashMap();

    public static VisuaLigue getInstance() {
        return INSTANCE;
    }


    public void createSport(String name, Double width, Double length, 
            String units) {
        Sport sport = new Sport(name);
        PlayingSurface playingSurface = new PlayingSurface(width, length, units);
        sport.setPlayingSurface(playingSurface);
        sports.put(name, sport);
    }
    
    public Iterator getListeSports() {
        
        return sports.keySet().iterator();
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
    
    public void createJeux(String jeuName,String sportName)
    {
        Sport sport = sports.get(sportName);
        Jeu jeu = new Jeu(jeuName, sport);
        jeux.put(jeuName, jeu);
    }

    public Iterator getListeJeux() {
        return jeux.keySet().iterator();
    }

    public void save() {

    }

}
