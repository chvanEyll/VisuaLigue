package ca.ulaval.glo2004.visualigue.domain.play.actorinstance;

import ca.ulaval.glo2004.visualigue.domain.obstacle.Obstacle;
import ca.ulaval.glo2004.visualigue.domain.xmladapters.XmlObstacleInstanceAdapter;
import java.util.UUID;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlJavaTypeAdapter(XmlObstacleInstanceAdapter.class)
public class ObstacleInstance extends ActorInstance {

    private UUID obstacleUUID;
    @XmlTransient
    private Obstacle obstacle;

    public ObstacleInstance(Obstacle obstacle) {
        this.obstacle = obstacle;
    }

    public UUID getObstacleUUID() {
        return obstacleUUID;
    }

    public void setObstacleUUID(UUID obstacleUUID) {
        this.obstacleUUID = obstacleUUID;
    }

    public Obstacle getObstacle() {
        return obstacle;
    }

    public void setObstacle(Obstacle obstacle) {
        this.obstacle = obstacle;
    }
}
