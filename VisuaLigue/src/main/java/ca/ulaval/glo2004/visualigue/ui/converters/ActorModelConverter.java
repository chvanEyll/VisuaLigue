package ca.ulaval.glo2004.visualigue.ui.converters;

import ca.ulaval.glo2004.visualigue.domain.play.actorinstance.TeamSide;
import ca.ulaval.glo2004.visualigue.ui.models.ActorModel;
import ca.ulaval.glo2004.visualigue.ui.models.PlayerCategoryModel;

public class ActorModelConverter {

    public ActorModel convert(PlayerCategoryModel playerCategoryModel, TeamSide teamSide) {
        ActorModel actorModel = new ActorModel();
        actorModel.type.set(ActorModel.Type.PLAYER);
        if (teamSide == TeamSide.ALLIES) {
            actorModel.color.set(playerCategoryModel.allyPlayerColor.get());
        } else {
            actorModel.color.set(playerCategoryModel.opponentPlayerColor.get());
        }
        actorModel.label.set(playerCategoryModel.abbreviation.get());
        return actorModel;
    }

}
