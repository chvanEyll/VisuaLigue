package ca.ulaval.glo2004.visualigue.ui.converters.actor;

import ca.ulaval.glo2004.visualigue.domain.play.frame.ActorInstance;
import ca.ulaval.glo2004.visualigue.domain.play.actor.PlayerActor;
import ca.ulaval.glo2004.visualigue.domain.play.actor.TeamSide;
import ca.ulaval.glo2004.visualigue.domain.play.actorstate.PlayerState;
import ca.ulaval.glo2004.visualigue.ui.models.PlayerCategoryModel;
import ca.ulaval.glo2004.visualigue.ui.models.actors.PlayerActorModel;

public class PlayerActorModelConverter {

    public PlayerActorModel convert(PlayerCategoryModel playerCategoryModel, TeamSide teamSide) {
        PlayerActorModel model = new PlayerActorModel();
        if (teamSide == TeamSide.ALLIES) {
            model.color.set(playerCategoryModel.allyPlayerColor.get());
        } else {
            model.color.set(playerCategoryModel.opponentPlayerColor.get());
        }
        model.label.set(playerCategoryModel.abbreviation.get());
        return model;
    }

    public PlayerActorModel convert(ActorInstance actorInstance) {
        PlayerActorModel model = new PlayerActorModel();
        update(model, actorInstance);
        return model;
    }

    public void update(PlayerActorModel model, ActorInstance actorInstance) {
        PlayerActor playerActor = (PlayerActor) actorInstance.getActor();
        PlayerState playerState = (PlayerState) actorInstance.getActorState();
        model.setUUID(playerActor.getUUID());
        model.instanceID.set(actorInstance.getInstanceID());
        model.isLocked.set(playerState.isLocked());
        model.opacity.set(playerState.getOpacity());
        model.visible.set(playerState.isVisible());
        model.zOrder.set(playerState.getZOrder());
        model.showLabel.set(playerState.getShowLabel());
        model.position.set(playerState.getPosition());
        model.nextPosition.set(playerState.getNextPosition());
        model.color.set(playerActor.getPlayerCategory().getColor(playerActor.getTeamSide()));
        model.orientation.set(playerState.getOrientation());
        model.label.set(playerActor.getPlayerCategory().getAbbreviation());
        model.hoverText.set(playerActor.getPlayerCategory().getName());
        if (playerState.hasSnappedBall()) {
            model.snappedBallUUID.set(playerState.getSnappedBall().getUUID());
        } else {
            model.snappedBallUUID.set(null);
        }
    }

}
