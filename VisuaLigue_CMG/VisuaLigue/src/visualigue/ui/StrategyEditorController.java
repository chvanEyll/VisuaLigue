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
import visualigue.domain.VisuaLigue;

/**
 * FXML Controller class
 *
 * @author grasshop
 */
public class StrategyEditorController extends ViewFlowController  {


    @FXML private Label jeuName;
    @FXML private ComboBox frameSelector;
    private VisuaLigue visualigue = VisuaLigue.getInstance();
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    @Override
     public void initScreen(Object[] sportName) {
        visualigue.createJeux((String)sportName[0], (String)sportName[1]);
        frameSelector.getItems().addAll((String)sportName[0], (String)sportName[1]);
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
