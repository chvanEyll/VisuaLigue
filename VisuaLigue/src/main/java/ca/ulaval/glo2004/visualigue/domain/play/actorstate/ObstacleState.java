package ca.ulaval.glo2004.visualigue.domain.play.actorstate;

import ca.ulaval.glo2004.visualigue.domain.play.position.Position;

public class ObstacleState extends ActorState {

    private Position position;

    public ObstacleState(Position position) {
        this.position = position;
    }
}
