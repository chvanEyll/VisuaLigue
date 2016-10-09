package ca.ulaval.glo2004.visualigue.services;

import ca.ulaval.glo2004.visualigue.domain.image.ImageRepository;
import ca.ulaval.glo2004.visualigue.domain.playercategory.PlayerCategory;
import ca.ulaval.glo2004.visualigue.domain.playercategory.PlayerCategoryFactory;
import ca.ulaval.glo2004.visualigue.domain.playingsurface.PlayingSurface;
import ca.ulaval.glo2004.visualigue.domain.playingsurface.PlayingSurfaceUnit;
import ca.ulaval.glo2004.visualigue.domain.resource.LocatedResource;
import ca.ulaval.glo2004.visualigue.domain.resource.ResourceLocationType;
import ca.ulaval.glo2004.visualigue.domain.sport.Sport;
import ca.ulaval.glo2004.visualigue.domain.sport.SportAlreadyExistsException;
import ca.ulaval.glo2004.visualigue.domain.sport.SportFactory;
import ca.ulaval.glo2004.visualigue.domain.sport.SportRepository;
import ca.ulaval.glo2004.visualigue.utils.EventHandler;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.List;
import java.util.function.Function;
import javafx.scene.paint.Color;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.swing.SortOrder;

@Singleton
public class SportService {

    private final SportRepository sportRepository;
    private final ImageRepository imageRepository;
    private final SportFactory sportFactory;
    private final PlayerCategoryFactory playerCategoryFactory;

    public EventHandler<Sport> onSportCreated = new EventHandler<>();
    public EventHandler<Sport> onSportUpdated = new EventHandler<>();

    @Inject
    public SportService(final SportRepository sportRepository, final ImageRepository imageRepository, final SportFactory sportFactory, final PlayerCategoryFactory playerCategoryFactory) {
        this.sportRepository = sportRepository;
        this.imageRepository = imageRepository;
        this.sportFactory = sportFactory;
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

    public void updatePlayingSurface(Sport sport, Double width, Double length, PlayingSurfaceUnit widthUnits, PlayingSurfaceUnit lengthUnits) {
        PlayingSurface playingSurface = sport.getPlayingSurface();
        playingSurface.setWidth(width);
        playingSurface.setLength(length);
        playingSurface.setWidthUnits(widthUnits);
        playingSurface.setLengthUnits(lengthUnits);
        try {
            sportRepository.update(sport);
        } catch (SportAlreadyExistsException ex) {
            //Intentionally left blank.
        }
    }

    public void updatePlayingSurfaceImage(Sport sport, BufferedImage image) {
        File file = imageRepository.persist(image);
        LocatedResource imageResource = new LocatedResource(file.getPath(), ResourceLocationType.EXTERNAL);
        PlayingSurface playingSurface = sport.getPlayingSurface();
        if (playingSurface.getImageResource().isExternalResource()) {
            imageRepository.delete(playingSurface.getImageResource().getName());
        }
        playingSurface.setImageResource(imageResource);
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
