/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visualigue.ui;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import visualigue.domain.Sport;

/**
 * FXML Controller class
 *
 * @author Guillaume
 */
public class SportInformationController extends MainController {
    
    @FXML private TextField sportNameField;
    @FXML private Spinner widthSpinner;
    @FXML private Spinner lengthSpinner;
    @FXML private ComboBox unitComboBox;
    @FXML private Label imageErrorLabel;
   
    /**
     * Initializes the controller class.
     * @param url
     
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //Preferences prefs = Preferences.userRoot().node(this.getClass().getName());
        //sportNameField.setText("Nouveau Sport");
    }    
    
   public void init(Object sport) {
        sportNameField.setText("Nouveau Sport");
    }
   */
    @Override
    public void initScreen(Object sport){ 
       sportNameField.setText(sport.getName()); 
    } 
   public void setInfo(Sport sport) {
        sportNameField.setText(sport.getName());
    }   
}
