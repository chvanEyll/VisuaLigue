package ca.ulaval.glo2004.visualigue.contexts;

import ca.ulaval.glo2004.visualigue.domain.image.ImageRepository;
import ca.ulaval.glo2004.visualigue.domain.obstacle.Obstacle;
import ca.ulaval.glo2004.visualigue.domain.obstacle.ObstacleAlreadyExistsException;
import ca.ulaval.glo2004.visualigue.domain.obstacle.ObstacleFactory;
import ca.ulaval.glo2004.visualigue.domain.obstacle.ObstacleRepository;
import ca.ulaval.glo2004.visualigue.domain.play.Play;
import ca.ulaval.glo2004.visualigue.domain.play.PlayAlreadyExistsException;
import ca.ulaval.glo2004.visualigue.domain.play.PlayFactory;
import ca.ulaval.glo2004.visualigue.domain.play.PlayRepository;
import ca.ulaval.glo2004.visualigue.domain.play.actorinstance.BallInstance;
import ca.ulaval.glo2004.visualigue.domain.play.actorinstance.ObstacleInstance;
import ca.ulaval.glo2004.visualigue.domain.play.actorinstance.PlayerInstance;
import ca.ulaval.glo2004.visualigue.domain.play.actorinstance.TeamSide;
import ca.ulaval.glo2004.visualigue.domain.play.actorstate.BallState;
import ca.ulaval.glo2004.visualigue.domain.play.actorstate.ObstacleState;
import ca.ulaval.glo2004.visualigue.domain.play.actorstate.PlayerState;
import ca.ulaval.glo2004.visualigue.domain.play.actorstate.transition.LinearTransition;
import ca.ulaval.glo2004.visualigue.domain.sport.Sport;
import ca.ulaval.glo2004.visualigue.domain.sport.SportAlreadyExistsException;
import ca.ulaval.glo2004.visualigue.domain.sport.SportFactory;
import ca.ulaval.glo2004.visualigue.domain.sport.SportRepository;
import ca.ulaval.glo2004.visualigue.domain.sport.ball.Ball;
import ca.ulaval.glo2004.visualigue.domain.sport.playercategory.PlayerCategory;
import ca.ulaval.glo2004.visualigue.domain.sport.playercategory.PlayerCategoryRepository;
import ca.ulaval.glo2004.visualigue.domain.sport.playingsurface.PlayingSurface;
import ca.ulaval.glo2004.visualigue.domain.sport.playingsurface.PlayingSurfaceUnit;
import ca.ulaval.glo2004.visualigue.utils.geometry.Vector2;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.paint.Color;
import javax.inject.Inject;

public class DefaultContext extends ContextBase {

    private final SportFactory sportFactory;
    private final ObstacleFactory obstacleFactory;
    private final PlayFactory playFactory;
    private final List<Sport> sportPool = new ArrayList();
    private Sport hockeySport;
    private Obstacle coneObstacle;

    @Inject
    public DefaultContext(final SportFactory sportFactory, final SportRepository sportRepository, final PlayerCategoryRepository playerCategoryRepository, final ImageRepository imageRepository, final ObstacleFactory obstacleFactory,
            final ObstacleRepository obstacleRepository, final PlayRepository playRepository, final PlayFactory playFactory) {
        super(sportRepository, playerCategoryRepository, imageRepository, obstacleRepository, playRepository);
        this.sportFactory = sportFactory;
        this.obstacleFactory = obstacleFactory;
        this.playFactory = playFactory;
    }

    @Override
    protected void applyFillers(Boolean forceClear) throws Exception {
        if (!sportRepository.isEmpty() && forceClear) {
            clearRepositories();
        } else if (!sportRepository.isEmpty()) {
            System.out.println("Repositories are not empty: Skipping default context repository filling.");
            return;
        }
        fill();
    }

    private void fill() throws Exception {
        createSports();
        createObstacles();
        createPlays();
    }

    private void createSports() throws Exception {
        hockeySport = createHockeySport();
        sportPool.add(hockeySport);
        sportPool.add(createSoccerSport());
        sportPool.add(createFootballSport());
        sportPool.add(createBaseballSport());
        sportPool.add(createBasketballSport());
        sportPool.add(createVolleyballSport());
        persistSports(sportPool);
    }

