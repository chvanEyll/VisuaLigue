package ca.ulaval.glo2004.visualigue.contexts;

import ca.ulaval.glo2004.visualigue.domain.playercategory.PlayerCategory;
import ca.ulaval.glo2004.visualigue.domain.playingsurface.PlayingSurface;
import ca.ulaval.glo2004.visualigue.domain.playingsurface.PlayingSurfaceUnit;
import ca.ulaval.glo2004.visualigue.domain.resource.ResourceLocationType;
import ca.ulaval.glo2004.visualigue.domain.resource.LocatedResource;
import ca.ulaval.glo2004.visualigue.domain.sport.Sport;
import ca.ulaval.glo2004.visualigue.domain.sport.SportAlreadyExistsException;
import ca.ulaval.glo2004.visualigue.domain.sport.SportFactory;
import ca.ulaval.glo2004.visualigue.domain.sport.SportRepository;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javafx.scene.paint.Color;
import javax.inject.Inject;

public class DefaultContext extends ContextBase {

    private final SportFactory sportFactory;
    private final SportRepository sportRepository;

    @Inject
    public DefaultContext(final SportFactory sportFactory, final SportRepository sportRepository) {
        this.sportFactory = sportFactory;
        this.sportRepository = sportRepository;
    }

    @Override
    protected void applyFillers() throws Exception {
        if (!sportRepository.isEmpty()) {
            System.out.println("Repositories are not empty: Skipping default context repository filling.");
            return;
        }
        fill();
    }

    private void fill() throws Exception {
        createSports();

        Sport soccerSport = sportFactory.create("Soccer");
        soccerSport.setBuiltInIconPathName("/images/built-in-sport-icons/soccer-icon");
        Sport footballSport = sportFactory.create("Football");
        footballSport.setBuiltInIconPathName("/images/built-in-sport-icons/football-icon");

    }

    private void createSports() throws Exception {
        List<Sport> sportPool = new ArrayList<>();
        sportPool.add(createHockeySport());
        sportPool.add(createSoccerSport());
        sportPool.add(createFootballSport());
        persistSports(sportPool);
    }

    private Sport createHockeySport() {
        Sport sport = sportFactory.create("Hockey");
        sport.setBuiltInIconPathName("/images/built-in-sport-icons/hockey-icon");
        PlayingSurface playingSurface = new PlayingSurface(200.0, PlayingSurfaceUnit.FOOTS, 85.0, PlayingSurfaceUnit.FOOTS, new LocatedResource("/images/built-in-playing-surfaces/hockey.png", ResourceLocationType.CLASS_PATH));
        sport.setPlayingSurface(playingSurface);
        Set<PlayerCategory> playerCategories = new HashSet<>();
        playerCategories.add(new PlayerCategory("Gardien", Color.web("#F7931E"), Color.web("#F7931E"), 1));
        playerCategories.add(new PlayerCategory("Défenseur", Color.web("#0071BC"), Color.web("#C1272D"), 2));
        playerCategories.add(new PlayerCategory("Ailier Gauche", Color.web("#0071BC"), Color.web("#C1272D"), 1));
        playerCategories.add(new PlayerCategory("Ailier Droit", Color.web("#0071BC"), Color.web("#C1272D"), 1));
        playerCategories.add(new PlayerCategory("Centre", Color.web("#0071BC"), Color.web("#C1272D"), 1));
        sport.setPlayerCategories(playerCategories);
        return sport;
    }

