package ca.ulaval.glo2004.visualigue.ui;

import ca.ulaval.glo2004.visualigue.GuiceInjector;
import ca.ulaval.glo2004.visualigue.utils.CachedClassLoader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;

public class InjectableFXMLLoader {

    public static final CachedClassLoader CACHED_CLASS_LOADER = new CachedClassLoader(FXMLLoader.getDefaultClassLoader());
    private static final InjectableFXMLLoader INSTANCE = new InjectableFXMLLoader();

    public static FXMLLoader createLoader(String viewName, Object controller, Node root) {
        FXMLLoader fxmlLoader = new FXMLLoader(InjectableFXMLLoader.class.getResource(viewName));
        fxmlLoader.setClassLoader(CACHED_CLASS_LOADER);
        fxmlLoader.setControllerFactory(GuiceInjector.getInstance()::getInstance);
        if (controller != null) {
            fxmlLoader.setController(controller);
        }
        if (root != null) {
            fxmlLoader.setRoot(root);
        }
        return fxmlLoader;
    }

    public static View loadView(String viewName) {
        FXMLLoader fxmlLoader = createLoader(viewName, null, null);
        try {
            fxmlLoader.load();
        } catch (IOException ex) {
            Logger.getLogger(InjectableFXMLLoader.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new View(fxmlLoader.getController(), fxmlLoader.getRoot());
    }
}
