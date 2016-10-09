package ca.ulaval.glo2004.visualigue.services;

import ca.ulaval.glo2004.visualigue.domain.image.ImageRepository;
import ca.ulaval.glo2004.visualigue.domain.playercategory.PlayerCategory;
import ca.ulaval.glo2004.visualigue.domain.playercategory.PlayerCategoryFactory;
import ca.ulaval.glo2004.visualigue.domain.playingsurface.PlayingSurface;
import ca.ulaval.glo2004.visualigue.domain.playingsurface.PlayingSurfaceUnit;
import ca.ulaval.glo2004.visualigue.domain.sport.*;
import ca.ulaval.glo2004.visualigue.utils.EventHandler;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.UUID;
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

    public UUID createSport(String name) throws SportAlreadyExistsException {
        Sport sport = sportFactory.create(name);
        sportRepository.persist(sport);
        onSportCreated.fire(this, sport);
        return sport.getUUID();
    }

    public void updateSport(UUID sportUUID, String name) throws SportAlreadyExistsException, SportNotFoundException {
        Sport sport = sportRepository.get(sportUUID);
        sport.setName(name);
        sportRepository.update(sport);
        onSportUpdated.fire(this, sport);
    }

    public void updatePlayingSurface(UUID sportUUID, Double width, Double length, PlayingSurfaceUnit widthUnits, PlayingSurfaceUnit lengthUnits) throws SportNotFoundException, SportAlreadyExistsException {
        Sport sport = sportRepository.get(sportUUID);
        PlayingSurface playingSurface = sport.getPlayingSurface();
        playingSurface.setWidth(width);
        playingSurface.setLength(length);
        playingSurface.setWidthUnits(widthUnits);
        playingSurface.setLengthUnits(lengthUnits);
        sportRepository.update(sport);
    }

    public void updatePlayingSurfaceImage(UUID sportUUID, BufferedImage image) throws SportNotFoundException, SportAlreadyExistsException {
        Sport sport = sportRepository.get(sportUUID);
        UUID imageUuid = imageRepository.persist(image);
        PlayingSurface playingSurface = sport.getPlayingSurface();
        playingSurface.setImageUUID(imageUuid);
        if (playingSurface.hasImage()) {
            imageRepository.delete(playingSurface.getImageUUID());
        }
        sportRepository.update(sport);
    }

    public void addPlayerCategory(UUID sportUUID, String name, Color allyColor, Color opponentColor, Integer defaultNumberOfPlayers) throws SportAlreadyExistsException, SportNotFoundException {
        Sport sport = sportRepository.get(sportUUID);
        sport.addPlayerCategory(playerCategoryFactory.create(name, allyColor, opponentColor, defaultNumberOfPlayers));
        sportRepository.update(sport);
    }

    public void removePlayerCategory(UUID sportUUID, UUID playerCategoryUUID) throws SportAlreadyExistsException, SportNotFoundException {
        Sport sport = sportRepository.get(sportUUID);
        sport.removeCategory(playerCategoryUUID);
        sportRepository.update(sport);
    }

    public void updatePlayerCategory(UUID sportUUID, UUID playerCategoryUUID, String name, Color allyColor, Color opponentColor, Integer defaultNumberOfPlayers) throws SportNotFoundException, SportAlreadyExistsException {
        Sport sport = sportRepository.get(sportUUID);
        PlayerCategory playerCategory = sport.getPlayerCategory(playerCategoryUUID);
        playerCategory.setName(name);
        playerCategory.setAllyColor(allyColor);
        playerCategory.setOpponentColor(opponentColor);
        playerCategory.setDefaultNumberOfPlayers(defaultNumberOfPlayers);
        sportRepository.update(sport);
    }

    public Sport getSport(UUID sportUUID) throws SportNotFoundException {
        return sportRepository.get(sportUUID);
    }

    public List<Sport> getSports(Function<Sport, Comparable> sortFunction, SortOrder sortOrder) {
        return sportRepository.getAll(sortFunction, sortOrder);
    }
}