    private Sport createHockeySport() throws Exception {
        Sport sport = sportFactory.create("Hockey");
        sport.setIsBuiltIn(true);
        sport.setBuiltInIconPathName("/images/built-in-sport-icons/hockey-icon.png");
        Ball ball = new Ball("Rondelle");
        ball.setBuiltInImagePathName("/images/built-in-ball-icons/hockey-puck-icon.png");
        sport.setBall(ball);
        PlayingSurface playingSurface = new PlayingSurface(85.0, PlayingSurfaceUnit.FOOTS, 200.0, PlayingSurfaceUnit.FOOTS);
        playingSurface.setBuiltInImagePathName("/images/built-in-playing-surfaces/hockey-rink.png");
        sport.setPlayingSurface(playingSurface);
        addPlayerCategory(sport, new PlayerCategory("Gardien", "G", Color.web("#E6994D"), Color.web("#E6994D"), 1));
        addPlayerCategory(sport, new PlayerCategory("Défenseur", "D", Color.web("#001A80"), Color.web("#990000"), 2));
        addPlayerCategory(sport, new PlayerCategory("Attaquant", "A", Color.web("#001A80"), Color.web("#990000"), 2));
        addPlayerCategory(sport, new PlayerCategory("Ailier Gauche", "AG", Color.web("#001A80"), Color.web("#990000"), 1));
        addPlayerCategory(sport, new PlayerCategory("Ailier Droit", "AD", Color.web("#001A80"), Color.web("#990000"), 1));
        addPlayerCategory(sport, new PlayerCategory("Centre", "C", Color.web("#001A80"), Color.web("#990000"), 1));
        return sport;
    }

    private Sport createSoccerSport() throws Exception {
        Sport sport = sportFactory.create("Soccer");
        sport.setBuiltInIconPathName("/images/built-in-sport-icons/soccer-icon.png");
        sport.setIsBuiltIn(true);
        Ball ball = new Ball("Ballon");
        ball.setBuiltInImagePathName("/images/built-in-ball-icons/soccer-ball-icon.png");
        sport.setBall(ball);
        PlayingSurface playingSurface = new PlayingSurface(68.0, PlayingSurfaceUnit.METER, 100.0, PlayingSurfaceUnit.METER);
        playingSurface.setBuiltInImagePathName("/images/built-in-playing-surfaces/soccer-field.jpg");
        sport.setPlayingSurface(playingSurface);
        addPlayerCategory(sport, new PlayerCategory("Gardien", "G", Color.web("#E6994D"), Color.web("#E6994D"), 1));
        addPlayerCategory(sport, new PlayerCategory("Défenseur", "D", Color.web("#001A80"), Color.web("#990000"), 1));
        addPlayerCategory(sport, new PlayerCategory("Défenseur Droit", "DD", Color.web("#001A80"), Color.web("#990000"), 1));
        addPlayerCategory(sport, new PlayerCategory("Défenseur Gauche", "DG", Color.web("#001A80"), Color.web("#990000"), 1));
        addPlayerCategory(sport, new PlayerCategory("Défenseur Central", "DC", Color.web("#001A80"), Color.web("#990000"), 1));
        addPlayerCategory(sport, new PlayerCategory("Milieu", "M", Color.web("#001A80"), Color.web("#990000"), 1));
        addPlayerCategory(sport, new PlayerCategory("Milieu Défensif Gauche", "MDG", Color.web("#001A80"), Color.web("#990000"), 1));
        addPlayerCategory(sport, new PlayerCategory("Milieu Défensif Droit", "MDD", Color.web("#001A80"), Color.web("#990000"), 1));
        addPlayerCategory(sport, new PlayerCategory("Milieu Défensif Central", "MDC", Color.web("#001A80"), Color.web("#990000"), 0));
        addPlayerCategory(sport, new PlayerCategory("Milieu Gauche", "MG", Color.web("#001A80"), Color.web("#990000"), 0));
        addPlayerCategory(sport, new PlayerCategory("Milieu Droit", "MD", Color.web("#001A80"), Color.web("#990000"), 0));
        addPlayerCategory(sport, new PlayerCategory("Milieu Central", "MC", Color.web("#001A80"), Color.web("#990000"), 1));
        addPlayerCategory(sport, new PlayerCategory("Milieu Offensif Gauche", "MOG", Color.web("#001A80"), Color.web("#990000"), 1));
        addPlayerCategory(sport, new PlayerCategory("Milieu Offensif Droit", "MOD", Color.web("#001A80"), Color.web("#990000"), 1));
        addPlayerCategory(sport, new PlayerCategory("Milieu Offensif Axial", "MOA", Color.web("#001A80"), Color.web("#990000"), 0));
        addPlayerCategory(sport, new PlayerCategory("Attaquant", "A", Color.web("#001A80"), Color.web("#990000"), 1));
        addPlayerCategory(sport, new PlayerCategory("Attaquant Gauche", "AG", Color.web("#001A80"), Color.web("#990000"), 1));
        addPlayerCategory(sport, new PlayerCategory("Attaquant Droit", "AD", Color.web("#001A80"), Color.web("#990000"), 1));
        addPlayerCategory(sport, new PlayerCategory("Attaquant Axial", "AA", Color.web("#001A80"), Color.web("#990000"), 1));
        return sport;
    }

