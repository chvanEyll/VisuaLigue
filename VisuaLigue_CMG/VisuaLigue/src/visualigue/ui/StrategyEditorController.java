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
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ComboBox;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author grasshop
 */
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
public class StrategyEditorController extends ViewFlowController  {

    @FXML private Label jeuName;
    @FXML private ComboBox frameSelector;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
     public void initScreen(Object[] sportName) {
        jeuName.setText((String)sportName[0]);
    }
     
    private int frameEnCours() {
        return frameSelector.getText();
    }
     
     @FXML
    protected void addJoueur(MouseEvent e) throws IOException {
        //ajouter un joueur a la frame en cours
        int frameEnCours = frameEnCours();
        jeu.get(frameEnCours);
    }
    
    @FXML
    protected void addAdversaire(MouseEvent e) throws IOException {
        
    }
    
    @FXML
    protected void addObstacle(MouseEvent e) throws IOException {
        
    }
    
    private void redraw() {
        //prendre dictionnaires de joueurs avec leurs vecteurs
        //iterer dessus
            //generer un dessin aux coordonnees
        
    }
}
