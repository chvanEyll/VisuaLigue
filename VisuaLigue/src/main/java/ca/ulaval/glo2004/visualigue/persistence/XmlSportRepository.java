package ca.ulaval.glo2004.visualigue.persistence;

import ca.ulaval.glo2004.visualigue.domain.sport.Sport;
import ca.ulaval.glo2004.visualigue.domain.sport.SportAlreadyExistsException;
import ca.ulaval.glo2004.visualigue.domain.sport.SportNotFoundException;
import ca.ulaval.glo2004.visualigue.domain.sport.SportRepository;
import ca.ulaval.glo2004.visualigue.domain.xmladapters.XmlPlayerCategoryRefAdapter;
import ca.ulaval.glo2004.visualigue.persistence.marshalling.XmlRepositoryMarshaller;
import ca.ulaval.glo2004.visualigue.utils.EventHandler;
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
public class XmlSportRepository implements SportRepository {

    public final EventHandler<Sport> onSportDelete = new EventHandler();
    private final XmlRepositoryMarshaller<Sport> xmlRepositoryMarshaller;
    private final Map<String, Sport> sports;

    @Inject
    public XmlSportRepository(XmlRepositoryMarshaller<Sport> xmlRepositoryMarshaller, XmlPlayerCategoryRefAdapter xmlPlayerCategoryRefAdapter) {
        this.xmlRepositoryMarshaller = xmlRepositoryMarshaller;
        xmlRepositoryMarshaller.setMarshallingAdapters(xmlPlayerCategoryRefAdapter);
        sports = xmlRepositoryMarshaller.unmarshalAll();
    }

    @Override
    public String persist(Sport sport) throws SportAlreadyExistsException {
        if (sports.containsValue(sport)) {
            throw new SportAlreadyExistsException(String.format("A sport with UUID '%s' already exists.", sport.getUUID()));
        } else if (sports.values().stream().anyMatch(s -> s.getName().equals(sport.getName()))) {
            throw new SportAlreadyExistsException(String.format("A sport with name '%s' already exists.", sport.getName()));
        }
        sports.put(sport.getUUID(), sport);
        xmlRepositoryMarshaller.marshal(sport, sport.getUUID());
        return sport.getUUID();
    }

    @Override
    public void update(Sport sport) throws SportAlreadyExistsException {
        if (!sports.containsValue(sport)) {
            throw new IllegalStateException("Update requested for an object that is not persisted.");
        } else if (sports.values().stream().anyMatch(s -> s != sport && s.getName().equals(sport.getName()))) {
            throw new SportAlreadyExistsException(String.format("A sport with name '%s' already exists.", sport.getName()));
        }
        xmlRepositoryMarshaller.marshal(sport, sport.getUUID());
    }

    @Override
    public void delete(Sport sport) throws SportNotFoundException {
        if (!sports.containsKey(sport.getUUID())) {
            throw new SportNotFoundException(String.format("Cannot find sport with UUID '%s'.", sport.getUUID()));
        }
        onSportDelete.fire(this, sport);
        xmlRepositoryMarshaller.remove(sport.getUUID());
        sports.remove(sport.getUUID());
    }

    @Override
    public Sport get(String uuid) throws SportNotFoundException {
        Sport sport = sports.get(uuid);
        if (sport == null) {
            throw new SportNotFoundException(String.format("Cannot find sport with UUID '%s'.", uuid));
        }
        return sport;
    }

    @Override
    public List<Sport> getAll(Function<Sport, Comparable> sortFunction, SortOrder sortOrder) {
        List<Sport> sportList = new ArrayList(sports.values());
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
