package ca.ulaval.glo2004.visualigue.services;

import ca.ulaval.glo2004.visualigue.domain.playercategory.PlayerCategory;
import ca.ulaval.glo2004.visualigue.domain.playercategory.PlayerCategoryFactory;
import ca.ulaval.glo2004.visualigue.domain.playingsurface.PlayingSurface;
import ca.ulaval.glo2004.visualigue.domain.playingsurface.PlayingSurfaceFactory;
import ca.ulaval.glo2004.visualigue.domain.playingsurface.PlayingSurfaceUnit;
import ca.ulaval.glo2004.visualigue.domain.sport.Sport;
import ca.ulaval.glo2004.visualigue.domain.sport.SportAlreadyExistsException;
import ca.ulaval.glo2004.visualigue.domain.sport.SportFactory;
import ca.ulaval.glo2004.visualigue.domain.sport.SportRepository;
import ca.ulaval.glo2004.visualigue.utils.EventHandler;
import java.awt.Color;
import java.util.List;
import java.util.function.Function;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.swing.SortOrder;

@Singleton
public class SportService {

    private final SportRepository sportRepository;
    private final SportFactory sportFactory;
    private final PlayingSurfaceFactory playingSurfaceFactory;
    private final PlayerCategoryFactory playerCategoryFactory;

    public EventHandler<Sport> onSportCreated = new EventHandler<>();
    public EventHandler<Sport> onSportUpdated = new EventHandler<>();

    @Inject
    public SportService(final SportRepository sportRepository, final SportFactory sportFactory, final PlayingSurfaceFactory playingSurfaceFactory, final PlayerCategoryFactory playerCategoryFactory) {
        this.sportRepository = sportRepository;
        this.sportFactory = sportFactory;
        this.playingSurfaceFactory = playingSurfaceFactory;
        this.playerCategoryFactory = playerCategoryFactory;
    }

    public Sport createSport(String name) throws SportAlreadyExistsException {
        Sport sport = sportFactory.create(name);
        sportRepository.persist(sport);
        onSportCreated.fire(this, sport);
        return sport;
    }

    public void updateSport(Sport sport, String name) throws SportAlreadyExistsException {
        sport.setName(name);
        sportRepository.update(sport);
        onSportUpdated.fire(this, sport);
    }

    public void updatePlayingSurface(Sport sport, Double width, Double length, PlayingSurfaceUnit widthUnits, PlayingSurfaceUnit lengthUnits, String imageFileName) {
        PlayingSurface playingSurface = playingSurfaceFactory.create(width, length, widthUnits, lengthUnits, imageFileName);
        sport.setPlayingSurface(playingSurface);
        try {
            sportRepository.update(sport);
        } catch (SportAlreadyExistsException ex) {
            //Intentionally left blank.
        }
    }

    public void addPlayerCategory(Sport sport, String name, Color allyColor, Color opponentColor, Integer defaultNumberOfPlayers) {
        sport.addPlayerCategory(playerCategoryFactory.create(name, allyColor, opponentColor, defaultNumberOfPlayers));
        try {
            sportRepository.update(sport);
        } catch (SportAlreadyExistsException ex) {
            //Intentionally left blank.
        }
    }

    public void removePlayerCategory(Sport sport, PlayerCategory category) {
        sport.getPlayerCategories().remove(category);
        try {
            sportRepository.update(sport);
        } catch (SportAlreadyExistsException ex) {
            //Intentionally left blank.
        }
    }

    public void updatePlayerCategory(Sport sport, PlayerCategory category, String name, Color allyColor, Color opponentColor, Integer defaultNumberOfPlayers) {
        category.setName(name);
        category.setAllyColor(allyColor);
        category.setOpponentColor(opponentColor);
        category.setDefaultNumberOfPlayers(defaultNumberOfPlayers);
        try {
            sportRepository.update(sport);
        } catch (SportAlreadyExistsException ex) {
            //Intentionally left blank.
        }
    }

    public List<Sport> getSports(Function<Sport, Comparable> sortFunction, SortOrder sortOrder) {
        return sportRepository.getAll(sortFunction, sortOrder);
    }
}
