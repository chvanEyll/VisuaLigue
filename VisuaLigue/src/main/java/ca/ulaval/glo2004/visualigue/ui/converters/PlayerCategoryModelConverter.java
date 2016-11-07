package ca.ulaval.glo2004.visualigue.ui.converters;

import ca.ulaval.glo2004.visualigue.domain.play.actor.TeamSide;
import ca.ulaval.glo2004.visualigue.domain.sport.playercategory.PlayerCategory;
import ca.ulaval.glo2004.visualigue.ui.models.PlayerCategoryModel;

public class PlayerCategoryModelConverter {

    public PlayerCategoryModel convert(PlayerCategory playerCategory) {
        PlayerCategoryModel playerCategoryModel = new PlayerCategoryModel();
        playerCategoryModel.setUUID(playerCategory.getUUID());
        playerCategoryModel.setIsNew(false);
        playerCategoryModel.name.set(playerCategory.getName());
        playerCategoryModel.abbreviation.set(playerCategory.getAbbreviation());
        playerCategoryModel.allyPlayerColor.set(playerCategory.getColor(TeamSide.ALLIES));
        playerCategoryModel.opponentPlayerColor.set(playerCategory.getColor(TeamSide.OPPONENTS));
        playerCategoryModel.defaultNumberOfPlayers.set(playerCategory.getDefaultNumberOfPlayers());
        return playerCategoryModel;
    }
}
