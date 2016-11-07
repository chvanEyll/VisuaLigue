package ca.ulaval.glo2004.visualigue.domain.play.actor;

import ca.ulaval.glo2004.visualigue.domain.obstacle.Obstacle;
import ca.ulaval.glo2004.visualigue.domain.xmladapters.XmlObstacleRefAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

public class ObstacleActor extends Actor {

    @XmlJavaTypeAdapter(XmlObstacleRefAdapter.class)
    private Obstacle obstacle;

    public ObstacleActor() {
        //Required for JAXB instanciation.
    }

    public ObstacleActor(Obstacle obstacle) {
        this.obstacle = obstacle;
    }

    public Obstacle getObstacle() {
        return obstacle;
    }

}
