package ca.ulaval.glo2004.visualigue.domain.xmladapters;

import ca.ulaval.glo2004.visualigue.domain.sport.playercategory.PlayerCategory;
import ca.ulaval.glo2004.visualigue.domain.sport.playercategory.PlayerCategoryRepository;
import javax.inject.Inject;
import javax.xml.bind.annotation.adapters.XmlAdapter;

public class XmlPlayerCategoryRefAdapter extends XmlAdapter<String, PlayerCategory> {

    @Inject private PlayerCategoryRepository playerCategoryRepository;

    @Override
    public PlayerCategory unmarshal(String playerCategoryUUID) throws Exception {
        return playerCategoryRepository.get(playerCategoryUUID);
    }

    @Override
    public String marshal(PlayerCategory playerCategory) throws Exception {
        return playerCategory.getUUID();
    }

}
