package ca.ulaval.glo2004.visualigue.domain.play.actorinstance;

import ca.ulaval.glo2004.visualigue.domain.obstacle.Obstacle;
import ca.ulaval.glo2004.visualigue.domain.xmladapters.XmlObstacleRefAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

public class ObstacleInstance extends ActorInstance {

    @XmlJavaTypeAdapter(XmlObstacleRefAdapter.class)
    private Obstacle obstacle;

    public ObstacleInstance() {
        //Required for JAXB instanciation.
    }

    public ObstacleInstance(Obstacle obstacle) {
        this.obstacle = obstacle;
    }

    public Obstacle getObstacle() {
        return obstacle;
    }

    public void setObstacle(Obstacle obstacle) {
        this.obstacle = obstacle;
    }
}
