package ca.ulaval.glo2004.visualigue.ui.converters;

import ca.ulaval.glo2004.visualigue.domain.playercategory.PlayerCategory;
import ca.ulaval.glo2004.visualigue.domain.sport.Sport;
import ca.ulaval.glo2004.visualigue.ui.models.PlayerCategoryModel;
import ca.ulaval.glo2004.visualigue.ui.models.SportCreationModel;
import ca.ulaval.glo2004.visualigue.utils.ImageUtils;
import java.util.Map;
import java.util.UUID;

public class SportCreationModelConverter {

    public SportCreationModel convert(Sport sport) {
        SportCreationModel model = new SportCreationModel();
        model.setUUID(sport.getUUID());
        model.setIsNew(false);
        model.name.set(sport.getName());
        model.builtInIconPathName.set(sport.getBuiltInIconPathName());
        model.playingSurfaceWidth.set(sport.getPlayingSurface().getWidth());
        model.playingSurfaceLength.set(sport.getPlayingSurface().getLength());
        model.playingSurfaceWidthUnits.set(sport.getPlayingSurface().getWidthUnits());
        model.playingSurfaceLengthUnits.set(sport.getPlayingSurface().getLengthUnits());
        if (!sport.getPlayingSurface().getImageResource().isEmpty()) {
            model.currentPlayingSurfaceImage.set(ImageUtils.loadImageFromLocatedResource(sport.getPlayingSurface().getImageResource()));
        }
        convertPlayerCategories(sport.getPlayerCategories(), model);
        return model;
    }

    private void convertPlayerCategories(Map<UUID, PlayerCategory> playerCategories, SportCreationModel model) {
        playerCategories.values().forEach(playerCategory -> {
            PlayerCategoryModel playerCategoryModel = new PlayerCategoryModel();
            playerCategoryModel.setUUID(playerCategory.getUUID());
            playerCategoryModel.setIsNew(false);
            playerCategoryModel.name.set(playerCategory.getName());
            playerCategoryModel.allyPlayerColor.set(playerCategory.getAllyColor());
            playerCategoryModel.opponentPlayerColor.set(playerCategory.getOpponentColor());
            playerCategoryModel.defaultNumberOfPlayers.set(playerCategory.getDefaultNumberOfPlayers());
            model.playerCategoryModels.add(playerCategoryModel);
        });
    }
}
