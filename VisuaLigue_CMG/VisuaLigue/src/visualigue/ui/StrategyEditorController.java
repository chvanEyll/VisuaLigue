/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visualigue.ui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ComboBox;

/**
 * FXML Controller class
 *
 * @author grasshop
 */
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
public class StrategyEditorController implements Initializable {

    @FXML private Label jeuName;
    @FXML private ComboBox frameSelector;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
     public void initScreen(Object sportName) {
        jeuName.setText((String)sportName);
    }
     
     @FXML
    protected void addJoueur(MouseEvent e) throws IOException {
        
    }
    
    @FXML
    protected void addAdversaire(MouseEvent e) throws IOException {
        
    }
    
    @FXML
    protected void addObstacle(MouseEvent e) throws IOException {
        
    }
    
}
