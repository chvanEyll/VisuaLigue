package ca.ulaval.glo2004.visualigue.services.settings;

import ca.ulaval.glo2004.visualigue.VisuaLigue;
import java.io.IOException;

public class SettingsService {

    public void reset() throws IOException, Exception {
        VisuaLigue.getDefaultContext().apply(true);
    }

}