    private Sport createSoccerSport() {
        Sport sport = sportFactory.create("Soccer");
        sport.setBuiltInIconPathName("/images/built-in-sport-icons/soccer-icon");
        PlayingSurface playingSurface = new PlayingSurface(68.0, PlayingSurfaceUnit.METER, 100.0, PlayingSurfaceUnit.METER, new LocatedResource("/images/built-in-playing-surfaces/soccer.jpg", ResourceLocationType.CLASS_PATH));
        sport.setPlayingSurface(playingSurface);
        Set<PlayerCategory> playerCategories = new HashSet<>();
        playerCategories.add(new PlayerCategory("Gardien", Color.web("#F7931E"), Color.web("#F7931E"), 1));
        playerCategories.add(new PlayerCategory("Défenseur Droit", Color.web("#0071BC"), Color.web("#C1272D"), 1));
        playerCategories.add(new PlayerCategory("Défenseur Gauche", Color.web("#0071BC"), Color.web("#C1272D"), 1));
        playerCategories.add(new PlayerCategory("Défenseur Central", Color.web("#0071BC"), Color.web("#C1272D"), 1));
        playerCategories.add(new PlayerCategory("Milieu Défensif Gauche", Color.web("#0071BC"), Color.web("#C1272D"), 1));
        playerCategories.add(new PlayerCategory("Milieu Défensif Droit", Color.web("#0071BC"), Color.web("#C1272D"), 1));
        playerCategories.add(new PlayerCategory("Milieu Défensif Central", Color.web("#0071BC"), Color.web("#C1272D"), 0));
        playerCategories.add(new PlayerCategory("Milieu Gauche", Color.web("#0071BC"), Color.web("#C1272D"), 0));
        playerCategories.add(new PlayerCategory("Milieu Droit", Color.web("#0071BC"), Color.web("#C1272D"), 0));
        playerCategories.add(new PlayerCategory("Milieu Central", Color.web("#0071BC"), Color.web("#C1272D"), 1));
        playerCategories.add(new PlayerCategory("Milieu Offensif Gauche", Color.web("#0071BC"), Color.web("#C1272D"), 1));
        playerCategories.add(new PlayerCategory("Milieu Offensif Droit", Color.web("#0071BC"), Color.web("#C1272D"), 1));
        playerCategories.add(new PlayerCategory("Milieu Offensif Axial", Color.web("#0071BC"), Color.web("#C1272D"), 0));
        playerCategories.add(new PlayerCategory("Attaquant Gauche", Color.web("#0071BC"), Color.web("#C1272D"), 1));
        playerCategories.add(new PlayerCategory("Attaquant Droit", Color.web("#0071BC"), Color.web("#C1272D"), 1));
        playerCategories.add(new PlayerCategory("Attaquant Axial", Color.web("#0071BC"), Color.web("#C1272D"), 1));
        sport.setPlayerCategories(playerCategories);
        return sport;
    }

    private Sport createFootballSport() {
        Sport sport = sportFactory.create("Football");
        sport.setBuiltInIconPathName("/images/built-in-sport-icons/football-icon");
        PlayingSurface playingSurface = new PlayingSurface(160.0, PlayingSurfaceUnit.FOOTS, 360.0, PlayingSurfaceUnit.FOOTS, new LocatedResource("/images/built-in-playing-surfaces/football.png", ResourceLocationType.CLASS_PATH));
        sport.setPlayingSurface(playingSurface);
        Set<PlayerCategory> playerCategories = new HashSet<>();
        playerCategories.add(new PlayerCategory("Safety", Color.web("#F7931E"), Color.web("#F7931E"), 2));
        playerCategories.add(new PlayerCategory("Cornerback", Color.web("#0071BC"), Color.web("#C1272D"), 2));
        playerCategories.add(new PlayerCategory("Outside Linebacker", Color.web("#0071BC"), Color.web("#C1272D"), 2));
        playerCategories.add(new PlayerCategory("Middle Linebacker", Color.web("#0071BC"), Color.web("#C1272D"), 1));
        playerCategories.add(new PlayerCategory("Defensive End", Color.web("#0071BC"), Color.web("#C1272D"), 2));
        playerCategories.add(new PlayerCategory("Defensive Tackle", Color.web("#0071BC"), Color.web("#C1272D"), 2));
        playerCategories.add(new PlayerCategory("Wide Receiver", Color.web("#0071BC"), Color.web("#C1272D"), 2));
        playerCategories.add(new PlayerCategory("Offensive Tackle", Color.web("#0071BC"), Color.web("#C1272D"), 2));
        playerCategories.add(new PlayerCategory("Offensive Guard", Color.web("#0071BC"), Color.web("#C1272D"), 2));
        playerCategories.add(new PlayerCategory("Center", Color.web("#0071BC"), Color.web("#C1272D"), 1));
        playerCategories.add(new PlayerCategory("Tight End", Color.web("#0071BC"), Color.web("#C1272D"), 1));
        playerCategories.add(new PlayerCategory("Quarterback", Color.web("#0071BC"), Color.web("#C1272D"), 1));
        playerCategories.add(new PlayerCategory("Fullback", Color.web("#0071BC"), Color.web("#C1272D"), 1));
        playerCategories.add(new PlayerCategory("Halfback", Color.web("#0071BC"), Color.web("#C1272D"), 1));
        playerCategories.add(new PlayerCategory("Running Back", Color.web("#0071BC"), Color.web("#C1272D"), 0));
        sport.setPlayerCategories(playerCategories);
        return sport;
    }

    private void persistSports(List<Sport> sportPool) throws SportAlreadyExistsException {
        for (Sport sport : sportPool) {
            sportRepository.persist(sport);
        }
    }
}
