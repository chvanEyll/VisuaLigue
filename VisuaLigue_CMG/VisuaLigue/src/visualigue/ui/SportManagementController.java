/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visualigue.ui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import visualigue.domain.Sport;
import visualigue.domain.VisuaLigue;
import javafx.scene.layout.GridPane;
import java.util.List;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

/**
 * FXML Controller class
 *
 * @author Guillaume
 */
public class SportManagementController extends ViewFlowController {

    
    @FXML private StackPane rootNode;
    @FXML private SportInformationController sportInformationController;
    @FXML private GridPane SportsList;
    private VisuaLigue visualigue = VisuaLigue.getInstance();
    private String myName = "sportManagement";
    
    ScreensController myController;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        List<Sport> sports = visualigue.getListeSports();
        
        for (int i=0;i<sports.size();i++) {
            Label label = new Label(sports.get(i).getName());
            HBox hbox = new HBox(label);
            //SportsList.getChildren().add(label);
            SportsList.add(hbox, 0, i);
        }
    }   
 
 
    @FXML
    protected void onNewSportButtonClicked(MouseEvent e) throws IOException {
        
    /*    URL location = getClass().getResource("sport-information.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader(location);

        Node node = fxmlLoader.load();
        rootNode.getChildren().setAll(node);
        
        Sport sport = new Sport("Nouveau Sport");
        sportInformationController.init(sport);
    */
    
        String newName = visualigue.getDefaultSportName();
        if (loadScreenWithInfo("sportInformation", "sport-information.fxml", newName))
        {
            setScreen("sportInformation");
        }     
    }
}
