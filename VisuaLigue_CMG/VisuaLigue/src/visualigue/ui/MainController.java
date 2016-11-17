/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visualigue.ui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.layout.StackPane;

/**
 * FXML Controller class
 *
 * @author Guillaume
 */
public class MainController implements Initializable {
 
    @FXML private VBox menuPane;
    @FXML private BorderPane viewFlow;
    @FXML private HBox playsMenuItem;
    @FXML private HBox sportsMenuItem;
    @FXML private HBox obstaclesMenuItem;
    @FXML private HBox settingsMenuItem;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    } 
    
    @FXML
    protected void onSportsMenuItemClicked(MouseEvent e) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(viewFlow.getClass().getResource("visualigue/ui/sport-management.fxml"));
        viewFlow = fxmlLoader.load();
    }
    
}
