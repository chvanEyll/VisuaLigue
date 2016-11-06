package ca.ulaval.glo2004.visualigue.services.settings;

import ca.ulaval.glo2004.visualigue.VisuaLigueFX;
import ca.ulaval.glo2004.visualigue.domain.settings.Settings;
import ca.ulaval.glo2004.visualigue.domain.settings.SettingsRepository;
import ca.ulaval.glo2004.visualigue.utils.EventHandler;
import java.io.IOException;
import javax.inject.Inject;

public class SettingsService {

    public EventHandler onSettingsChanged = new EventHandler();
    private final SettingsRepository settingsRepository;

    @Inject
    public SettingsService(SettingsRepository settingsRepository) {
        this.settingsRepository = settingsRepository;
    }

    public void reset() throws IOException, Exception {
        VisuaLigueFX.getDefaultContext().apply(true);
    }

    public Boolean getResizeActorsOnZoom() {
        return settingsRepository.getFirstOrDefault().getResizeActorsOnZoom();
    }

    public void setResizeActorsOnZoom(Boolean value) {
        Settings settings = settingsRepository.getFirstOrDefault();
        settings.setResizeActorsOnZoom(value);
        settingsRepository.update(settings);
    }

    public Boolean getShowMovementArrows() {
        return settingsRepository.getFirstOrDefault().getShowMovementArrows();
    }

    public void setShowMovementArrows(Boolean value) {
        Settings settings = settingsRepository.getFirstOrDefault();
        settings.setShowMovementArrows(value);
        settingsRepository.update(settings);
    }

    public Boolean getShowActorLabels() {
        return settingsRepository.getFirstOrDefault().getShowMovementArrows();
    }

    public void setShowActorLabels(Boolean value) {
        Settings settings = settingsRepository.getFirstOrDefault();
        settings.setShowMovementArrows(value);
        settingsRepository.update(settings);
    }

    public Boolean getEnableSmoothMovements() {
        return settingsRepository.getFirstOrDefault().getEnableSmoothMovements();
    }

    public void setEnableSmoothMovements(Boolean value) {
        Settings settings = settingsRepository.getFirstOrDefault();
        settings.setEnableSmoothMovements(value);
        settingsRepository.update(settings);
    }

}
