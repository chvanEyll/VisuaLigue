/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visualigue.ui;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Guillaume
 */
public class MainController implements Initializable {
 
    @FXML private VBox menuPane;
    @FXML private StackPane viewFlow;
    @FXML private HBox playsMenuItem;
    @FXML private HBox sportsMenuItem;
    @FXML private HBox obstaclesMenuItem;
    @FXML private HBox settingsMenuItem;
    
    @FXML private ViewFlowController viewFlowController;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    } 
    
    public void init() {
        viewFlowController.init();
    } 
    
    public void initScreen(Object[] object){};
    
    @FXML
    protected void onJeuxMenuItemClicked(MouseEvent e) throws IOException {
        
        URL location = getClass().getResource("jeux-management.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader(location);

        Node node = fxmlLoader.load();
        viewFlow.getChildren().setAll(node);
    }
    
    @FXML
    protected void onSportsMenuItemClicked(MouseEvent e) throws IOException {
        /*
        URL location = getClass().getResource("sport-management.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader(location);

        Node node = fxmlLoader.load();
        viewFlow.getChildren().setAll(node);*/
        setSportsView();
    }
    
    @FXML
    public void setSportsView () throws IOException{
        
        URL location = getClass().getResource("sport-management.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader(location);

        Node node = fxmlLoader.load();
        viewFlow.getChildren().setAll(node);
    }          
}
