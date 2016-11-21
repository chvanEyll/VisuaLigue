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
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ComboBox;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.InputEvent;
import visualigue.domain.VisuaLigue;
import visualigue.domain.Jeu;
import visualigue.domain.PlayFrame;
import visualigue.utils.Vector2d;
import javafx.scene.layout.StackPane;
import javafx.scene.control.Button;

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
    @FXML private StackPane board;
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
        String frame_str = (String) frameSelector.getValue();
        
        return Integer.parseInt(frame_str);
    }
     
    @FXML
    protected void addJoueur(MouseEvent e) throws IOException {
        
        Jeu monJeu = visualigue.getJeu(jeuName.getText());
        PlayFrame frame = monJeu.getFrame(frameEnCours());
        Vector2d joueurPos = new Vector2d(0,0);
        
        frame.addJoueur(joueurPos);
        redraw();
        
    }
    
    @FXML
    protected void addAdversaire(MouseEvent e) throws IOException {
        
        Jeu monJeu = visualigue.getJeu(jeuName.getText());
        PlayFrame frame = monJeu.getFrame(frameEnCours());
        Vector2d advPos = new Vector2d(0,0);
        
        frame.addAdversaire(advPos);
        redraw();
        
    }
    
    @FXML
    protected void addObstacle(MouseEvent e) throws IOException {
        
        Jeu monJeu = visualigue.getJeu(jeuName.getText());
        PlayFrame frame = monJeu.getFrame(frameEnCours());
        Vector2d obstaclePos = new Vector2d(0,0);
        
        frame.addObstacle(obstaclePos);
        redraw();
        
    }
    
    @FXML
    protected void frameSelected(Event e) throws IOException {
        redraw();
    }
    
    @FXML
    protected void addFrame(MouseEvent e) throws IOException {
        
        List<String> availableFrames = frameSelector.getItems();
        int nbOfFrames = availableFrames.size();
        int new_frame = 0;
        if (nbOfFrames != 0) {

            String current_frame_str = availableFrames.get(nbOfFrames-1);
            int current_frame_int = Integer.parseInt(current_frame_str);
            new_frame = current_frame_int + 1;
            
        } 
        
        String new_frame_str = Integer.toString(new_frame);
        Jeu monJeu = visualigue.getJeu(jeuName.getText());
        monJeu.newFrame(new_frame);
        frameSelector.getItems().addAll(new_frame_str);
        frameSelector.setValue(new_frame_str);
        
        //redraw is automatic because of combobox onvaluechanged property
    }
    
    private void drawObjects(String Objecttype, List<Vector2d> positions) {
        
        ImageView view = new ImageView();
        
        String path="";
        switch(Objecttype) {
            case "Joueurs":
                path = System.getProperty("user.dir")+"/src/image/joueur.png";
            case "Adversaires":
                path = System.getProperty("user.dir")+"/src/image/adversaire.png";
            case "Obstacles":
                path = System.getProperty("user.dir")+"/src/image/cone-icon.png";
        }
        
        for (int i=0;i<positions.size();i++) {
            
            Vector2d pos = positions.get(i);
        
            File file = new File(path);
                try {
                    BufferedImage bufferedImage = ImageIO.read(file);
                    Image image = SwingFXUtils.toFXImage(bufferedImage, null);
                    view.setImage(image);
                } catch (IOException ex) {
                    Logger.getLogger(SportInformationController.class.getName()).log(Level.SEVERE, null, ex);
                }    

            board.getChildren().add(view);
            view.setFitHeight(40);
            view.setFitWidth(40);
            view.setX(pos.x);
            view.setY(pos.y);
        }
            
    }
    
    private void redraw() {
        
        Jeu monJeu = visualigue.getJeu(jeuName.getText());
        PlayFrame frame = monJeu.getFrame(frameEnCours());
        
        List<Vector2d> JoueursPos = frame.getJoueursPos();
        List<Vector2d> AdversairesPos = frame.getAdversairesPos();
        List<Vector2d> ObstaclesPos = frame.getObstaclesPos();
        
        drawObjects("Joueurs",JoueursPos);
        drawObjects("Adversaires",AdversairesPos);
        drawObjects("Obstacles",ObstaclesPos);
            
        
    }
}
