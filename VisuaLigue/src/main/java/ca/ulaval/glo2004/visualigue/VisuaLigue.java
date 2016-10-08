package ca.ulaval.glo2004.visualigue;

import ca.ulaval.glo2004.visualigue.contexts.DefaultContext;
import ca.ulaval.glo2004.visualigue.ui.InjectableFXMLLoader;
import ca.ulaval.glo2004.visualigue.ui.controllers.MainSceneController;
import ca.ulaval.glo2004.visualigue.utils.EnvironmentUtils;
import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class VisuaLigue extends Application {

    private static final int MIN_STAGE_WIDTH = 1000;
    private static final int MIN_STAGE_HEIGHT = 600;
    private static final String APP_NAME = "VisuaLigue";

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = InjectableFXMLLoader.load(MainSceneController.VIEW_NAME);
        MainSceneController mainSceneController = fxmlLoader.getController();
        mainSceneController.setStage(stage);
        Scene scene = new Scene(fxmlLoader.getRoot());
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
        DefaultContext defaultContext = GuiceInjector.getInstance().getInstance(DefaultContext.class);
        defaultContext.apply();
    }

    public static String getAppDataDirectory() {
        return String.format("%s/%s", EnvironmentUtils.getAppDataDirectory(), APP_NAME);
    }

}
