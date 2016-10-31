package ca.ulaval.glo2004.visualigue.persistence;

import ca.ulaval.glo2004.visualigue.domain.sport.playercategory.PlayerCategory;
import ca.ulaval.glo2004.visualigue.domain.sport.playercategory.PlayerCategoryAlreadyExistsException;
import ca.ulaval.glo2004.visualigue.domain.sport.playercategory.PlayerCategoryNotFoundException;
import ca.ulaval.glo2004.visualigue.domain.sport.playercategory.PlayerCategoryRepository;
import ca.ulaval.glo2004.visualigue.persistence.marshalling.XmlRepositoryMarshaller;
import ca.ulaval.glo2004.visualigue.utils.ListUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.swing.SortOrder;

@Singleton
public class XmlPlayerCategoryRepository implements PlayerCategoryRepository {

    private final XmlRepositoryMarshaller<PlayerCategory> xmlRepositoryMarshaller;
    private final Map<String, PlayerCategory> sports;

    @Inject
    public XmlPlayerCategoryRepository(XmlRepositoryMarshaller<PlayerCategory> xmlRepositoryMarshaller) {
        this.xmlRepositoryMarshaller = xmlRepositoryMarshaller;
        sports = xmlRepositoryMarshaller.unmarshalAll();
    }

    @Override
    public String persist(PlayerCategory sport) throws PlayerCategoryAlreadyExistsException {
        if (sports.containsValue(sport)) {
            throw new PlayerCategoryAlreadyExistsException(String.format("A player category with UUID '%s' already exists.", sport.getUUID()));
        }
        sports.put(sport.getUUID(), sport);
        xmlRepositoryMarshaller.marshal(sport, sport.getUUID());
        return sport.getUUID();
    }

    @Override
    public void update(PlayerCategory sport) {
        if (!sports.containsValue(sport)) {
            throw new IllegalStateException("Update requested for an object that is not persisted.");
        }
        xmlRepositoryMarshaller.marshal(sport, sport.getUUID());
    }

    @Override
    public void delete(PlayerCategory sport) throws PlayerCategoryNotFoundException {
        if (!sports.containsKey(sport.getUUID())) {
            throw new PlayerCategoryNotFoundException(String.format("Cannot find player category with UUID '%s'.", sport.getUUID()));
        }
        xmlRepositoryMarshaller.remove(sport.getUUID());
        sports.remove(sport.getUUID());
    }

    @Override
    public PlayerCategory get(String uuid) throws PlayerCategoryNotFoundException {
        PlayerCategory sport = sports.get(uuid);
        if (sport == null) {
            throw new PlayerCategoryNotFoundException(String.format("Cannot find player category with UUID '%s'.", uuid));
        }
        return sport;
    }

    @Override
    public List<PlayerCategory> getAll(Function<PlayerCategory, Comparable> sortFunction, SortOrder sortOrder) {
        List<PlayerCategory> sportList = new ArrayList(sports.values());
        return ListUtils.sort(sportList, sortFunction, sortOrder);
    }

    @Override
    public Boolean isEmpty() {
        return sports.isEmpty();
    }

    @Override
    public void clear() {
        sports.values().stream().collect(Collectors.toList()).forEach(uuid -> {
            delete(uuid);
        });
    }

}
