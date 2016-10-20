package ca.ulaval.glo2004.visualigue.domain.play.actor.obstacleinstance;

import ca.ulaval.glo2004.visualigue.domain.obstacle.Obstacle;
import ca.ulaval.glo2004.visualigue.domain.play.actor.Actor;

public class ObstacleInstance extends Actor {

    private Obstacle obstacle;

    public ObstacleInstance(Obstacle obstacle) {
        this.obstacle = obstacle;
    }
}
