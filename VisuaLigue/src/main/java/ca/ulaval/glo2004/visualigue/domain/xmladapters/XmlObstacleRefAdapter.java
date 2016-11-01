package ca.ulaval.glo2004.visualigue.domain.xmladapters;

import ca.ulaval.glo2004.visualigue.domain.obstacle.Obstacle;
import ca.ulaval.glo2004.visualigue.domain.obstacle.ObstacleRepository;
import javax.inject.Inject;
import javax.xml.bind.annotation.adapters.XmlAdapter;

public class XmlObstacleRefAdapter extends XmlAdapter<String, Obstacle> {

    @Inject private ObstacleRepository obstacleRepository;

    @Override
    public Obstacle unmarshal(String obstacleUUID) throws Exception {
        return obstacleRepository.get(obstacleUUID);
    }

    @Override
    public String marshal(Obstacle obstacle) throws Exception {
        return obstacle.getUUID();
    }

}
