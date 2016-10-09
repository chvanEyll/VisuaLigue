package ca.ulaval.glo2004.visualigue.ui.converters;

import ca.ulaval.glo2004.visualigue.domain.sport.Sport;
import ca.ulaval.glo2004.visualigue.ui.models.PlayerCategoryModel;
import ca.ulaval.glo2004.visualigue.ui.models.SportCreationModel;
import ca.ulaval.glo2004.visualigue.utils.ColorUtils;

public class SportCreationModelConverter {

    public SportCreationModel convert(Sport sport) {
        SportCreationModel model = new SportCreationModel();
        model.setAssociatedEntity(sport);
        model.setIsNew(false);
        model.name.set(sport.getName());
        model.builtInIconPathName.set(sport.getBuiltInIconPathName());
        model.playingSurfaceWidth.set(sport.getPlayingSurface().getWidth());
        model.playingSurfaceLength.set(sport.getPlayingSurface().getLength());
        model.playingSurfaceWidthUnits.set(sport.getPlayingSurface().getWidthUnits());
        model.playingSurfaceLengthUnits.set(sport.getPlayingSurface().getLengthUnits());
        model.currentPlayingSurfacePathName.set(sport.getPlayingSurface().getImageResource().getAbsolutePersistedPathName());
        sport.getPlayerCategories().forEach(playerCategory -> {
            PlayerCategoryModel playerCategoryModel = new PlayerCategoryModel();
            playerCategoryModel.setAssociatedEntity(playerCategory);
            playerCategoryModel.setIsNew(false);
            playerCategoryModel.name.set(playerCategory.getName());
            playerCategoryModel.allyPlayerColor.set(ColorUtils.AWTColorToFXColor(playerCategory.getAllyColor()));
            playerCategoryModel.opponentPlayerColor.set(ColorUtils.AWTColorToFXColor(playerCategory.getOpponentColor()));
            playerCategoryModel.defaultNumberOfPlayers.set(playerCategory.getDefaultNumberOfPlayers());
            model.playerCategoryModels.add(playerCategoryModel);
        });
        return model;
    }
}
