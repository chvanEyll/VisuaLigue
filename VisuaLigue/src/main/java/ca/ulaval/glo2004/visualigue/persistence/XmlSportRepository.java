package ca.ulaval.glo2004.visualigue.persistence;

import ca.ulaval.glo2004.visualigue.domain.sport.Sport;
import ca.ulaval.glo2004.visualigue.domain.sport.SportAlreadyExistsException;
import ca.ulaval.glo2004.visualigue.domain.sport.SportNotFoundException;
import ca.ulaval.glo2004.visualigue.domain.sport.SportRepository;
import ca.ulaval.glo2004.visualigue.persistence.marshalling.XmlRepositoryMarshaller;
import ca.ulaval.glo2004.visualigue.utils.ListUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.swing.SortOrder;

@Singleton
public class XmlSportRepository implements SportRepository {

    private final XmlRepositoryMarshaller<Sport> xmlRepositoryMarshaller;
    private final Map<UUID, Sport> sports;

    @Inject
    public XmlSportRepository(XmlRepositoryMarshaller<Sport> xmlRepositoryMarshaller) {
        this.xmlRepositoryMarshaller = xmlRepositoryMarshaller;
        sports = xmlRepositoryMarshaller.unmarshalAll();
        int i = 8;
    }

    @Override
    public UUID persist(Sport sport) throws SportAlreadyExistsException {
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
    public void delete(UUID uuid) throws SportNotFoundException {
        if (!sports.containsKey(uuid)) {
            throw new SportNotFoundException(String.format("Cannot find sport with UUID '%s'.", uuid.toString()));
        }
        xmlRepositoryMarshaller.remove(uuid);
        sports.remove(uuid);
    }

    @Override
    public Sport get(UUID uuid) throws SportNotFoundException {
        Sport sport = sports.get(uuid);
        if (sport == null) {
            throw new SportNotFoundException(String.format("Cannot find sport with UUID '%s'.", uuid.toString()));
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

}