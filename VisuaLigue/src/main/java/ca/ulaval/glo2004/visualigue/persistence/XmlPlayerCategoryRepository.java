package ca.ulaval.glo2004.visualigue.persistence;

import ca.ulaval.glo2004.visualigue.domain.sport.playercategory.PlayerCategory;
import ca.ulaval.glo2004.visualigue.domain.sport.playercategory.PlayerCategoryAlreadyExistsException;
import ca.ulaval.glo2004.visualigue.domain.sport.playercategory.PlayerCategoryNotFoundException;
import ca.ulaval.glo2004.visualigue.domain.sport.playercategory.PlayerCategoryRepository;
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
public class XmlPlayerCategoryRepository implements PlayerCategoryRepository {

    public final EventHandler<PlayerCategory> onPlayerCategoryDelete = new EventHandler();
    private final XmlRepositoryMarshaller<PlayerCategory> xmlRepositoryMarshaller;
    private final Map<String, PlayerCategory> playerCategories;

    @Inject
    public XmlPlayerCategoryRepository(XmlRepositoryMarshaller<PlayerCategory> xmlRepositoryMarshaller) {
        this.xmlRepositoryMarshaller = xmlRepositoryMarshaller;
        playerCategories = xmlRepositoryMarshaller.unmarshalAll();
    }

    @Override
    public String persist(PlayerCategory playerCategory) throws PlayerCategoryAlreadyExistsException {
        if (playerCategories.containsValue(playerCategory)) {
            throw new PlayerCategoryAlreadyExistsException(String.format("A player category with UUID '%s' already exists.", playerCategory.getUUID()));
        }
        playerCategories.put(playerCategory.getUUID(), playerCategory);
        xmlRepositoryMarshaller.marshal(playerCategory, playerCategory.getUUID());
        return playerCategory.getUUID();
    }

    @Override
    public void update(PlayerCategory playerCategory) {
        if (!playerCategories.containsValue(playerCategory)) {
            throw new IllegalStateException("Update requested for an object that is not persisted.");
        }
        xmlRepositoryMarshaller.marshal(playerCategory, playerCategory.getUUID());
    }

    @Override
    public void simulateDelete(PlayerCategory playerCategory) throws PlayerCategoryNotFoundException {
        if (!playerCategories.containsKey(playerCategory.getUUID())) {
            throw new PlayerCategoryNotFoundException(String.format("Cannot find player category with UUID '%s'.", playerCategory.getUUID()));
        }
        onPlayerCategoryDelete.fire(this, playerCategory);
    }

    @Override
    public void delete(PlayerCategory playerCategory) throws PlayerCategoryNotFoundException {
        simulateDelete(playerCategory);
        xmlRepositoryMarshaller.remove(playerCategory.getUUID());
        playerCategories.remove(playerCategory.getUUID());
    }

    @Override
    public PlayerCategory get(String uuid) throws PlayerCategoryNotFoundException {
        PlayerCategory playerCategory = playerCategories.get(uuid);
        if (playerCategory == null) {
            throw new PlayerCategoryNotFoundException(String.format("Cannot find player category with UUID '%s'.", uuid));
        }
        return playerCategory;
    }

    @Override
    public List<PlayerCategory> getAll(Function<PlayerCategory, Comparable> sortFunction, SortOrder sortOrder) {
        List<PlayerCategory> playerCategoryList = new ArrayList(playerCategories.values());
        return ListUtils.sort(playerCategoryList, sortFunction, sortOrder);
    }

    @Override
    public Boolean isEmpty() {
        return playerCategories.isEmpty();
    }

    @Override
    public void clear() {
        playerCategories.values().stream().collect(Collectors.toList()).forEach(uuid -> {
            delete(uuid);
        });
    }

}
