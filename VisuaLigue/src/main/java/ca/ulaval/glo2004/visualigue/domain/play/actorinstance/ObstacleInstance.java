package ca.ulaval.glo2004.visualigue.domain.play.actorinstance;

import ca.ulaval.glo2004.visualigue.domain.obstacle.Obstacle;
import ca.ulaval.glo2004.visualigue.domain.xmladapters.XmlObstacleInstanceAdapter;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlJavaTypeAdapter(XmlObstacleInstanceAdapter.class)
public class ObstacleInstance extends ActorInstance {

    private String obstacleUUID;
    @XmlTransient
    private Obstacle obstacle;

    public ObstacleInstance() {
        //Required for JAXB instanciation.
    }

    public ObstacleInstance(Obstacle obstacle) {
        this.obstacle = obstacle;
    }

    public String getObstacleUUID() {
        return obstacleUUID;
    }

    public void setObstacleUUID(String obstacleUUID) {
        this.obstacleUUID = obstacleUUID;
    }

    public Obstacle getObstacle() {
        return obstacle;
    }

    public void setObstacle(Obstacle obstacle) {
        this.obstacle = obstacle;
    }
}
