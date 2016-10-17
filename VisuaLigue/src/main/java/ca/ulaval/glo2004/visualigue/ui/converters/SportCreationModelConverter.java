package ca.ulaval.glo2004.visualigue.ui.converters;

import ca.ulaval.glo2004.visualigue.domain.image.ImageRepository;
import ca.ulaval.glo2004.visualigue.domain.playercategory.PlayerCategory;
import ca.ulaval.glo2004.visualigue.domain.playingsurface.PlayingSurface;
import ca.ulaval.glo2004.visualigue.domain.sport.Sport;
import ca.ulaval.glo2004.visualigue.ui.models.PlayerCategoryModel;
import ca.ulaval.glo2004.visualigue.ui.models.SportCreationModel;
import java.util.Collections;
import java.util.Map;
import java.util.UUID;
import javax.inject.Inject;

public class SportCreationModelConverter {

    ImageRepository imageRepository;

    @Inject
    public SportCreationModelConverter(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    public SportCreationModel convert(Sport sport) {
        SportCreationModel model = new SportCreationModel();
        model.setUUID(sport.getUUID());
        model.setIsNew(false);
        model.name.set(sport.getName());
        model.iconPathName.set(sport.getIconPathName());
        model.playingSurfaceWidth.set(sport.getPlayingSurface().getWidth());
        model.playingSurfaceLength.set(sport.getPlayingSurface().getLength());
        model.playingSurfaceWidthUnits.set(sport.getPlayingSurface().getWidthUnits());
        model.playingSurfaceLengthUnits.set(sport.getPlayingSurface().getLengthUnits());
        convertPlayingSurfaceImage(sport.getPlayingSurface(), model);
        convertPlayerCategories(sport.getPlayerCategories(), model);
        return model;
    }

    private void convertPlayingSurfaceImage(PlayingSurface playingSurface, SportCreationModel model) {
        if (playingSurface.hasCustomImage()) {
            model.currentPlayingSurfacePathName.set(imageRepository.get(playingSurface.getCustomImageUUID()));
        }
        model.builtInPlayingSurfaceImage.set(playingSurface.getBuiltInImagePathName());
    }

    private void convertPlayerCategories(Map<UUID, PlayerCategory> playerCategories, SportCreationModel model) {
        playerCategories.values().forEach(playerCategory -> {
            PlayerCategoryModel playerCategoryModel = new PlayerCategoryModel();
            playerCategoryModel.setUUID(playerCategory.getUUID());
            playerCategoryModel.setIsNew(false);
            playerCategoryModel.name.set(playerCategory.getName());
            playerCategoryModel.abbreviation.set(playerCategory.getAbbreviation());
            playerCategoryModel.allyPlayerColor.set(playerCategory.getAllyColor());
            playerCategoryModel.opponentPlayerColor.set(playerCategory.getOpponentColor());
            playerCategoryModel.defaultNumberOfPlayers.set(playerCategory.getDefaultNumberOfPlayers());
            model.playerCategoryModels.add(playerCategoryModel);
            Collections.sort(model.playerCategoryModels);
        });
    }
}
