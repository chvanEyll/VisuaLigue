package ca.ulaval.glo2004.visualigue.services.sport;

import ca.ulaval.glo2004.visualigue.domain.image.ImageRepository;
import ca.ulaval.glo2004.visualigue.domain.play.actorinstance.TeamSide;
import ca.ulaval.glo2004.visualigue.domain.sport.*;
import ca.ulaval.glo2004.visualigue.domain.sport.ball.Ball;
import ca.ulaval.glo2004.visualigue.domain.sport.playercategory.PlayerCategory;
import ca.ulaval.glo2004.visualigue.domain.sport.playercategory.PlayerCategoryFactory;
import ca.ulaval.glo2004.visualigue.domain.sport.playercategory.PlayerCategoryNotFoundException;
import ca.ulaval.glo2004.visualigue.domain.sport.playercategory.PlayerCategoryRepository;
import ca.ulaval.glo2004.visualigue.domain.sport.playingsurface.PlayingSurface;
import ca.ulaval.glo2004.visualigue.domain.sport.playingsurface.PlayingSurfaceUnit;
import ca.ulaval.glo2004.visualigue.utils.EventHandler;
import ca.ulaval.glo2004.visualigue.utils.ListUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import javafx.scene.paint.Color;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.swing.SortOrder;

@Singleton
public class SportService {

    private final SportRepository sportRepository;
    private final PlayerCategoryRepository playerCategoryRepository;
    private final ImageRepository imageRepository;
    private final SportFactory sportFactory;
    private final PlayerCategoryFactory playerCategoryFactory;

    public EventHandler<Sport> onSportCreated = new EventHandler();
    public EventHandler<Sport> onSportUpdated = new EventHandler();
    public EventHandler<Sport> onSportDeleted = new EventHandler();

    @Inject
    public SportService(final SportRepository sportRepository, final PlayerCategoryRepository playerCategoryRepository, final ImageRepository imageRepository, final SportFactory sportFactory, final PlayerCategoryFactory playerCategoryFactory) {
        this.sportRepository = sportRepository;
        this.playerCategoryRepository = playerCategoryRepository;
        this.imageRepository = imageRepository;
        this.sportFactory = sportFactory;
        this.playerCategoryFactory = playerCategoryFactory;
    }

    public String createSport(String name) throws SportAlreadyExistsException {
        Sport sport = sportFactory.create(name);
        sportRepository.persist(sport);
        onSportCreated.fire(this, sport);
        return sport.getUUID();
    }

    public void updateSport(String sportUUID, String name) throws SportAlreadyExistsException, SportNotFoundException {
        Sport sport = sportRepository.get(sportUUID);
        sport.setName(name);
        sportRepository.update(sport);
        onSportUpdated.fire(this, sport);
    }

    public void updateIcon(String sportUUID, String sourceImagePathName) throws SportNotFoundException {
        Sport sport = sportRepository.get(sportUUID);
        String imageUuid = imageRepository.replace(sport.getCustomIconUUID(), sourceImagePathName);
        sport.setCustomIconUUID(imageUuid);
        sportRepository.update(sport);
        onSportUpdated.fire(this, sport);
    }

    public void updateBall(String sportUUID, String name) throws SportNotFoundException {
        Sport sport = sportRepository.get(sportUUID);
        Ball ball = sport.getBall();
        ball.setName(name);
        sportRepository.update(sport);
    }

    public void updateBallImage(String sportUUID, String sourceImagePathName) throws SportNotFoundException {
        Sport sport = sportRepository.get(sportUUID);
        Ball ball = sport.getBall();
        String imageUuid = imageRepository.replace(ball.getCustomImageUUID(), sourceImagePathName);
        ball.setCustomImageUUID(imageUuid);
        sportRepository.update(sport);
    }

    public void updatePlayingSurface(String sportUUID, Double width, Double length, PlayingSurfaceUnit widthUnits, PlayingSurfaceUnit lengthUnits) throws SportNotFoundException {
        Sport sport = sportRepository.get(sportUUID);
        PlayingSurface playingSurface = sport.getPlayingSurface();
        playingSurface.setWidth(width);
        playingSurface.setLength(length);
        playingSurface.setWidthUnits(widthUnits);
        playingSurface.setLengthUnits(lengthUnits);
        sportRepository.update(sport);
    }

    public void updatePlayingSurfaceImage(String sportUUID, String sourceImagePathName) throws SportNotFoundException {
        Sport sport = sportRepository.get(sportUUID);
        PlayingSurface playingSurface = sport.getPlayingSurface();
        String imageUuid = imageRepository.replace(playingSurface.getCustomImageUUID(), sourceImagePathName);
        playingSurface.setCustomImageUUID(imageUuid);
        sportRepository.update(sport);
    }

    public void deleteSport(String sportUUID) throws SportNotFoundException {
        Sport sport = sportRepository.get(sportUUID);
        sportRepository.delete(sport);
        onSportDeleted.fire(this, sport);
    }

    public void addPlayerCategory(String sportUUID, String name, String abbreviation, Color allyColor, Color opponentColor, Integer defaultNumberOfPlayers) throws SportNotFoundException {
        Sport sport = sportRepository.get(sportUUID);
        PlayerCategory playerCategory = playerCategoryFactory.create(name, abbreviation, allyColor, opponentColor, defaultNumberOfPlayers);
        playerCategoryRepository.persist(playerCategory);
        sport.addPlayerCategory(playerCategory);
        sportRepository.update(sport);
    }

    public void simulatePlayerCategoryDeletion(String playerCategoryUUID) {
        PlayerCategory playerCategory = playerCategoryRepository.get(playerCategoryUUID);
        playerCategoryRepository.simulateDelete(playerCategory);
    }

    public void deletePlayerCategory(String sportUUID, String playerCategoryUUID) throws SportNotFoundException, PlayerCategoryNotFoundException {
        Sport sport = sportRepository.get(sportUUID);
        PlayerCategory playerCategory = playerCategoryRepository.get(playerCategoryUUID);
        sport.removePlayerCategory(playerCategory);
        sportRepository.update(sport);
        playerCategoryRepository.delete(playerCategory);
    }

    public void updatePlayerCategory(String sportUUID, String playerCategoryUUID, String name, Color allyColor, Color opponentColor, Integer defaultNumberOfPlayers) throws SportNotFoundException {
        Sport sport = sportRepository.get(sportUUID);
        PlayerCategory playerCategory = playerCategoryRepository.get(playerCategoryUUID);
        playerCategory.setName(name);
        playerCategory.setColor(allyColor, TeamSide.ALLIES);
        playerCategory.setColor(opponentColor, TeamSide.OPPONENTS);
        playerCategory.setDefaultNumberOfPlayers(defaultNumberOfPlayers);
        sportRepository.update(sport);
        playerCategoryRepository.update(playerCategory);
    }

    public List<PlayerCategory> getPlayerCategories(String sportUUID, Function<PlayerCategory, Comparable> sortFunction, SortOrder sortOrder) throws SportNotFoundException {
        Sport sport = sportRepository.get(sportUUID);
        List<PlayerCategory> playerCategories = new ArrayList(sport.getPlayerCategories());
        return ListUtils.sort(playerCategories, sortFunction, sortOrder);
    }

    public Sport getSport(String sportUUID) throws SportNotFoundException {
        return sportRepository.get(sportUUID);
    }

    public List<Sport> getSports(Function<Sport, Comparable> sortFunction, SortOrder sortOrder) {
        return sportRepository.getAll(sortFunction, sortOrder);
    }
}