    private Sport createFootballSport() throws Exception {
        Sport sport = sportFactory.create("Football");
        sport.setBuiltInIconPathName("/images/built-in-sport-icons/football-icon.png");
        sport.setIsBuiltIn(true);
        Ball ball = new Ball("Ballon");
        ball.setBuiltInImagePathName("/images/built-in-ball-icons/football-ball-icon.png");
        sport.setBall(ball);
        PlayingSurface playingSurface = new PlayingSurface(160.0, PlayingSurfaceUnit.FOOTS, 360.0, PlayingSurfaceUnit.FOOTS);
        playingSurface.setBuiltInImagePathName("/images/built-in-playing-surfaces/football-field.png");
        sport.setPlayingSurface(playingSurface);
        addPlayerCategory(sport, new PlayerCategory("Safety", "S", Color.web("#001A80"), Color.web("#990000"), 2));
        addPlayerCategory(sport, new PlayerCategory("Cornerback", "CB", Color.web("#001A80"), Color.web("#990000"), 2));
        addPlayerCategory(sport, new PlayerCategory("Outside Linebacker", "OL", Color.web("#001A80"), Color.web("#990000"), 2));
        addPlayerCategory(sport, new PlayerCategory("Middle Linebacker", "ML", Color.web("#001A80"), Color.web("#990000"), 1));
        addPlayerCategory(sport, new PlayerCategory("Defensive End", "DE", Color.web("#001A80"), Color.web("#990000"), 2));
        addPlayerCategory(sport, new PlayerCategory("Defensive Tackle", "DT", Color.web("#001A80"), Color.web("#990000"), 2));
        addPlayerCategory(sport, new PlayerCategory("Wide Receiver", "WR", Color.web("#001A80"), Color.web("#990000"), 2));
        addPlayerCategory(sport, new PlayerCategory("Offensive Tackle", "OT", Color.web("#001A80"), Color.web("#990000"), 2));
        addPlayerCategory(sport, new PlayerCategory("Offensive Guard", "OG", Color.web("#001A80"), Color.web("#990000"), 2));
        addPlayerCategory(sport, new PlayerCategory("Center", "C", Color.web("#001A80"), Color.web("#990000"), 1));
        addPlayerCategory(sport, new PlayerCategory("Tight End", "TE", Color.web("#001A80"), Color.web("#990000"), 1));
        addPlayerCategory(sport, new PlayerCategory("Quarterback", "QB", Color.web("#001A80"), Color.web("#990000"), 1));
        addPlayerCategory(sport, new PlayerCategory("Fullback", "FB", Color.web("#001A80"), Color.web("#990000"), 1));
        addPlayerCategory(sport, new PlayerCategory("Halfback", "HB", Color.web("#001A80"), Color.web("#990000"), 1));
        addPlayerCategory(sport, new PlayerCategory("Running Back", "RB", Color.web("#001A80"), Color.web("#990000"), 0));
        return sport;
    }

