package ca.ulaval.glo2004.visualigue;

import com.google.inject.Guice;
import com.google.inject.Injector;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;

public class GuiceFXMLLoader {

    private static final Injector injector = Guice.createInjector();

    private static class GuiceFXMLLoaderHolder {

        private static final GuiceFXMLLoader INSTANCE = new GuiceFXMLLoader();
    }

    public static GuiceFXMLLoader getInstance() {
        return GuiceFXMLLoaderHolder.INSTANCE;
    }

    public static FXMLLoader load(String viewName) {
        FXMLLoader fxmlLoader = new FXMLLoader(GuiceFXMLLoader.class.getResource(viewName));
        fxmlLoader.setControllerFactory(injector::getInstance);
        try {
            fxmlLoader.load();
        } catch (IOException ex) {
            Logger.getLogger(GuiceFXMLLoader.class.getName()).log(Level.SEVERE, null, ex);
        }
        return fxmlLoader;
    }
}
