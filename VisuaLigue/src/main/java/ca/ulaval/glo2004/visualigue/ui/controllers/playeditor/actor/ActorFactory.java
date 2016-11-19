package ca.ulaval.glo2004.visualigue.ui.controllers.playeditor.actor;

import ca.ulaval.glo2004.visualigue.ui.InjectableFXMLLoader;
import ca.ulaval.glo2004.visualigue.ui.View;
import ca.ulaval.glo2004.visualigue.ui.models.actors.ActorModel;
import ca.ulaval.glo2004.visualigue.ui.models.actors.BallActorModel;
import ca.ulaval.glo2004.visualigue.ui.models.actors.ObstacleActorModel;
import ca.ulaval.glo2004.visualigue.ui.models.actors.PlayerActorModel;

public class ActorFactory {

    public View create(ActorModel actorModel) {
        if (actorModel instanceof PlayerActorModel) {
            return InjectableFXMLLoader.loadView(PlayerController.VIEW_NAME);
        } else if (actorModel instanceof ObstacleActorModel) {
            return InjectableFXMLLoader.loadView(ObstacleController.VIEW_NAME);
        } else if (actorModel instanceof BallActorModel) {
            return InjectableFXMLLoader.loadView(BallController.VIEW_NAME);
        } else {
            throw new RuntimeException("Unsupported ActorModel subclass.");
        }
    }

}