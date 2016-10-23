package ca.ulaval.glo2004.visualigue.ui.converters;

import ca.ulaval.glo2004.visualigue.domain.image.ImageRepository;
import ca.ulaval.glo2004.visualigue.domain.sport.Sport;
import ca.ulaval.glo2004.visualigue.domain.sport.ball.Ball;
import ca.ulaval.glo2004.visualigue.domain.sport.playercategory.PlayerCategory;
import ca.ulaval.glo2004.visualigue.domain.sport.playingsurface.PlayingSurface;
import ca.ulaval.glo2004.visualigue.ui.models.PlayerCategoryModel;
import ca.ulaval.glo2004.visualigue.ui.models.SportCreationModel;
import java.util.Collections;
import java.util.Map;
import java.util.UUID;
import javax.inject.Inject;

public class SportCreationModelConverter {

    PlayerCategoryModelConverter playerCategoryModelConverter;
    ImageRepository imageRepository;

    @Inject
    public SportCreationModelConverter(PlayerCategoryModelConverter playerCategoryModelConverter, ImageRepository imageRepository) {
        this.playerCategoryModelConverter = playerCategoryModelConverter;
        this.imageRepository = imageRepository;
    }

    public SportCreationModel convert(Sport sport) {
        SportCreationModel model = new SportCreationModel();
        model.setUUID(sport.getUUID());
        model.setIsNew(false);
        model.name.set(sport.getName());
        model.playingSurfaceWidth.set(sport.getPlayingSurface().getWidth());
        model.playingSurfaceLength.set(sport.getPlayingSurface().getLength());
        model.playingSurfaceWidthUnits.set(sport.getPlayingSurface().getWidthUnits());
        model.playingSurfaceLengthUnits.set(sport.getPlayingSurface().getLengthUnits());
        convertIcon(sport, model);
        convertBall(sport.getBall(), model);
        convertPlayingSurfaceImage(sport.getPlayingSurface(), model);
        convertPlayerCategories(sport.getPlayerCategories(), model);
        return model;
    }

    private void convertIcon(Sport sport, SportCreationModel model) {
        if (sport.hasCustomIcon()) {
            model.currentIconPathName.set(imageRepository.get(sport.getCustomIconUUID()));
        }
        model.builtInIconPathName.set(sport.getBuiltInIconPathName());
    }

    private void convertBall(Ball ball, SportCreationModel model) {
        model.ballName.set(ball.getName());
        if (ball.hasCustomImage()) {
            model.currentBallImagePathName.set(imageRepository.get(ball.getCustomImageUUID()));
        }
        model.builtInBallImagePathName.set(ball.getBuiltInImagePathName());
    }

    private void convertPlayingSurfaceImage(PlayingSurface playingSurface, SportCreationModel model) {
        if (playingSurface.hasCustomImage()) {
            model.currentPlayingSurfaceImagePathName.set(imageRepository.get(playingSurface.getCustomImageUUID()));
        }
        model.builtInPlayingSurfaceImagePathName.set(playingSurface.getBuiltInImagePathName());
    }

    private void convertPlayerCategories(Map<UUID, PlayerCategory> playerCategories, SportCreationModel model) {
        playerCategories.values().forEach(playerCategory -> {
            PlayerCategoryModel playerCategoryModel = playerCategoryModelConverter.convert(playerCategory);
            model.playerCategoryModels.add(playerCategoryModel);
            Collections.sort(model.playerCategoryModels);
        });
    }
}
