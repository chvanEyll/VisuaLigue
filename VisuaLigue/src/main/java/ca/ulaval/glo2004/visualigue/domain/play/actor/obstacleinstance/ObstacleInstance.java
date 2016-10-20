package ca.ulaval.glo2004.visualigue.domain.play.actor.obstacleinstance;

import ca.ulaval.glo2004.visualigue.domain.obstacle.Obstacle;
import ca.ulaval.glo2004.visualigue.domain.play.actor.ActorInstance;

public class ObstacleInstance extends ActorInstance {

    private Obstacle obstacle;

    public ObstacleInstance(Obstacle obstacle) {
        this.obstacle = obstacle;
    }
}
