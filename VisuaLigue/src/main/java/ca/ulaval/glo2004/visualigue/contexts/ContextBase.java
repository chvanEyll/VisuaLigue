package ca.ulaval.glo2004.visualigue.contexts;

import ca.ulaval.glo2004.visualigue.domain.image.ImageRepository;
import ca.ulaval.glo2004.visualigue.domain.obstacle.ObstacleRepository;
import ca.ulaval.glo2004.visualigue.domain.play.PlayRepository;
import ca.ulaval.glo2004.visualigue.domain.sport.SportRepository;
import ca.ulaval.glo2004.visualigue.domain.sport.playercategory.PlayerCategoryRepository;
import javax.inject.Inject;

public abstract class ContextBase {

    protected final SportRepository sportRepository;
    protected final PlayerCategoryRepository playerCategoryRepository;
    protected final ImageRepository imageRepository;
    protected final ObstacleRepository obstacleRepository;
    protected final PlayRepository playRepository;

    @Inject
    public ContextBase(final SportRepository sportRepository, final PlayerCategoryRepository playerCategoryRepository, final ImageRepository imageRepository, final ObstacleRepository obstacleRepository, final PlayRepository playRepository) {
        this.sportRepository = sportRepository;
        this.playerCategoryRepository = playerCategoryRepository;
        this.imageRepository = imageRepository;
        this.obstacleRepository = obstacleRepository;
        this.playRepository = playRepository;
    }

    public void apply(Boolean forceClear) throws Exception {
        applyFillers(forceClear);
    }

    protected void clearRepositories() {
        playRepository.clear();
        playerCategoryRepository.clear();
        sportRepository.clear();
        obstacleRepository.clear();
        imageRepository.clear();
    }

    protected abstract void applyFillers(Boolean forceClear) throws Exception;

}
