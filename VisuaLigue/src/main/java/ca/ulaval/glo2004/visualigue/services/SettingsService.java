package ca.ulaval.glo2004.visualigue.services;

import ca.ulaval.glo2004.visualigue.VisuaLigue;
import java.io.File;
import java.io.IOException;
import org.apache.commons.io.FileUtils;

public class SettingsService {

    public void reset() throws IOException, Exception {
        FileUtils.deleteDirectory(new File(VisuaLigue.getRepositoryDirectory()));
        VisuaLigue.getDefaultContext().apply();
    }

}
