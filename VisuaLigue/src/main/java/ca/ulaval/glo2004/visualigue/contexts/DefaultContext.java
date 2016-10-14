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
        sport.setBuiltInIconPathName("/images/built-in-sport-icons/hockey-icon.fxml");
        PlayingSurface playingSurface = new PlayingSurface(200.0, PlayingSurfaceUnit.FOOTS, 85.0, PlayingSurfaceUnit.FOOTS);
        playingSurface.setBuiltInImagePathName("/images/built-in-playing-surfaces/hockey.png");
        sport.setPlayingSurface(playingSurface);
        sport.addPlayerCategory(new PlayerCategory("Gardien", "G", Color.web("#E6994D"), Color.web("#E6994D"), 1));
        sport.addPlayerCategory(new PlayerCategory("Défenseur", "D", Color.web("#001A80"), Color.web("#990000"), 2));
        sport.addPlayerCategory(new PlayerCategory("Attaquant", "A", Color.web("#001A80"), Color.web("#990000"), 2));
        sport.addPlayerCategory(new PlayerCategory("Ailier Gauche", "AG", Color.web("#001A80"), Color.web("#990000"), 1));
        sport.addPlayerCategory(new PlayerCategory("Ailier Droit", "AD", Color.web("#001A80"), Color.web("#990000"), 1));
        sport.addPlayerCategory(new PlayerCategory("Centre", "C", Color.web("#001A80"), Color.web("#990000"), 1));
        return sport;
    }

    private Sport createSoccerSport() throws Exception {
        Sport sport = sportFactory.create("Soccer");
        sport.setBuiltInIconPathName("/images/built-in-sport-icons/soccer-icon.fxml");
        PlayingSurface playingSurface = new PlayingSurface(68.0, PlayingSurfaceUnit.METER, 100.0, PlayingSurfaceUnit.METER);
        playingSurface.setBuiltInImagePathName("/images/built-in-playing-surfaces/soccer.jpg");
        sport.setPlayingSurface(playingSurface);
        sport.addPlayerCategory(new PlayerCategory("Gardien", "G", Color.web("#E6994D"), Color.web("#E6994D"), 1));
        sport.addPlayerCategory(new PlayerCategory("Défenseur", "D", Color.web("#001A80"), Color.web("#990000"), 1));
        sport.addPlayerCategory(new PlayerCategory("Défenseur Droit", "DD", Color.web("#001A80"), Color.web("#990000"), 1));
        sport.addPlayerCategory(new PlayerCategory("Défenseur Gauche", "DG", Color.web("#001A80"), Color.web("#990000"), 1));
        sport.addPlayerCategory(new PlayerCategory("Défenseur Central", "DC", Color.web("#001A80"), Color.web("#990000"), 1));
        sport.addPlayerCategory(new PlayerCategory("Milieu", "M", Color.web("#001A80"), Color.web("#990000"), 1));
        sport.addPlayerCategory(new PlayerCategory("Milieu Défensif Gauche", "MDG", Color.web("#001A80"), Color.web("#990000"), 1));
        sport.addPlayerCategory(new PlayerCategory("Milieu Défensif Droit", "MDD", Color.web("#001A80"), Color.web("#990000"), 1));
        sport.addPlayerCategory(new PlayerCategory("Milieu Défensif Central", "MDC", Color.web("#001A80"), Color.web("#990000"), 0));
        sport.addPlayerCategory(new PlayerCategory("Milieu Gauche", "MG", Color.web("#001A80"), Color.web("#990000"), 0));
        sport.addPlayerCategory(new PlayerCategory("Milieu Droit", "MD", Color.web("#001A80"), Color.web("#990000"), 0));
        sport.addPlayerCategory(new PlayerCategory("Milieu Central", "MC", Color.web("#001A80"), Color.web("#990000"), 1));
        sport.addPlayerCategory(new PlayerCategory("Milieu Offensif Gauche", "MOG", Color.web("#001A80"), Color.web("#990000"), 1));
        sport.addPlayerCategory(new PlayerCategory("Milieu Offensif Droit", "MOD", Color.web("#001A80"), Color.web("#990000"), 1));
        sport.addPlayerCategory(new PlayerCategory("Milieu Offensif Axial", "MOA", Color.web("#001A80"), Color.web("#990000"), 0));
        sport.addPlayerCategory(new PlayerCategory("Attaquant", "A", Color.web("#001A80"), Color.web("#990000"), 1));
        sport.addPlayerCategory(new PlayerCategory("Attaquant Gauche", "AG", Color.web("#001A80"), Color.web("#990000"), 1));
        sport.addPlayerCategory(new PlayerCategory("Attaquant Droit", "AD", Color.web("#001A80"), Color.web("#990000"), 1));
        sport.addPlayerCategory(new PlayerCategory("Attaquant Axial", "AA", Color.web("#001A80"), Color.web("#990000"), 1));
        return sport;
    }

    private Sport createFootballSport() throws Exception {
        Sport sport = sportFactory.create("Football");
        sport.setBuiltInIconPathName("/images/built-in-sport-icons/football-icon.fxml");
        PlayingSurface playingSurface = new PlayingSurface(160.0, PlayingSurfaceUnit.FOOTS, 360.0, PlayingSurfaceUnit.FOOTS);
        playingSurface.setBuiltInImagePathName("/images/built-in-playing-surfaces/football.png");
        sport.setPlayingSurface(playingSurface);
        sport.addPlayerCategory(new PlayerCategory("Safety", "S", Color.web("#001A80"), Color.web("#990000"), 2));
        sport.addPlayerCategory(new PlayerCategory("Cornerback", "CB", Color.web("#001A80"), Color.web("#990000"), 2));
        sport.addPlayerCategory(new PlayerCategory("Outside Linebacker", "OL", Color.web("#001A80"), Color.web("#990000"), 2));
        sport.addPlayerCategory(new PlayerCategory("Middle Linebacker", "ML", Color.web("#001A80"), Color.web("#990000"), 1));
        sport.addPlayerCategory(new PlayerCategory("Defensive End", "DE", Color.web("#001A80"), Color.web("#990000"), 2));
        sport.addPlayerCategory(new PlayerCategory("Defensive Tackle", "DT", Color.web("#001A80"), Color.web("#990000"), 2));
        sport.addPlayerCategory(new PlayerCategory("Wide Receiver", "WR", Color.web("#001A80"), Color.web("#990000"), 2));
        sport.addPlayerCategory(new PlayerCategory("Offensive Tackle", "OT", Color.web("#001A80"), Color.web("#990000"), 2));
        sport.addPlayerCategory(new PlayerCategory("Offensive Guard", "OG", Color.web("#001A80"), Color.web("#990000"), 2));
        sport.addPlayerCategory(new PlayerCategory("Center", "C", Color.web("#001A80"), Color.web("#990000"), 1));
        sport.addPlayerCategory(new PlayerCategory("Tight End", "TE", Color.web("#001A80"), Color.web("#990000"), 1));
        sport.addPlayerCategory(new PlayerCategory("Quarterback", "QB", Color.web("#001A80"), Color.web("#990000"), 1));
        sport.addPlayerCategory(new PlayerCategory("Fullback", "FB", Color.web("#001A80"), Color.web("#990000"), 1));
        sport.addPlayerCategory(new PlayerCategory("Halfback", "HB", Color.web("#001A80"), Color.web("#990000"), 1));
        sport.addPlayerCategory(new PlayerCategory("Running Back", "RB", Color.web("#001A80"), Color.web("#990000"), 0));
        return sport;
    }

    private void persistSports(List<Sport> sportPool) throws SportAlreadyExistsException {
        for (Sport sport : sportPool) {
            sportRepository.persist(sport);
        }
    }
}
