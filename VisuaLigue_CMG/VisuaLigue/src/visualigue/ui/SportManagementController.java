/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visualigue.ui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import visualigue.domain.Sport;
import visualigue.domain.VisuaLigue;

/**
 * FXML Controller class
 *
 * @author Guillaume
 */
public class SportManagementController extends ViewFlowController {

    private VisuaLigue visualigue = VisuaLigue.getInstance();
    
    @FXML private StackPane rootNode;
    @FXML private SportInformationController sportInformationController; 
 
    @FXML
    protected void onNewSportButtonClicked(MouseEvent e) throws IOException {
        
    /*    URL location = getClass().getResource("sport-information.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader(location);

        Node node = fxmlLoader.load();
        rootNode.getChildren().setAll(node);
        
        Sport sport = new Sport("Nouveau Sport");
        sportInformationController.init(sport);
    */
    
        Sport sport = new Sport("Nouveau Sport");
    
        loadScreenWithInfo("sportInformation", "sport-information.fxml", sport);
        //sportInformationController.init(sport);
     
    }
}
