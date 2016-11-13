package ca.ulaval.glo2004.visualigue.domain.play.actor;

import ca.ulaval.glo2004.visualigue.domain.play.actorstate.ActorState;
import ca.ulaval.glo2004.visualigue.domain.play.actorstate.BallState;

public class BallActor extends Actor {

    public BallActor() {
        //Required for JAXB instanciation.
    }

    @Override
    public ActorState getBaseState() {
        return new BallState();
    }
}
