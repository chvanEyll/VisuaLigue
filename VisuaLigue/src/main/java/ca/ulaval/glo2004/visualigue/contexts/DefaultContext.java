package ca.ulaval.glo2004.visualigue.contexts;

import ca.ulaval.glo2004.visualigue.domain.resource.PersistentResource;
import ca.ulaval.glo2004.visualigue.domain.playercategory.PlayerCategory;
import ca.ulaval.glo2004.visualigue.domain.playingsurface.PlayingSurface;
import ca.ulaval.glo2004.visualigue.domain.playingsurface.PlayingSurfaceUnit;
import ca.ulaval.glo2004.visualigue.domain.sport.Sport;
import ca.ulaval.glo2004.visualigue.domain.sport.SportAlreadyExistsException;
import ca.ulaval.glo2004.visualigue.domain.sport.SportFactory;
import ca.ulaval.glo2004.visualigue.domain.sport.SportRepository;
import java.awt.Color;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
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
        PlayingSurface playingSurface = new PlayingSurface(200.0, PlayingSurfaceUnit.FOOTS, 85.0, PlayingSurfaceUnit.FOOTS, PersistentResource.fromResource("/images/built-in-playing-surfaces/hockey.png"));
        sport.setPlayingSurface(playingSurface);
        Set<PlayerCategory> playerCategories = new HashSet<>();
        playerCategories.add(new PlayerCategory("Gardien", Color.decode("#F7931E"), Color.decode("#F7931E"), 1));
        playerCategories.add(new PlayerCategory("Défenseur", Color.decode("#0071BC"), Color.decode("#C1272D"), 2));
        playerCategories.add(new PlayerCategory("Ailier Gauche", Color.decode("#0071BC"), Color.decode("#C1272D"), 1));
        playerCategories.add(new PlayerCategory("Ailier Droit", Color.decode("#0071BC"), Color.decode("#C1272D"), 1));
        playerCategories.add(new PlayerCategory("Centre", Color.decode("#0071BC"), Color.decode("#C1272D"), 1));
        sport.setPlayerCategories(playerCategories);
        return sport;
    }

    private Sport createSoccerSport() {
        Sport sport = sportFactory.create("Soccer");
        sport.setBuiltInIconPathName("/images/built-in-sport-icons/soccer-icon");
        PlayingSurface playingSurface = new PlayingSurface(68.0, PlayingSurfaceUnit.METER, 100.0, PlayingSurfaceUnit.METER, PersistentResource.fromResource("/images/built-in-playing-surfaces/soccer.jpg"));
        sport.setPlayingSurface(playingSurface);
        Set<PlayerCategory> playerCategories = new HashSet<>();
        playerCategories.add(new PlayerCategory("Gardien", Color.decode("#F7931E"), Color.decode("#F7931E"), 1));
        playerCategories.add(new PlayerCategory("Défenseur Droit", Color.decode("#0071BC"), Color.decode("#C1272D"), 1));
        playerCategories.add(new PlayerCategory("Défenseur Gauche", Color.decode("#0071BC"), Color.decode("#C1272D"), 1));
        playerCategories.add(new PlayerCategory("Défenseur Central", Color.decode("#0071BC"), Color.decode("#C1272D"), 1));
        playerCategories.add(new PlayerCategory("Milieu Défensif Gauche", Color.decode("#0071BC"), Color.decode("#C1272D"), 1));
        playerCategories.add(new PlayerCategory("Milieu Défensif Droit", Color.decode("#0071BC"), Color.decode("#C1272D"), 1));
        playerCategories.add(new PlayerCategory("Milieu Défensif Central", Color.decode("#0071BC"), Color.decode("#C1272D"), 0));
        playerCategories.add(new PlayerCategory("Milieu Gauche", Color.decode("#0071BC"), Color.decode("#C1272D"), 0));
        playerCategories.add(new PlayerCategory("Milieu Droit", Color.decode("#0071BC"), Color.decode("#C1272D"), 0));
        playerCategories.add(new PlayerCategory("Milieu Central", Color.decode("#0071BC"), Color.decode("#C1272D"), 1));
        playerCategories.add(new PlayerCategory("Milieu Offensif Gauche", Color.decode("#0071BC"), Color.decode("#C1272D"), 1));
        playerCategories.add(new PlayerCategory("Milieu Offensif Droit", Color.decode("#0071BC"), Color.decode("#C1272D"), 1));
        playerCategories.add(new PlayerCategory("Milieu Offensif Axial", Color.decode("#0071BC"), Color.decode("#C1272D"), 0));
        playerCategories.add(new PlayerCategory("Attaquant Gauche", Color.decode("#0071BC"), Color.decode("#C1272D"), 1));
        playerCategories.add(new PlayerCategory("Attaquant Droit", Color.decode("#0071BC"), Color.decode("#C1272D"), 1));
        playerCategories.add(new PlayerCategory("Attaquant Axial", Color.decode("#0071BC"), Color.decode("#C1272D"), 1));
        sport.setPlayerCategories(playerCategories);
        return sport;
    }

    private Sport createFootballSport() {
        Sport sport = sportFactory.create("Football");
        sport.setBuiltInIconPathName("/images/built-in-sport-icons/football-icon");
        PlayingSurface playingSurface = new PlayingSurface(160.0, PlayingSurfaceUnit.FOOTS, 360.0, PlayingSurfaceUnit.FOOTS, PersistentResource.fromResource("/images/built-in-playing-surfaces/football.png"));
        sport.setPlayingSurface(playingSurface);
        Set<PlayerCategory> playerCategories = new HashSet<>();
        playerCategories.add(new PlayerCategory("Safety", Color.decode("#F7931E"), Color.decode("#F7931E"), 2));
        playerCategories.add(new PlayerCategory("Cornerback", Color.decode("#0071BC"), Color.decode("#C1272D"), 2));
        playerCategories.add(new PlayerCategory("Outside Linebacker", Color.decode("#0071BC"), Color.decode("#C1272D"), 2));
        playerCategories.add(new PlayerCategory("Middle Linebacker", Color.decode("#0071BC"), Color.decode("#C1272D"), 1));
        playerCategories.add(new PlayerCategory("Defensive End", Color.decode("#0071BC"), Color.decode("#C1272D"), 2));
        playerCategories.add(new PlayerCategory("Defensive Tackle", Color.decode("#0071BC"), Color.decode("#C1272D"), 2));
        playerCategories.add(new PlayerCategory("Wide Receiver", Color.decode("#0071BC"), Color.decode("#C1272D"), 2));
        playerCategories.add(new PlayerCategory("Offensive Tackle", Color.decode("#0071BC"), Color.decode("#C1272D"), 2));
        playerCategories.add(new PlayerCategory("Offensive Guard", Color.decode("#0071BC"), Color.decode("#C1272D"), 2));
        playerCategories.add(new PlayerCategory("Center", Color.decode("#0071BC"), Color.decode("#C1272D"), 1));
        playerCategories.add(new PlayerCategory("Tight End", Color.decode("#0071BC"), Color.decode("#C1272D"), 1));
        playerCategories.add(new PlayerCategory("Quarterback", Color.decode("#0071BC"), Color.decode("#C1272D"), 1));
        playerCategories.add(new PlayerCategory("Fullback", Color.decode("#0071BC"), Color.decode("#C1272D"), 1));
        playerCategories.add(new PlayerCategory("Halfback", Color.decode("#0071BC"), Color.decode("#C1272D"), 1));
        playerCategories.add(new PlayerCategory("Running Back", Color.decode("#0071BC"), Color.decode("#C1272D"), 0));
        sport.setPlayerCategories(playerCategories);
        return sport;
    }

    private void persistSports(List<Sport> sportPool) throws SportAlreadyExistsException {
        for (Sport sport : sportPool) {
            sportRepository.persist(sport);
        }
    }
}