    private Sport createBaseballSport() throws Exception {
        Sport sport = sportFactory.create("Baseball");
        sport.setBuiltInIconPathName("/images/built-in-sport-icons/baseball-icon.png");
        sport.setIsBuiltIn(true);
        Ball ball = new Ball("Balle");
        ball.setBuiltInImagePathName("/images/built-in-ball-icons/baseball-ball-icon.png");
        sport.setBall(ball);
        PlayingSurface playingSurface = new PlayingSurface(190.0, PlayingSurfaceUnit.FOOTS, 155.0, PlayingSurfaceUnit.FOOTS);
        playingSurface.setBuiltInImagePathName("/images/built-in-playing-surfaces/baseball-field.png");
        sport.setPlayingSurface(playingSurface);
        addPlayerCategory(sport, new PlayerCategory("Lanceur", "L", Color.web("#001A80"), Color.web("#990000"), 1));
        addPlayerCategory(sport, new PlayerCategory("Receveur", "R", Color.web("#001A80"), Color.web("#990000"), 1));
        addPlayerCategory(sport, new PlayerCategory("Premier but", "1B", Color.web("#001A80"), Color.web("#990000"), 1));
        addPlayerCategory(sport, new PlayerCategory("Deuxième but", "2B", Color.web("#001A80"), Color.web("#990000"), 1));
        addPlayerCategory(sport, new PlayerCategory("Trosième but", "3B", Color.web("#001A80"), Color.web("#990000"), 1));
        addPlayerCategory(sport, new PlayerCategory("Arrêt-court", "AC", Color.web("#001A80"), Color.web("#990000"), 1));
        addPlayerCategory(sport, new PlayerCategory("Voltige gauche", "VG", Color.web("#001A80"), Color.web("#990000"), 1));
        addPlayerCategory(sport, new PlayerCategory("Voltige centre", "VC", Color.web("#001A80"), Color.web("#990000"), 1));
        addPlayerCategory(sport, new PlayerCategory("Voltige droit", "VD", Color.web("#001A80"), Color.web("#990000"), 1));
        return sport;
    }

    private Sport createBasketballSport() throws Exception {
        Sport sport = sportFactory.create("Basketball");
        sport.setBuiltInIconPathName("/images/built-in-sport-icons/basketball-icon.png");
        sport.setIsBuiltIn(true);
        Ball ball = new Ball("Ballon");
        ball.setBuiltInImagePathName("/images/built-in-ball-icons/basketball-ball-icon.png");
        sport.setBall(ball);
        PlayingSurface playingSurface = new PlayingSurface(50.0, PlayingSurfaceUnit.FOOTS, 94.0, PlayingSurfaceUnit.FOOTS);
        playingSurface.setBuiltInImagePathName("/images/built-in-playing-surfaces/basketball-court.png");
        sport.setPlayingSurface(playingSurface);
        addPlayerCategory(sport, new PlayerCategory("Meneur", "M", Color.web("#001A80"), Color.web("#990000"), 1));
        addPlayerCategory(sport, new PlayerCategory("Arrière", "AR", Color.web("#001A80"), Color.web("#990000"), 1));
        addPlayerCategory(sport, new PlayerCategory("Ailier", "A", Color.web("#001A80"), Color.web("#990000"), 1));
        addPlayerCategory(sport, new PlayerCategory("Ailier fort", "AD", Color.web("#001A80"), Color.web("#990000"), 1));
        addPlayerCategory(sport, new PlayerCategory("Pivot", "C", Color.web("#001A80"), Color.web("#990000"), 1));
        return sport;
    }

    private Sport createVolleyballSport() throws Exception {
        Sport sport = sportFactory.create("Volleyball");
        sport.setBuiltInIconPathName("/images/built-in-sport-icons/volleyball-icon.png");
        sport.setIsBuiltIn(true);
        Ball ball = new Ball("Ballon");
        ball.setBuiltInImagePathName("/images/built-in-ball-icons/volleyball-ball-icon.png");
        sport.setBall(ball);
        PlayingSurface playingSurface = new PlayingSurface(9.0, PlayingSurfaceUnit.METER, 18.0, PlayingSurfaceUnit.METER);
        playingSurface.setBuiltInImagePathName("/images/built-in-playing-surfaces/volleyball-court.png");
        sport.setPlayingSurface(playingSurface);
        addPlayerCategory(sport, new PlayerCategory("1 (Arrière droit)", "1AD", Color.web("#001A80"), Color.web("#990000"), 1));
        addPlayerCategory(sport, new PlayerCategory("2 (Avant droit)", "2AD", Color.web("#001A80"), Color.web("#990000"), 1));
        addPlayerCategory(sport, new PlayerCategory("3 (Avant centre)", "3AC", Color.web("#001A80"), Color.web("#990000"), 1));
        addPlayerCategory(sport, new PlayerCategory("4 (Avant gauche)", "4AG", Color.web("#001A80"), Color.web("#990000"), 1));
        addPlayerCategory(sport, new PlayerCategory("5 (Arrière gauche)", "5AG", Color.web("#001A80"), Color.web("#990000"), 1));
        addPlayerCategory(sport, new PlayerCategory("6 (Arrière centre)", "6AC", Color.web("#001A80"), Color.web("#990000"), 1));
        return sport;
    }

