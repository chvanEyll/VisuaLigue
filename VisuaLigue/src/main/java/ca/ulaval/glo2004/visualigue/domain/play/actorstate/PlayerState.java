package ca.ulaval.glo2004.visualigue.domain.play.actorstate;

import ca.ulaval.glo2004.visualigue.domain.play.position.Position;

public class PlayerState extends ActorState {

    private Position position;
    private Double orientation;

    public PlayerState(Position position, Double orientation) {
        this.position = position;
        this.orientation = orientation;
    }

}
