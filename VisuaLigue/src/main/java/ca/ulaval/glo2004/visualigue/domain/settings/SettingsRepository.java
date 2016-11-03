package ca.ulaval.glo2004.visualigue.domain.settings;

public interface SettingsRepository {

    String persist(Settings settings) throws SettingsAlreadyExistsException;

    void update(Settings settings);

    Settings getFirstOrDefault() throws SettingsNotFoundException;

    void delete(Settings settings) throws SettingsNotFoundException;

    void clear();
}
