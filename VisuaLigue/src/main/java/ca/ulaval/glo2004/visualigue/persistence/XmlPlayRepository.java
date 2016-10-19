package ca.ulaval.glo2004.visualigue.persistence;

import ca.ulaval.glo2004.visualigue.domain.play.Play;
import ca.ulaval.glo2004.visualigue.domain.play.PlayAlreadyExistsException;
import ca.ulaval.glo2004.visualigue.domain.play.PlayNotFoundException;
import ca.ulaval.glo2004.visualigue.domain.play.PlayRepository;
import ca.ulaval.glo2004.visualigue.persistence.marshalling.XmlRepositoryMarshaller;
import ca.ulaval.glo2004.visualigue.utils.ListUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.swing.SortOrder;

@Singleton
public class XmlPlayRepository implements PlayRepository {

    private final XmlRepositoryMarshaller<Play> xmlRepositoryMarshaller;
    private final Map<UUID, Play> plays;

    @Inject
    public XmlPlayRepository(XmlRepositoryMarshaller<Play> xmlRepositoryMarshaller) {
        this.xmlRepositoryMarshaller = xmlRepositoryMarshaller;
        plays = xmlRepositoryMarshaller.unmarshalAll();
    }

    @Override
    public UUID persist(Play play) throws PlayAlreadyExistsException {
        if (plays.containsValue(play)) {
            throw new PlayAlreadyExistsException(String.format("An play with UUID '%s' already exists.", play.getUUID()));
        }
        plays.put(play.getUUID(), play);
        xmlRepositoryMarshaller.marshal(play, play.getUUID());
        return play.getUUID();
    }

    @Override
    public void update(Play play) {
        if (!plays.containsValue(play)) {
            throw new IllegalStateException("Update requested for an object that is not persisted.");
        }
        xmlRepositoryMarshaller.marshal(play, play.getUUID());
    }

    @Override
    public void delete(UUID uuid) throws PlayNotFoundException {
        if (!plays.containsKey(uuid)) {
            throw new PlayNotFoundException(String.format("Cannot find play with UUID '%s'.", uuid.toString()));
        }
        xmlRepositoryMarshaller.remove(uuid);
        plays.remove(uuid);
    }

    @Override
    public Play get(UUID uuid) throws PlayNotFoundException {
        Play play = plays.get(uuid);
        if (play == null) {
            throw new PlayNotFoundException(String.format("Cannot find play with UUID '%s'.", uuid.toString()));
        }
        return play;
    }

    @Override
    public List<Play> getAll(Function<Play, Comparable> sortFunction, SortOrder sortOrder) {
        List<Play> playList = new ArrayList(plays.values());
        return ListUtils.sort(playList, sortFunction, sortOrder);
    }

    @Override
    public void clear() {
        plays.keySet().stream().collect(Collectors.toList()).forEach(uuid -> {
            try {
                delete(uuid);
            } catch (PlayNotFoundException ex) {
                Logger.getLogger(XmlPlayRepository.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

}
