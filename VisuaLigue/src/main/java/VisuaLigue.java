/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.java;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 *
 * @author OlivierR
 */
public class VisuaLigue extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/resources/view/main.fxml"));
        
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.setTitle("VisuaLigue");
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/resources/images/app-icon/icon_16x16.png")));
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/resources/images/app-icon/icon_32x32.png")));
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/resources/images/app-icon/icon_48x48.png")));
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/resources/images/app-icon/icon_256x256.png")));
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
