/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visualigue.ui;

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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

/**
 * FXML Controller class
 *
 * @author CH
 */
public class JeuxManagementController extends ViewFlowController {

    
    @FXML private StackPane rootNode;
    //@FXML private SportInformationController sportInformationController;
    @FXML private GridPane JeuxList;
    private VisuaLigue visualigue = VisuaLigue.getInstance();
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Iterator iterator_jeux = visualigue.getListeJeux();
        
        int flag = 0;
        while(iterator_jeux.hasNext())
        {
            String nom_jeu=iterator_jeux.next().toString();
            Button button = new Button(nom_jeu);
        
            button.setOnMouseClicked(e -> {
            try {
                onJeuClicked(e, nom_jeu);
            } catch (IOException ex) {
                Logger.getLogger(SelectionSportController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
            HBox hbox = new HBox(button);
            JeuxList.add(hbox, 0, flag++);
        }
    }   
 
    @FXML
    protected void onJeuClicked(MouseEvent e, String nom_jeu) throws IOException {
        if(visualigue.hasJeuASport(nom_jeu))
        {
            String[] to_send = {nom_jeu, visualigue.getSportNameFromJeu(nom_jeu)};
            if (loadScreenWithInfo("strategyEditor", "strategyEditor.fxml", to_send))
            {
                setScreen("strategyEditor");
            }   
        }
        else
        {
            String[] to_send = {nom_jeu};
            if (loadScreenWithInfo("selectionPourJeu", "selection_sport_pour_jeu.fxml"
                , to_send))
            {
                setScreen("selectionPourJeu");
            }
        }
    }
    
    @FXML
    protected void onNewJeuButtonClicked(MouseEvent e) throws IOException {
        //TODO THJISD
        String[] to_send = {visualigue.getDefaultJeuxName()};
        visualigue.createJeux(visualigue.getDefaultJeuxName());
        if (loadScreenWithInfo("selectionPourJeu", "selection_sport_pour_jeu.fxml"
                , to_send))
        {
            setScreen("selectionPourJeu");
        }    
    }
}
