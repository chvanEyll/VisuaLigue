package ca.ulaval.glo2004.visualigue;

import ca.ulaval.glo2004.visualigue.contexts.ContextBase;
import ca.ulaval.glo2004.visualigue.contexts.DefaultContext;
import ca.ulaval.glo2004.visualigue.ui.InjectableFXMLLoader;
import ca.ulaval.glo2004.visualigue.ui.View;
import ca.ulaval.glo2004.visualigue.ui.controllers.MainSceneController;
import java.io.IOException;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class VisuaLigue extends Application {

    private static final int MIN_STAGE_WIDTH = 500;
    private static final int MIN_STAGE_HEIGHT = 500;
    private static final String APP_NAME = "VisuaLigue";
    private static final String DATA_DIRECTORY = "data";
    private static final String REPOSITORY_DIRECTORTY = "/repository";
    private static final String MAIN_STYLE_SHEET = "css/main.fxml.css";
    private static ContextBase defaultContext;
    private static Stage stage;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        this.stage = stage;
        View view = InjectableFXMLLoader.loadView(MainSceneController.VIEW_NAME);
        MainSceneController mainSceneController = (MainSceneController) view.getController();
        Scene scene = new Scene((Parent) view.getRoot());
        initStage(stage, scene);
        initContext();
    }

    private void initStage(Stage stage, Scene scene) throws IOException {
        setStageIcons(stage);
        stage.setScene(scene);
        stage.setTitle(APP_NAME);
        stage.setMinWidth(MIN_STAGE_WIDTH);
        stage.setMinHeight(MIN_STAGE_HEIGHT);
        stage.show();
    }

    private void setStageIcons(Stage stage) {
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/images/app-icon/icon-16x16.png")));
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/images/app-icon/icon-32x32.png")));
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/images/app-icon/icon-48x48.png")));
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/images/app-icon/icon-256x256.png")));
    }

    private void initContext() throws Exception {
        defaultContext = GuiceInjector.getInstance().getInstance(DefaultContext.class);
        defaultContext.apply(false);
    }

    public static String getAppDataDirectory() {
        return DATA_DIRECTORY;
    }

    public static String getRepositoryDirectory() {
        return getAppDataDirectory() + REPOSITORY_DIRECTORTY;
    }

    public static Stage getMainStage() {
        return stage;
    }

    public static String getAppName() {
        return APP_NAME;
    }

    public static String getMainStyleSheet() {
        return MAIN_STYLE_SHEET;
    }

    public static ContextBase getDefaultContext() {
        return defaultContext;
    }
}
