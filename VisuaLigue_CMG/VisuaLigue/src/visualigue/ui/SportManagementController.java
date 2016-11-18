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
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;

/**
 * FXML Controller class
 *
 * @author Guillaume
 */
public class SportManagementController extends MainController {

    @FXML private StackPane rootNode;
    @FXML private SportInformationController sportInformationController;
    
    ScreensController myController;
     
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
     public void setScreenParent(ScreensController screenParent){ 
        myController = screenParent; 
     }
 
    @FXML
    protected void onNewSportButtonClicked(MouseEvent e) throws IOException {
        
        URL location = getClass().getResource("sport-information.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader(location);

        Node node = fxmlLoader.load();
        rootNode.getChildren().setAll(node);
    }
}
