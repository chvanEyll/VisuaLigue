/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visualigue;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import visualigue.ui.MainController;
import visualigue.ui.ViewFlowController;

/**
 *
 * @author OlivierR
 */
public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("ui/main.fxml"));
        Parent root = (Parent)loader.load();
        MainController controller = (MainController)loader.getController();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        controller.init();
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
