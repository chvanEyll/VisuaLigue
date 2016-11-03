package ca.ulaval.glo2004.visualigue.persistence;

import ca.ulaval.glo2004.visualigue.domain.obstacle.ObstacleAlreadyExistsException;
import ca.ulaval.glo2004.visualigue.domain.obstacle.ObstacleNotFoundException;
import ca.ulaval.glo2004.visualigue.domain.settings.Settings;
import ca.ulaval.glo2004.visualigue.domain.settings.SettingsAlreadyExistsException;
import ca.ulaval.glo2004.visualigue.domain.settings.SettingsNotFoundException;
import ca.ulaval.glo2004.visualigue.domain.settings.SettingsRepository;
import ca.ulaval.glo2004.visualigue.persistence.marshalling.XmlRepositoryMarshaller;
import java.util.Map;
import java.util.stream.Collectors;
import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class XmlSettingsRepository implements SettingsRepository {

    private final XmlRepositoryMarshaller<Settings> xmlRepositoryMarshaller;
    private final Map<String, Settings> settingsMap;

    @Inject
    public XmlSettingsRepository(XmlRepositoryMarshaller<Settings> xmlRepositoryMarshaller) {
        this.xmlRepositoryMarshaller = xmlRepositoryMarshaller;
        settingsMap = xmlRepositoryMarshaller.unmarshalAll();
    }

    @Override
    public String persist(Settings settings) throws SettingsAlreadyExistsException {
        if (settingsMap.containsValue(settings)) {
            throw new ObstacleAlreadyExistsException(String.format("Settings with UUID '%s' already exists.", settings.getUUID()));
        }
        settingsMap.put(settings.getUUID(), settings);
        xmlRepositoryMarshaller.marshal(settings, settings.getUUID());
        return settings.getUUID();
    }

    @Override
    public void update(Settings settings) {
        if (!settingsMap.containsValue(settings)) {
            throw new IllegalStateException("Update requested for an object that is not persisted.");
        }
        xmlRepositoryMarshaller.marshal(settings, settings.getUUID());
    }

    @Override
    public void delete(Settings settings) throws SettingsNotFoundException {
        if (!settingsMap.containsKey(settings.getUUID())) {
            throw new ObstacleNotFoundException(String.format("Cannot find settings with UUID '%s'.", settings.getUUID()));
        }
        xmlRepositoryMarshaller.remove(settings.getUUID());
        settingsMap.remove(settings.getUUID());
    }

    @Override
    public Settings getFirstOrDefault() {
        if (settingsMap.size() < 1) {
            persist(new Settings());
        }
        return settingsMap.values().stream().findFirst().get();
    }

    @Override
    public void clear() {
        settingsMap.values().stream().collect(Collectors.toList()).forEach(uuid -> {
            delete(uuid);
        });
    }

}
