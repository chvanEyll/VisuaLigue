package ca.ulaval.glo2004.visualigue.persistence.sport;

import ca.ulaval.glo2004.visualigue.domain.sport.Sport;
import ca.ulaval.glo2004.visualigue.domain.sport.SportAlreadyExistsException;
import ca.ulaval.glo2004.visualigue.domain.sport.SportNotFoundException;
import ca.ulaval.glo2004.visualigue.domain.sport.SportRepository;
import ca.ulaval.glo2004.visualigue.persistence.marshalling.XmlRepositoryMarshaller;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class XmlSportRepository implements SportRepository {

    private final XmlRepositoryMarshaller<XmlSportRootElement> xmlRepositoryMarshaller;
    private XmlSportRootElement xmlSportRootElement;

    private final Map<UUID, Sport> sports = new ConcurrentHashMap<>();

    @Inject
    public XmlSportRepository(final XmlRepositoryMarshaller<XmlSportRootElement> xmlRepositoryMarshaller,
            final XmlSportAdapter xmlSportAdapter) {
        this.xmlRepositoryMarshaller = xmlRepositoryMarshaller;
        initRepository(xmlSportAdapter);
    }

    private void initRepository(final XmlSportAdapter xmlSportAdapter) {
        xmlRepositoryMarshaller.setMarshallingAdapters(xmlSportAdapter);
        xmlSportRootElement = xmlRepositoryMarshaller.unmarshal(new XmlSportRootElement());
        Collection<Sport> sportElements = xmlSportRootElement.getSports();
        sportElements.forEach(s -> sports.put(s.getUUID(), s));
    }

    @Override
    public void persist(Sport sport) throws SportAlreadyExistsException {
        if (sports.containsValue(sport)) {
            throw new SportAlreadyExistsException(String.format("A sport with UUID '%s' already exists.", sport.getUUID()));
        } else if (sports.values().stream().anyMatch(s -> s.getName().equals(sport.getName()))) {
            throw new SportAlreadyExistsException(String.format("A sport with name '%s' already exists.", sport.getName()));
        }
        sports.put(sport.getUUID(), sport);
        marshal();
    }

    @Override
    public Sport getByUUID(UUID uuid) throws SportNotFoundException {
        Sport sport = sports.get(uuid);
        if (sport == null) {
            throw new SportNotFoundException(String.format("Cannot find sport with UUID '%s'.", uuid));
        }
        return sport;
    }

    @Override
    public void update(Sport sport) throws SportAlreadyExistsException {
        if (!sports.containsValue(sport)) {
            throw new IllegalStateException("Update requested for an object that is not persisted.");
        } else if (sports.values().stream().anyMatch(s -> s != sport && s.getName().equals(sport.getName()))) {
            throw new SportAlreadyExistsException(String.format("A sport with name '%s' already exists.", sport.getName()));
        }

        marshal();
    }

    @Override
    public Set<Sport> getAll() {
        return new HashSet<>(sports.values());
    }

    @Override
    public Boolean isEmpty() {
        return sports.isEmpty();
    }

    private void marshal() {
        xmlSportRootElement.setSports(sports.values());
        xmlRepositoryMarshaller.marshal(xmlSportRootElement);
    }

}
