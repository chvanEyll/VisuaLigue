package ca.ulaval.glo2004.visualigue.domain.xmladapters;

import ca.ulaval.glo2004.visualigue.domain.obstacle.ObstacleRepository;
import ca.ulaval.glo2004.visualigue.domain.play.actorinstance.ObstacleInstance;
import javax.inject.Inject;
import javax.xml.bind.annotation.adapters.XmlAdapter;

public class XmlObstacleInstanceAdapter extends XmlAdapter<ObstacleInstance, ObstacleInstance> {

    @Inject ObstacleRepository obstacleRepository;

    @Override
    public ObstacleInstance unmarshal(ObstacleInstance obstacleInstance) throws Exception {
        obstacleInstance.setObstacle(obstacleRepository.get(obstacleInstance.getObstacleUUID()));
        return obstacleInstance;
    }

    @Override
    public ObstacleInstance marshal(ObstacleInstance obstacleInstance) throws Exception {
        obstacleInstance.setObstacleUUID(obstacleInstance.getObstacle().getUUID());
        return obstacleInstance;
    }

}
