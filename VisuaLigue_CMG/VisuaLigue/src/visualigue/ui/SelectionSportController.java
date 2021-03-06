/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visualigue.ui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.control.Button;
import static javafx.scene.input.MouseEvent.MOUSE_CLICKED;

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
    private String myName = "selectionPourJeu";
    private String myRessourceName = "selection_sport_pour_jeu.fxml";
    private String to_move_foward;
        
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Iterator iterator = visualigue.getListeSports();
        
        int flag = 0;
        while(iterator.hasNext()) {
            String nom_sport=iterator.next().toString();
            Button button = new Button(nom_sport);
            MouseEvent mouseEvent = null;

            button.setOnMouseClicked(e -> {
                try {
                    onSportClicked(e, nom_sport);
                } catch (IOException ex) {
                    Logger.getLogger(SelectionSportController.class.getName()).log(Level.SEVERE, null, ex);
                }
            });

            HBox hbox = new HBox(button);
            SportsList.add(hbox, 0, flag++);
        }
    }   
 
    @Override
   public void initScreen(Object[] object) {
        to_move_foward = (String) object[0];
    }
    
    @FXML
    protected void onSportClicked(MouseEvent t, String nom_sport) throws IOException {
       
        String[] to_send = {to_move_foward, nom_sport};
        if (loadScreenWithInfo("strategyEditor", "strategyEditor.fxml", to_send))
        {
            setScreen("strategyEditor");
        }   
    }
    
    @FXML
    protected void onNewSportButtonForJeuxClicked(MouseEvent e) throws IOException {
        String[] newName = {myName, myRessourceName, to_move_foward};
        if (loadScreenWithInfo("sportInformation", "sport-information.fxml", newName))
        {
            setScreen("sportInformation");
        }     
    }
}
