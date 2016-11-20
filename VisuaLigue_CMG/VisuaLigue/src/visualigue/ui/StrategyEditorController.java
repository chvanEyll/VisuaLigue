/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visualigue.ui;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ComboBox;
import javafx.scene.input.MouseEvent;
import visualigue.domain.VisuaLigue;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javax.imageio.ImageIO;

/**
 * FXML Controller class
 *
 * @author grasshop
 */

public class StrategyEditorController extends ViewFlowController  {

    @FXML private Label jeuName;
    @FXML private ComboBox frameSelector;
    @FXML private ImageView field;
    private VisuaLigue visualigue = VisuaLigue.getInstance();
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    @Override
     public void initScreen(Object[] name_of_thing) {
        visualigue.createJeux((String)name_of_thing[0], (String)name_of_thing[1]);
        String sportFieldImage = visualigue.getPlayingSurfaceImagePath(visualigue.getSportNameFromJeu((String)name_of_thing[0]));
        File file = new File(sportFieldImage);
        try {
                BufferedImage bufferedImage = ImageIO.read(file);
                Image image = SwingFXUtils.toFXImage(bufferedImage, null);
                field.setImage(image);
            } catch (IOException ex) {
                Logger.getLogger(SportInformationController.class.getName()).log(Level.SEVERE, null, ex);
            }        
        
        jeuName.setText((String)name_of_thing[0]);
    }
     
    private int frameEnCours() {
        return 1;
    }
     
     @FXML
    protected void addJoueur(MouseEvent e) throws IOException {
        //ajouter un joueur a la frame en cours
        int frameEnCours = frameEnCours();
        //jeu.get(frameEnCours);
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