    private void addPlayerCategory(Sport sport, PlayerCategory playerCategory) {
        playerCategoryRepository.persist(playerCategory);
        sport.addPlayerCategory(playerCategory);
    }

    private void persistSports(List<Sport> sportPool) throws SportAlreadyExistsException {
        for (Sport sport : sportPool) {
            sportRepository.persist(sport);
        }
    }

    private void createObstacles() throws Exception {
        List<Obstacle> obstaclePool = new ArrayList();
        coneObstacle = createConeObstacle();
        obstaclePool.add(coneObstacle);
        persistObstacles(obstaclePool);
    }

    private Obstacle createConeObstacle() {
        Obstacle obstacle = obstacleFactory.create("Cône");
        obstacle.setBuiltInImagePathName("/images/built-in-obstacle-icons/cone-icon.png");
        obstacle.setIsBuiltIn(true);
        return obstacle;
    }

    private void persistObstacles(List<Obstacle> obstaclePool) throws ObstacleAlreadyExistsException {
        for (Obstacle obstacle : obstaclePool) {
            obstacleRepository.persist(obstacle);
        }
    }

    private void createPlays() throws Exception {
        List<Play> playPool = new ArrayList();
        playPool.add(createDefaultPlay());
        persistPlays(playPool);
    }

    private Play createDefaultPlay() {
        Play play = playFactory.create("Test", sportPool.get(0));
        List<PlayerCategory> playerCategories = new ArrayList(hockeySport.getPlayerCategories());
        PlayerInstance playerInstance1 = new PlayerInstance(playerCategories.get(0), TeamSide.ALLIES);
        PlayerState playerState = new PlayerState(new Vector2(0.25, 0.25), new LinearTransition(), 0.0, new LinearTransition());
        play.mergeKeyframe(0, playerInstance1, playerState);
        playerState = new PlayerState(new Vector2(0.75, 0.75), new LinearTransition(), 90.0, new LinearTransition());
        play.mergeKeyframe(1000, playerInstance1, playerState);
        playerState = new PlayerState(new Vector2(0.5, 0.2), new LinearTransition(), 180.0, new LinearTransition());
        play.mergeKeyframe(2000, playerInstance1, playerState);

        PlayerInstance playerInstance2 = new PlayerInstance(playerCategories.get(1), TeamSide.OPPONENTS);
        playerState = new PlayerState(new Vector2(0.8, 0.3), new LinearTransition(), 180.0, new LinearTransition());
        play.mergeKeyframe(0, playerInstance2, playerState);
        playerState = new PlayerState(new Vector2(0.1, 0.9), new LinearTransition(), 0.0, new LinearTransition());
        play.mergeKeyframe(1000, playerInstance2, playerState);
        playerState = new PlayerState(new Vector2(0.3, 0.45), new LinearTransition(), 0.0, new LinearTransition());
        play.mergeKeyframe(2000, playerInstance2, playerState);

        ObstacleInstance obstacleInstance1 = new ObstacleInstance(coneObstacle);
        ObstacleState obstacleState = new ObstacleState(new Vector2(0.5, 0.4));
        play.mergeKeyframe(0, obstacleInstance1, obstacleState);

        BallInstance ballInstance1 = new BallInstance();
        BallState ballState = new BallState(new Vector2(0.4, 0.8), new LinearTransition(), null);
        play.mergeKeyframe(0, ballInstance1, ballState);
        ballState = new BallState(new Vector2(0.7, 0.25), new LinearTransition(), null);
        play.mergeKeyframe(1000, ballInstance1, ballState);
        ballState = new BallState(new Vector2(0.1, 0.1), new LinearTransition(), null);
        play.mergeKeyframe(2000, ballInstance1, ballState);

        return play;
    }

    private void persistPlays(List<Play> playPool) throws PlayAlreadyExistsException {
        for (Play play : playPool) {
            playRepository.persist(play);
        }
    }
}
