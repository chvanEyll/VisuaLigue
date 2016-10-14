package ca.ulaval.glo2004.visualigue.ui;

import ca.ulaval.glo2004.visualigue.GuiceInjector;
import ca.ulaval.glo2004.visualigue.utils.CachedClassLoader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;

public class InjectableFXMLLoader {

    public static CachedClassLoader cachedClassLoader = new CachedClassLoader(FXMLLoader.getDefaultClassLoader());

    private static class InjectableFXMLLoaderHolder {

        private static final InjectableFXMLLoader INSTANCE = new InjectableFXMLLoader();
    }

    public static InjectableFXMLLoader getInstance() {
        return InjectableFXMLLoaderHolder.INSTANCE;
    }

    public static FXMLLoader createLoader(String viewName) {
        FXMLLoader fxmlLoader = new FXMLLoader(InjectableFXMLLoader.class.getResource(viewName));
        fxmlLoader.setClassLoader(cachedClassLoader);
        fxmlLoader.setControllerFactory(GuiceInjector.getInstance()::getInstance);
        return fxmlLoader;
    }

    public static FXMLLoader load(String viewName) {
        FXMLLoader fxmlLoader = createLoader(viewName);
        try {
            fxmlLoader.load();
        } catch (IOException ex) {
            Logger.getLogger(InjectableFXMLLoader.class.getName()).log(Level.SEVERE, null, ex);
        }
        return fxmlLoader;
    }
}
