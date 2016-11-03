package ca.ulaval.glo2004.visualigue.persistence;

import ca.ulaval.glo2004.visualigue.domain.obstacle.Obstacle;
import ca.ulaval.glo2004.visualigue.domain.play.*;
import ca.ulaval.glo2004.visualigue.domain.sport.Sport;
import ca.ulaval.glo2004.visualigue.domain.sport.playercategory.PlayerCategory;
import ca.ulaval.glo2004.visualigue.domain.xmladapters.XmlObstacleRefAdapter;
import ca.ulaval.glo2004.visualigue.domain.xmladapters.XmlPlayerCategoryRefAdapter;
import ca.ulaval.glo2004.visualigue.domain.xmladapters.XmlSportRefAdapter;
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
public class XmlPlayRepository implements PlayRepository {

    private final XmlRepositoryMarshaller<Play> xmlRepositoryMarshaller;
    private final Map<String, Play> plays;

    @Inject
    public XmlPlayRepository(XmlRepositoryMarshaller<Play> xmlRepositoryMarshaller,
            XmlObstacleRepository xmlObstacleRepository, XmlPlayerCategoryRepository xmlPlayerCategoryRepository, XmlSportRepository xmlSportRepository,
            XmlObstacleRefAdapter xmlObstacleRefAdapter, XmlPlayerCategoryRefAdapter xmlPlayerCategoryRefAdapter, XmlSportRefAdapter xmlSportRefAdapter) {
        this.xmlRepositoryMarshaller = xmlRepositoryMarshaller;
        xmlObstacleRepository.onObstacleDelete.addHandler(this::onObstacleDelete);
        xmlPlayerCategoryRepository.onPlayerCategoryDelete.addHandler(this::onPlayerCategoryDelete);
        xmlSportRepository.onSportDelete.addHandler(this::onSportDelete);
        xmlRepositoryMarshaller.setMarshallingAdapters(xmlObstacleRefAdapter);
        xmlRepositoryMarshaller.setMarshallingAdapters(xmlPlayerCategoryRefAdapter);
        xmlRepositoryMarshaller.setMarshallingAdapters(xmlSportRefAdapter);
        plays = xmlRepositoryMarshaller.unmarshalAll();
    }

    @Override
    public String persist(Play play) throws PlayAlreadyExistsException {
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
    public void delete(Play play) throws PlayNotFoundException {
        if (!plays.containsKey(play.getUUID())) {
            throw new PlayNotFoundException(String.format("Cannot find play with UUID '%s'.", play.getUUID()));
        }
        xmlRepositoryMarshaller.remove(play.getUUID());
        plays.remove(play.getUUID());
    }

    @Override
    public void discard(Play play) throws PlayNotFoundException {
        Play restoredPlay = xmlRepositoryMarshaller.unmarshal(play.getUUID());
        plays.put(play.getUUID(), restoredPlay);
    }

    @Override
    public Play get(String uuid) throws PlayNotFoundException {
        Play play = plays.get(uuid);
        if (play == null) {
            throw new PlayNotFoundException(String.format("Cannot find play with UUID '%s'.", uuid));
        }
        return play;
    }

    @Override
    public List<Play> getAll() {
        return new ArrayList(plays.values());
    }

    @Override
    public List<Play> getAll(Function<Play, Comparable> sortFunction, SortOrder sortOrder) {
        return ListUtils.sort(getAll(), sortFunction, sortOrder);
    }

    @Override
    public void clear() {
        plays.values().stream().collect(Collectors.toList()).forEach(uuid -> {
            delete(uuid);
        });
    }

    private void onSportDelete(Object sender, Sport sport) {
        getAll().forEach(play -> {
            if (play.getSport() == sport) {
                throw new PlayIntegrityViolationException(String.format("Cannot delete sport with UUID '%s' because it would vioalte integrity of play '%s'.", sport.getUUID(), play.getUUID()), play);
            }
        });
    }

    private void onPlayerCategoryDelete(Object sender, PlayerCategory playerCategory) {
        getAll().forEach(play -> {
            if (play.containsPlayerCategory(playerCategory)) {
                throw new PlayIntegrityViolationException(String.format("Cannot delete player category with UUID '%s' because it would vioalte integrity of play '%s'.", playerCategory.getUUID(), play.getUUID()), play);
            }
        });
    }

    private void onObstacleDelete(Object sender, Obstacle obstacle) {
        getAll().forEach(play -> {
            if (play.containsObstacle(obstacle)) {
                throw new PlayIntegrityViolationException(String.format("Cannot delete obstacle with UUID '%s' because it would vioalte integrity of play '%s'.", obstacle.getUUID(), play.getUUID()), play);
            }
        });
    }

}
