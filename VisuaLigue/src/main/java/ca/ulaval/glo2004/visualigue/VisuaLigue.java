package ca.ulaval.glo2004.visualigue;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class VisuaLigue extends Application {
    
    private static final int MIN_STAGE_WIDTH = 1000;
    private static final int MIN_STAGE_HEIGHT = 600;
    private static final String APP_NAME = "VisuaLigue";
    
    @Override
    public void start(Stage stage) throws Exception {
        initStage(stage);
    }
    
    private void initStage(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/views/main.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        setStageIcons(stage);
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
    
    public static void main(String[] args) {
        launch(args);
    }
    
}
