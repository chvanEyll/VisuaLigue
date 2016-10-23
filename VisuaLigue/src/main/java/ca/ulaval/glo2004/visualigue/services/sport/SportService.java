package ca.ulaval.glo2004.visualigue.services.sport;

import ca.ulaval.glo2004.visualigue.domain.image.ImageRepository;
import ca.ulaval.glo2004.visualigue.domain.sport.*;
import ca.ulaval.glo2004.visualigue.domain.sport.ball.Ball;
import ca.ulaval.glo2004.visualigue.domain.sport.playercategory.PlayerCategory;
import ca.ulaval.glo2004.visualigue.domain.sport.playercategory.PlayerCategoryFactory;
import ca.ulaval.glo2004.visualigue.domain.sport.playingsurface.PlayingSurface;
import ca.ulaval.glo2004.visualigue.domain.sport.playingsurface.PlayingSurfaceUnit;
import ca.ulaval.glo2004.visualigue.utils.EventHandler;
import ca.ulaval.glo2004.visualigue.utils.ListUtils;
import java.util.ArrayList;
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

    public EventHandler<Sport> onSportCreated = new EventHandler();
    public EventHandler<Sport> onSportUpdated = new EventHandler();
    public EventHandler<Sport> onSportDeleted = new EventHandler();

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

    public void updateIcon(UUID sportUUID, String sourceImagePathName) throws SportNotFoundException {
        Sport sport = sportRepository.get(sportUUID);
        UUID imageUuid = imageRepository.replace(sport.getCustomIconUUID(), sourceImagePathName);
        sport.setCustomIconUUID(imageUuid);
        try {
            sportRepository.update(sport);
        } catch (SportAlreadyExistsException ex) {
            throw new RuntimeException(ex);
        }
    }

    public void updateBall(UUID sportUUID, String name) throws SportNotFoundException {
        Sport sport = sportRepository.get(sportUUID);
        Ball ball = sport.getBall();
        ball.setName(name);
        try {
            sportRepository.update(sport);
        } catch (SportAlreadyExistsException ex) {
            throw new RuntimeException(ex);
        }
    }

    public void updateBallImage(UUID sportUUID, String sourceImagePathName) throws SportNotFoundException {
        Sport sport = sportRepository.get(sportUUID);
        Ball ball = sport.getBall();
        UUID imageUuid = imageRepository.replace(ball.getCustomImageUUID(), sourceImagePathName);
        ball.setCustomImageUUID(imageUuid);
        try {
            sportRepository.update(sport);
        } catch (SportAlreadyExistsException ex) {
            throw new RuntimeException(ex);
        }
    }

    public void updatePlayingSurface(UUID sportUUID, Double width, Double length, PlayingSurfaceUnit widthUnits, PlayingSurfaceUnit lengthUnits) throws SportNotFoundException {
        Sport sport = sportRepository.get(sportUUID);
        PlayingSurface playingSurface = sport.getPlayingSurface();
        playingSurface.setWidth(width);
        playingSurface.setLength(length);
        playingSurface.setWidthUnits(widthUnits);
        playingSurface.setLengthUnits(lengthUnits);
        try {
            sportRepository.update(sport);
        } catch (SportAlreadyExistsException ex) {
            throw new RuntimeException(ex);
        }
    }

    public void updatePlayingSurfaceImage(UUID sportUUID, String sourceImagePathName) throws SportNotFoundException {
        Sport sport = sportRepository.get(sportUUID);
        PlayingSurface playingSurface = sport.getPlayingSurface();
        UUID imageUuid = imageRepository.replace(playingSurface.getCustomImageUUID(), sourceImagePathName);
        playingSurface.setCustomImageUUID(imageUuid);
        try {
            sportRepository.update(sport);
        } catch (SportAlreadyExistsException ex) {
            throw new RuntimeException(ex);
        }
    }

    public void deleteSport(UUID sportUUID) throws SportNotFoundException {
        Sport sport = sportRepository.get(sportUUID);
        sportRepository.delete(sport);
        onSportDeleted.fire(this, sport);
    }

    public void addPlayerCategory(UUID sportUUID, String name, String abbreviation, Color allyColor, Color opponentColor, Integer defaultNumberOfPlayers) throws SportNotFoundException {
        Sport sport = sportRepository.get(sportUUID);
        sport.addPlayerCategory(playerCategoryFactory.create(name, abbreviation, allyColor, opponentColor, defaultNumberOfPlayers));
        try {
            sportRepository.update(sport);
        } catch (SportAlreadyExistsException ex) {
            throw new RuntimeException(ex);
        }
    }

    public void removePlayerCategory(UUID sportUUID, UUID playerCategoryUUID) throws SportNotFoundException {
        Sport sport = sportRepository.get(sportUUID);
        sport.removeCategory(playerCategoryUUID);
        try {
            sportRepository.update(sport);
        } catch (SportAlreadyExistsException ex) {
            throw new RuntimeException(ex);
        }
    }

    public void updatePlayerCategory(UUID sportUUID, UUID playerCategoryUUID, String name, Color allyColor, Color opponentColor, Integer defaultNumberOfPlayers) throws SportNotFoundException {
        Sport sport = sportRepository.get(sportUUID);
        PlayerCategory playerCategory = sport.getPlayerCategory(playerCategoryUUID);
        playerCategory.setName(name);
        playerCategory.setAllyColor(allyColor);
        playerCategory.setOpponentColor(opponentColor);
        playerCategory.setDefaultNumberOfPlayers(defaultNumberOfPlayers);
        try {
            sportRepository.update(sport);
        } catch (SportAlreadyExistsException ex) {
            throw new RuntimeException(ex);
        }
    }

    public List<PlayerCategory> getPlayerCategories(UUID sportUUID, Function<PlayerCategory, Comparable> sortFunction, SortOrder sortOrder) throws SportNotFoundException {
        Sport sport = sportRepository.get(sportUUID);
        List<PlayerCategory> playerCategories = new ArrayList(sport.getPlayerCategories().values());
        return ListUtils.sort(playerCategories, sortFunction, sortOrder);
    }

    public Sport getSport(UUID sportUUID) throws SportNotFoundException {
        return sportRepository.get(sportUUID);
    }

    public List<Sport> getSports(Function<Sport, Comparable> sortFunction, SortOrder sortOrder) {
        return sportRepository.getAll(sortFunction, sortOrder);
    }
}
