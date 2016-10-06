package ca.ulaval.glo2004.visualigue.services;

import ca.ulaval.glo2004.visualigue.domain.playingsurface.PlayingSurface;
import ca.ulaval.glo2004.visualigue.domain.playingsurface.PlayingSurfaceFactory;
import ca.ulaval.glo2004.visualigue.domain.playingsurface.PlayingSurfaceUnit;
import ca.ulaval.glo2004.visualigue.domain.sport.Sport;
import ca.ulaval.glo2004.visualigue.domain.sport.SportFactory;
import ca.ulaval.glo2004.visualigue.domain.sport.SportNameAlreadyInUseException;
import ca.ulaval.glo2004.visualigue.utils.EventHandler;
import java.util.HashSet;
import java.util.Set;
import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class SportService {

    private final Set<Sport> sports = new HashSet<>();
    private final SportFactory sportFactory;
    private final PlayingSurfaceFactory playingSurfaceFactory;

    public EventHandler<Sport> onSportCreated = new EventHandler<>();
    public EventHandler<Sport> onSportUpdated = new EventHandler<>();

    @Inject
    public SportService(SportFactory sportFactory, PlayingSurfaceFactory playingSurfaceFactory) {
        this.sportFactory = sportFactory;
        this.playingSurfaceFactory = playingSurfaceFactory;
        sports.add(sportFactory.create("Sport 2"));
        sports.add(sportFactory.create("Sport 1"));
    }

    public Sport createSport(String name) throws SportNameAlreadyInUseException {
        if (sports.stream().anyMatch(sport -> sport.getName().equals(name))) {
            throw new SportNameAlreadyInUseException();
        }
        Sport sport = sportFactory.create(name);
        sports.add(sport);
        onSportCreated.fire(this, sport);
        return sport;
    }

    public void updateSport(Sport sport, String name) throws SportNameAlreadyInUseException {
        sport.setName(name);
        onSportUpdated.fire(this, sport);
    }

    public void updateSportPlayingSurface(Sport sport, Double width, Double length, PlayingSurfaceUnit widthUnits, PlayingSurfaceUnit lengthUnits, String imageFileName) {
        PlayingSurface playingSurface = playingSurfaceFactory.create(width, length, widthUnits, lengthUnits, imageFileName);
        sport.setPlayingSurface(playingSurface);
    }

    public Set<Sport> getSports() {
        return sports;
    }
}
