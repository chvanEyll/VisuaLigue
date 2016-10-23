package ca.ulaval.glo2004.visualigue.domain.xmladapters;

import ca.ulaval.glo2004.visualigue.domain.sport.Sport;
import ca.ulaval.glo2004.visualigue.domain.sport.playercategory.PlayerCategoryRepository;
import javax.inject.Inject;
import javax.xml.bind.annotation.adapters.XmlAdapter;

public class XmlSportAdapter extends XmlAdapter<Sport, Sport> {

    @Inject PlayerCategoryRepository playerCategoryRepository;

    @Override
    public Sport unmarshal(Sport sport) throws Exception {
        sport.getPlayerCategoryUUIDs().forEach(playerCategoryUUID -> {
            sport.getPlayerCategories().add(playerCategoryRepository.get(playerCategoryUUID));
        });
        return sport;
    }

    @Override
    public Sport marshal(Sport sport) throws Exception {
        sport.getPlayerCategories().forEach(playerCategory -> {
            sport.getPlayerCategoryUUIDs().add(playerCategory.getUUID());
        });
        return sport;
    }

}
