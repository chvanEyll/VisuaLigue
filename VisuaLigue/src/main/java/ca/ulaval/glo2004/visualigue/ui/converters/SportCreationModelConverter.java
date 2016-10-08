package ca.ulaval.glo2004.visualigue.ui.converters;

import ca.ulaval.glo2004.visualigue.domain.sport.Sport;
import ca.ulaval.glo2004.visualigue.ui.models.PlayerCategoryModel;
import ca.ulaval.glo2004.visualigue.ui.models.SportCreationModel;

public class SportCreationModelConverter {

    public SportCreationModel convert(Sport sport) {
        SportCreationModel model = new SportCreationModel();
        model.setAssociatedEntity(sport);
        model.setIsNew(false);
        model.name.set(sport.getName());
        model.builtInIconFileName.set(sport.getBuiltInIconFileName());
        model.playingSurfaceWidth.set(sport.getPlayingSurface().getWidth());
        model.playingSurfaceLength.set(sport.getPlayingSurface().getLength());
        model.playingSurfaceWidthUnits.set(sport.getPlayingSurface().getWidthUnits());
        model.playingSurfaceLengthUnits.set(sport.getPlayingSurface().getLengthUnits());
        model.playingSurfaceImageFileName.set(sport.getPlayingSurface().getImageFileName());
        sport.getPlayerCategories().forEach(playerCategory -> {
            PlayerCategoryModel playerCategoryModel = new PlayerCategoryModel();
            playerCategoryModel.setAssociatedEntity(playerCategory);
            playerCategoryModel.setIsNew(false);
            playerCategoryModel.name.set(playerCategory.getName());
            playerCategoryModel.allyPlayerColor.set(playerCategory.getAllyColor());
            playerCategoryModel.opponentPlayerColor.set(playerCategory.getOpponentColor());
            playerCategoryModel.defaultNumberOfPlayers.set(playerCategory.getDefaultNumberOfPlayers());
            model.playerCategoryModels.add(playerCategoryModel);
        });
        return model;
    }
}
