package ca.ulaval.glo2004.visualigue.ui.converters.layers;

import ca.ulaval.glo2004.visualigue.domain.play.actor.ActorInstance;
import ca.ulaval.glo2004.visualigue.domain.play.actor.PlayerActor;
import ca.ulaval.glo2004.visualigue.domain.play.actor.TeamSide;
import ca.ulaval.glo2004.visualigue.domain.play.actorstate.PlayerState;
import ca.ulaval.glo2004.visualigue.ui.models.PlayerCategoryModel;
import ca.ulaval.glo2004.visualigue.ui.models.layers.PlayerLayerModel;

public class PlayeLayerModelConverter {

    public PlayerLayerModel convert(PlayerCategoryModel playerCategoryModel, TeamSide teamSide) {
        PlayerLayerModel model = new PlayerLayerModel();
        if (teamSide == TeamSide.ALLIES) {
            model.color.set(playerCategoryModel.allyPlayerColor.get());
        } else {
            model.color.set(playerCategoryModel.opponentPlayerColor.get());
        }
        model.label.set(playerCategoryModel.abbreviation.get());
        return model;
    }

    public PlayerLayerModel convert(ActorInstance actorInstance) {
        PlayerLayerModel model = new PlayerLayerModel();
        update(model, actorInstance);
        return model;
    }

    public void update(PlayerLayerModel model, ActorInstance actorInstance) {
        PlayerActor playerActor = (PlayerActor) actorInstance.getActor();
        PlayerState playerState = (PlayerState) actorInstance.getActorState();
        model.setUUID(playerActor.getUUID());
        model.isLocked.set(playerState.isLocked());
        model.opacity.set(playerState.getOpacity());
        model.zOrder.set(playerState.getZOrder());
        model.showLabel.set(playerState.getShowLabel());
        model.position.set(playerState.getPosition());
        model.nextPosition.set(playerState.getNextPosition());
        model.color.set(playerActor.getPlayerCategory().getColor(playerActor.getTeamSide()));
        model.orientation.set(playerState.getOrientation());
        model.label.set(playerActor.getPlayerCategory().getAbbreviation());
        model.hoverText.set(playerActor.getPlayerCategory().getName());
    }

}
