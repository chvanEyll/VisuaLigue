package ca.ulaval.glo2004.visualigue.ui.converters.layers;

import ca.ulaval.glo2004.visualigue.domain.play.actor.PlayerActor;
import ca.ulaval.glo2004.visualigue.domain.play.actor.TeamSide;
import ca.ulaval.glo2004.visualigue.domain.play.actorstate.ActorState;
import ca.ulaval.glo2004.visualigue.domain.play.actorstate.PlayerState;
import ca.ulaval.glo2004.visualigue.domain.play.frame.Frame;
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

    public PlayerLayerModel convert(Frame frame, PlayerActor playerActor, PlayerState playerState) {
        PlayerLayerModel model = new PlayerLayerModel();
        update(frame, model, playerActor, playerState);
        return model;
    }

    public void update(Frame frame, PlayerLayerModel model, PlayerActor playerActor, PlayerState playerState) {
        model.setUUID(playerActor.getUUID());
        model.position.set(playerState.getPosition());
        ActorState nextActorState = frame.getNextActorState(playerActor);
        if (nextActorState != null) {
            model.nextPosition.set(nextActorState.getPosition());
        } else {
            model.nextPosition.set(null);
        }
        model.color.set(playerActor.getPlayerCategory().getColor(playerActor.getTeamSide()));
        model.orientation.set(playerState.getOrientation());
        model.label.set(playerActor.getPlayerCategory().getAbbreviation());
        model.hoverText.set(playerActor.getPlayerCategory().getName());
    }

}
