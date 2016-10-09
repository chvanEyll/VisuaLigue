package ca.ulaval.glo2004.visualigue.contexts;

import ca.ulaval.glo2004.visualigue.domain.image.ImageRepository;
import ca.ulaval.glo2004.visualigue.domain.playercategory.PlayerCategory;
import ca.ulaval.glo2004.visualigue.domain.playingsurface.PlayingSurface;
import ca.ulaval.glo2004.visualigue.domain.playingsurface.PlayingSurfaceUnit;
import ca.ulaval.glo2004.visualigue.domain.sport.Sport;
import ca.ulaval.glo2004.visualigue.domain.sport.SportAlreadyExistsException;
import ca.ulaval.glo2004.visualigue.domain.sport.SportFactory;
import ca.ulaval.glo2004.visualigue.domain.sport.SportRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import javafx.scene.paint.Color;
import javax.inject.Inject;

public class DefaultContext extends ContextBase {

    private final SportFactory sportFactory;
    private final SportRepository sportRepository;
    private final ImageRepository imageRepository;

    @Inject
    public DefaultContext(final SportFactory sportFactory, final SportRepository sportRepository, final ImageRepository imageRepository) {
        this.sportFactory = sportFactory;
        this.sportRepository = sportRepository;
        this.imageRepository = imageRepository;
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

    private Sport createHockeySport() throws Exception {
        Sport sport = sportFactory.create("Hockey");
        sport.setBuiltInIconPathName("/images/built-in-sport-icons/hockey-icon");
        UUID playingSurfaceUUID = imageRepository.persistFromResource("/images/built-in-playing-surfaces/hockey.png");
        PlayingSurface playingSurface = new PlayingSurface(200.0, PlayingSurfaceUnit.FOOTS, 85.0, PlayingSurfaceUnit.FOOTS, playingSurfaceUUID);
        sport.setPlayingSurface(playingSurface);
        sport.addPlayerCategory(new PlayerCategory("Gardien", Color.web("#F7931E"), Color.web("#F7931E"), 1));
        sport.addPlayerCategory(new PlayerCategory("Défenseur", Color.web("#0071BC"), Color.web("#C1272D"), 2));
        sport.addPlayerCategory(new PlayerCategory("Ailier Gauche", Color.web("#0071BC"), Color.web("#C1272D"), 1));
        sport.addPlayerCategory(new PlayerCategory("Ailier Droit", Color.web("#0071BC"), Color.web("#C1272D"), 1));
        sport.addPlayerCategory(new PlayerCategory("Centre", Color.web("#0071BC"), Color.web("#C1272D"), 1));
        return sport;
    }

    private Sport createSoccerSport() throws Exception {
        Sport sport = sportFactory.create("Soccer");
        sport.setBuiltInIconPathName("/images/built-in-sport-icons/soccer-icon");
        UUID playingSurfaceUUID = imageRepository.persistFromResource("/images/built-in-playing-surfaces/soccer.jpg");
        PlayingSurface playingSurface = new PlayingSurface(68.0, PlayingSurfaceUnit.METER, 100.0, PlayingSurfaceUnit.METER, playingSurfaceUUID);
        sport.setPlayingSurface(playingSurface);
        sport.addPlayerCategory(new PlayerCategory("Gardien", Color.web("#F7931E"), Color.web("#F7931E"), 1));
        sport.addPlayerCategory(new PlayerCategory("Défenseur Droit", Color.web("#0071BC"), Color.web("#C1272D"), 1));
        sport.addPlayerCategory(new PlayerCategory("Défenseur Gauche", Color.web("#0071BC"), Color.web("#C1272D"), 1));
        sport.addPlayerCategory(new PlayerCategory("Défenseur Central", Color.web("#0071BC"), Color.web("#C1272D"), 1));
        sport.addPlayerCategory(new PlayerCategory("Milieu Défensif Gauche", Color.web("#0071BC"), Color.web("#C1272D"), 1));
        sport.addPlayerCategory(new PlayerCategory("Milieu Défensif Droit", Color.web("#0071BC"), Color.web("#C1272D"), 1));
        sport.addPlayerCategory(new PlayerCategory("Milieu Défensif Central", Color.web("#0071BC"), Color.web("#C1272D"), 0));
        sport.addPlayerCategory(new PlayerCategory("Milieu Gauche", Color.web("#0071BC"), Color.web("#C1272D"), 0));
        sport.addPlayerCategory(new PlayerCategory("Milieu Droit", Color.web("#0071BC"), Color.web("#C1272D"), 0));
        sport.addPlayerCategory(new PlayerCategory("Milieu Central", Color.web("#0071BC"), Color.web("#C1272D"), 1));
        sport.addPlayerCategory(new PlayerCategory("Milieu Offensif Gauche", Color.web("#0071BC"), Color.web("#C1272D"), 1));
        sport.addPlayerCategory(new PlayerCategory("Milieu Offensif Droit", Color.web("#0071BC"), Color.web("#C1272D"), 1));
        sport.addPlayerCategory(new PlayerCategory("Milieu Offensif Axial", Color.web("#0071BC"), Color.web("#C1272D"), 0));
        sport.addPlayerCategory(new PlayerCategory("Attaquant Gauche", Color.web("#0071BC"), Color.web("#C1272D"), 1));
        sport.addPlayerCategory(new PlayerCategory("Attaquant Droit", Color.web("#0071BC"), Color.web("#C1272D"), 1));
        sport.addPlayerCategory(new PlayerCategory("Attaquant Axial", Color.web("#0071BC"), Color.web("#C1272D"), 1));
        return sport;
    }

    private Sport createFootballSport() throws Exception {
        Sport sport = sportFactory.create("Football");
        sport.setBuiltInIconPathName("/images/built-in-sport-icons/football-icon");
        UUID playingSurfaceUUID = imageRepository.persistFromResource("/images/built-in-playing-surfaces/football.png");
        PlayingSurface playingSurface = new PlayingSurface(160.0, PlayingSurfaceUnit.FOOTS, 360.0, PlayingSurfaceUnit.FOOTS, playingSurfaceUUID);
        sport.setPlayingSurface(playingSurface);
        sport.addPlayerCategory(new PlayerCategory("Safety", Color.web("#F7931E"), Color.web("#F7931E"), 2));
        sport.addPlayerCategory(new PlayerCategory("Cornerback", Color.web("#0071BC"), Color.web("#C1272D"), 2));
        sport.addPlayerCategory(new PlayerCategory("Outside Linebacker", Color.web("#0071BC"), Color.web("#C1272D"), 2));
        sport.addPlayerCategory(new PlayerCategory("Middle Linebacker", Color.web("#0071BC"), Color.web("#C1272D"), 1));
        sport.addPlayerCategory(new PlayerCategory("Defensive End", Color.web("#0071BC"), Color.web("#C1272D"), 2));
        sport.addPlayerCategory(new PlayerCategory("Defensive Tackle", Color.web("#0071BC"), Color.web("#C1272D"), 2));
        sport.addPlayerCategory(new PlayerCategory("Wide Receiver", Color.web("#0071BC"), Color.web("#C1272D"), 2));
        sport.addPlayerCategory(new PlayerCategory("Offensive Tackle", Color.web("#0071BC"), Color.web("#C1272D"), 2));
        sport.addPlayerCategory(new PlayerCategory("Offensive Guard", Color.web("#0071BC"), Color.web("#C1272D"), 2));
        sport.addPlayerCategory(new PlayerCategory("Center", Color.web("#0071BC"), Color.web("#C1272D"), 1));
        sport.addPlayerCategory(new PlayerCategory("Tight End", Color.web("#0071BC"), Color.web("#C1272D"), 1));
        sport.addPlayerCategory(new PlayerCategory("Quarterback", Color.web("#0071BC"), Color.web("#C1272D"), 1));
        sport.addPlayerCategory(new PlayerCategory("Fullback", Color.web("#0071BC"), Color.web("#C1272D"), 1));
        sport.addPlayerCategory(new PlayerCategory("Halfback", Color.web("#0071BC"), Color.web("#C1272D"), 1));
        sport.addPlayerCategory(new PlayerCategory("Running Back", Color.web("#0071BC"), Color.web("#C1272D"), 0));
        return sport;
    }

    private void persistSports(List<Sport> sportPool) throws SportAlreadyExistsException {
        for (Sport sport : sportPool) {
            sportRepository.persist(sport);
        }
    }
}
