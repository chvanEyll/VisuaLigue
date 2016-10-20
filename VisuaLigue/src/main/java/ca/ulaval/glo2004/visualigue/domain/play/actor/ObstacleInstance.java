package ca.ulaval.glo2004.visualigue.domain.play.actor;

import ca.ulaval.glo2004.visualigue.domain.obstacle.Obstacle;
import java.util.UUID;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@XmlRootElement(name = "obstacleinstance")
@XmlAccessorType(XmlAccessType.FIELD)
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
