package ca.ulaval.glo2004.visualigue.services;

import ca.ulaval.glo2004.visualigue.domain.playercategory.PlayerCategory;
import ca.ulaval.glo2004.visualigue.domain.playercategory.PlayerCategoryFactory;
import ca.ulaval.glo2004.visualigue.domain.playingsurface.PlayingSurface;
import ca.ulaval.glo2004.visualigue.domain.playingsurface.PlayingSurfaceFactory;
import ca.ulaval.glo2004.visualigue.domain.playingsurface.PlayingSurfaceUnit;
import ca.ulaval.glo2004.visualigue.domain.sport.Sport;
import ca.ulaval.glo2004.visualigue.domain.sport.SportFactory;
import ca.ulaval.glo2004.visualigue.domain.sport.SportNameAlreadyInUseException;
import ca.ulaval.glo2004.visualigue.utils.EventHandler;
import java.util.HashSet;
import java.util.Set;
import javafx.scene.paint.Color;
import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class SportService {

    private final Set<Sport> sports = new HashSet<>();
    private final SportFactory sportFactory;
    private final PlayingSurfaceFactory playingSurfaceFactory;
    private final PlayerCategoryFactory playerCategoryFactory;

    public EventHandler<Sport> onSportCreated = new EventHandler<>();
    public EventHandler<Sport> onSportUpdated = new EventHandler<>();

    @Inject
    public SportService(SportFactory sportFactory, PlayingSurfaceFactory playingSurfaceFactory, PlayerCategoryFactory playerCategoryFactory) {
        this.sportFactory = sportFactory;
        this.playingSurfaceFactory = playingSurfaceFactory;
        this.playerCategoryFactory = playerCategoryFactory;
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

    public Sport findSportByName(String name) {
        return sports.stream().filter(c -> c.getName().equals(name)).findFirst().get();
    }

    public void updatePlayingSurface(Sport sport, Double width, Double length, PlayingSurfaceUnit widthUnits, PlayingSurfaceUnit lengthUnits, String imageFileName) {
        PlayingSurface playingSurface = playingSurfaceFactory.create(width, length, widthUnits, lengthUnits, imageFileName);
        sport.setPlayingSurface(playingSurface);
    }

    public void addPlayerCategory(Sport sport, String name, Color allyColor, Color opponentColor, Integer defaultNumberOfPlayers) {
        sport.addPlayerCategory(playerCategoryFactory.create(name, allyColor, opponentColor, defaultNumberOfPlayers));
    }

    public void removePlayerCategory(Sport sport, PlayerCategory category) {
        sport.getPlayerCategories().remove(category);
    }

    public void updatePlayerCategory(Sport sport, PlayerCategory category, String name, Color allyColor, Color opponentColor, Integer defaultNumberOfPlayers) {
        category.setName(name);
        category.setAllyColor(allyColor);
        category.setOpponentColor(opponentColor);
        category.setDefaultNumberOfPlayers(defaultNumberOfPlayers);
    }

    public Set<Sport> getSports() {
        return sports;
    }
}
