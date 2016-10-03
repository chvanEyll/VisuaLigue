package ca.ulaval.glo2004.visualigue;

import com.google.inject.Guice;
import com.google.inject.Injector;
import java.io.IOException;
import java.net.URL;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

public class GuiceFXMLLoader {

    private static final Injector injector = Guice.createInjector();

    private static class GuiceFXMLLoaderHolder {

        private static final GuiceFXMLLoader INSTANCE = new GuiceFXMLLoader();
    }

    public static GuiceFXMLLoader getInstance() {
        return GuiceFXMLLoaderHolder.INSTANCE;
    }

    public static FXMLLoader createLoader(URL resource) {
        FXMLLoader fxmlLoader = new FXMLLoader(resource);
        fxmlLoader.setControllerFactory(injector::getInstance);
        return fxmlLoader;
    }

    public static Parent load(URL resource) throws IOException {
        FXMLLoader fxmlLoader = createLoader(resource);
        return fxmlLoader.load();
    }
}
