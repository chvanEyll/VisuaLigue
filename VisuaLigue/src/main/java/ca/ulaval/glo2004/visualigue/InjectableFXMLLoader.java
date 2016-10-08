package ca.ulaval.glo2004.visualigue;

import com.google.inject.Guice;
import com.google.inject.Injector;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;

public class InjectableFXMLLoader {

    private static final Injector injector = Guice.createInjector();

    private static class InjectableFXMLLoaderHolder {

        private static final InjectableFXMLLoader INSTANCE = new InjectableFXMLLoader();
    }

    public static InjectableFXMLLoader getInstance() {
        return InjectableFXMLLoaderHolder.INSTANCE;
    }

    public static FXMLLoader load(String viewName) {
        return load(viewName, null);
    }

    public static FXMLLoader load(String viewName, Node rootNode) {
        FXMLLoader fxmlLoader = new FXMLLoader(InjectableFXMLLoader.class.getResource(viewName));
        fxmlLoader.setControllerFactory(injector::getInstance);
        if (rootNode != null) {
            fxmlLoader.setRoot(rootNode);
        }
        try {
            fxmlLoader.load();
        } catch (IOException ex) {
            Logger.getLogger(InjectableFXMLLoader.class.getName()).log(Level.SEVERE, null, ex);
        }
        return fxmlLoader;
    }
}
