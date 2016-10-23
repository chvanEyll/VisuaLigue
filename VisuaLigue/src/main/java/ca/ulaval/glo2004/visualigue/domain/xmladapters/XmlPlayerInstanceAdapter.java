package ca.ulaval.glo2004.visualigue.domain.xmladapters;

import ca.ulaval.glo2004.visualigue.domain.play.actorinstance.PlayerInstance;
import ca.ulaval.glo2004.visualigue.domain.sport.playercategory.PlayerCategoryRepository;
import javax.inject.Inject;
import javax.xml.bind.annotation.adapters.XmlAdapter;

public class XmlPlayerInstanceAdapter extends XmlAdapter<PlayerInstance, PlayerInstance> {

    @Inject PlayerCategoryRepository playerCategoryRepository;

    @Override
    public PlayerInstance unmarshal(PlayerInstance playerInstance) throws Exception {
        playerInstance.setPlayerCategory(playerCategoryRepository.get(playerInstance.getPlayerCategoryUUID()));
        return playerInstance;
    }

    @Override
    public PlayerInstance marshal(PlayerInstance playerInstance) throws Exception {
        playerInstance.setPlayerCategoryUUID(playerInstance.getPlayerCategory().getUUID());
        return playerInstance;
    }

}
