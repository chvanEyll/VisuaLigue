/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visualigue.ui;

import ca.ulaval.glo2004.visualigue.utils.EventHandler;
import java.io.IOException;
import java.net.URL;
import java.util.Iterator;
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
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.control.Button;

/**
 * FXML Controller class
 *
 * @author Guillaume
 */
public class SelectionSportController extends ViewFlowController {

    
    @FXML private StackPane rootNode;
    //@FXML private SportInformationController sportInformationController;
    @FXML private GridPane SportsList;
    private VisuaLigue visualigue = VisuaLigue.getInstance();
    
    ScreensController myController;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Iterator iterator = visualigue.getListeSports();
        
        int flag = 0;
        while(iterator.hasNext()) {
            String nom_sport=iterator.next().toString();
            Button button = new Button(nom_sport);
            MouseEvent MouseEvent = null;
            
            //
            button.setOnMouseClicked(onNewSportButtonForJeuxClicked(MouseEvent));/*new EventHandler<ActionEvent>() {
                
                @Override public void handle(ActionEvent e) {

                    if (loadScreenWithInfo("strategyEditor", "strategyEditor.fxml", nom_sport))
                    {
                        setScreen("strategyEditor");
                    }   
                }
            });*/
            
            HBox hbox = new HBox(button);
            SportsList.add(hbox, 0, flag++);
        }
    }   
 
 
    @FXML
    protected void onNewSportButtonForJeuxClicked(MouseEvent e) throws IOException {
        
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
