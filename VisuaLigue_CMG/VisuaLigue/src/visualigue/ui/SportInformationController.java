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
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import visualigue.domain.Sport;
import javafx.stage.FileChooser;
import java.io.File;
import java.awt.image.BufferedImage;
import javafx.scene.image.Image;
import javafx.embed.swing.SwingFXUtils;
import javax.imageio.ImageIO;
import javafx.scene.image.ImageView;
import java.util.logging.Logger;
import java.util.logging.Level;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.SpinnerValueFactory;
import visualigue.domain.VisuaLigue;

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
    @FXML private ImageView myImageView;
    VisuaLigue visualigue = VisuaLigue.getInstance();
    
   
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Preferences prefs = Preferences.userRoot().node(this.getClass().getName());
        sportNameField.setText("Nouveau Sport");
        SpinnerValueFactory spinnerValueFactory1 = new SpinnerValueFactory.DoubleSpinnerValueFactory(0, 10000);
        SpinnerValueFactory spinnerValueFactory2 = new SpinnerValueFactory.DoubleSpinnerValueFactory(0, 10000);
        
        lengthSpinner.setValueFactory(spinnerValueFactory1);
        widthSpinner.setValueFactory(spinnerValueFactory2);
    }    
    
   public void init(Object sport) {
        sportNameField.setText("Nouveau Sport");
    }
   

    public void initScreen(Object sport){ 
       //sportNameField.setText(sport.getName()); 
    } 
   public void setInfo(Sport sport) {
        sportNameField.setText(sport.getName());
    }   
   
   @FXML
    protected void onImageSelectClicked(MouseEvent e) throws IOException {
    
        FileChooser fileChooser = new FileChooser();
             
            //Set extension filter
            FileChooser.ExtensionFilter extFilterJPG = new FileChooser.ExtensionFilter("Images (*.jpg, *.png, *.bmp, *.jpeg)", "*.JPG", "*.png", "*.PNG", "*.bmp", "*.BMP", "*.JPEG", "*.jpeg");
            //FileChooser.ExtensionFilter extFilterPNG = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.PNG", "*.png");
            fileChooser.getExtensionFilters().addAll(extFilterJPG);
              
            //Show open file dialog
            File file = fileChooser.showOpenDialog(null);
                       
            try {
                BufferedImage bufferedImage = ImageIO.read(file);
                Image image = SwingFXUtils.toFXImage(bufferedImage, null);
                myImageView.setImage(image);
            } catch (IOException ex) {
                Logger.getLogger(SportInformationController.class.getName()).log(Level.SEVERE, null, ex);
            }
 
    }
    
    @FXML
    protected void onAjouterSportClicked(MouseEvent e) throws IOException {
    
        visualigue.createSport(sportNameField.getText(), 
                (Double) lengthSpinner.getValue(), 
                (Double)widthSpinner.getValue(), "meters");
       setSportsView();
        }
}
