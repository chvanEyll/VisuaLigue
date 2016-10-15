package ca.ulaval.glo2004.visualigue.domain.obstacle;

import javax.inject.Singleton;

@Singleton
public class ObstacleFactory {

    public Obstacle create(String name) {
        return new Obstacle(name);
    }
}
