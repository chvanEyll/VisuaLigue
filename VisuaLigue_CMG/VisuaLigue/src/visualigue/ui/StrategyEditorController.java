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
import javafx.collections.ObservableList;
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
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
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
    public void initialize(URL url, ResourceBundle rb) {}    
    
    @Override
     public void initScreen(Object[] name_of_thing) {
         //creer le jeu et mettre l image
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
        
        // creer la frame 1
        Jeu monJeu = visualigue.getJeu(jeuName.getText());
        monJeu.newFrame(1);
        frameSelector.getItems().addAll("1");
        frameSelector.setValue("1");
        
    }
     
    private int frameEnCours() {
        String frame_str = (String) frameSelector.getValue();
        
        return Integer.parseInt(frame_str) - 1; // l index de l interface commence a 1
    }
    
    private int nbOfFrames() {
        List<String> availableFrames = frameSelector.getItems();
        int nbOfFrames = availableFrames.size();
        return nbOfFrames;
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
    protected void goToFirstFrame(MouseEvent e) throws IOException {
        frameSelector.setValue("1");
    }
    
    @FXML
    protected void goToPreviousFrame(MouseEvent e) throws IOException {
        
        String previous_frame = Integer.toString(frameEnCours()); // voir ajustement au retour de frameEnCours()
        
        if (frameSelector.getItems().contains(previous_frame)){
            
            frameSelector.setValue(previous_frame);
            
        };
         
    }
    
    @FXML
    protected void goToNextFrame(MouseEvent e) throws IOException {
        
        String next_frame = Integer.toString(frameEnCours()+2); // voir ajustement au retour de frameEnCours()
        
        if (frameSelector.getItems().contains(next_frame)){
            
            frameSelector.setValue(next_frame);
            
        };
    }
    
    @FXML
    protected void goToLastFrame(MouseEvent e) throws IOException {
        int nbOfFrames = nbOfFrames();
        frameSelector.setValue(Integer.toString(nbOfFrames));
    }
    
    @FXML
    protected void addFrame(MouseEvent e) throws IOException {
        
        // get current frame
        List<String> availableFrames = frameSelector.getItems();
        int nbOfFrames = nbOfFrames();
        String current_frame_str = availableFrames.get(nbOfFrames-1);
        int current_frame_int = Integer.parseInt(current_frame_str);
        
        // create new frame in domain
        int new_frame = current_frame_int + 1;
        String new_frame_str = Integer.toString(new_frame);
        Jeu monJeu = visualigue.getJeu(jeuName.getText());
        monJeu.newFrame(new_frame);
        
        // copy scene to new frame
        PlayFrame oldFrame = monJeu.getFrame(frameEnCours());
        PlayFrame newFrame = monJeu.getFrame(new_frame-1);
        newFrame.insertFramePositions(oldFrame);
        
        // show new frame
        frameSelector.getItems().addAll(new_frame_str);
        frameSelector.setValue(new_frame_str);
        
        //redraw is automatic because of combobox onvaluechanged property
    }
    
    private void drawObjects(String ObjectType, List<Vector2d> positions) {
        
        String path="";
        switch(ObjectType) {
            case "Joueurs":
                path = System.getProperty("user.dir")+"/src/image/joueur.png";
                break;
            case "Adversaires":
                path = System.getProperty("user.dir")+"/src/image/adversaire.png";
                break;
            case "Obstacles":
                path = System.getProperty("user.dir")+"/src/image/cone-icon.png";
                break;
        }
        
        for (int obj_idx=0;obj_idx<positions.size();obj_idx++) {
            
            ImageView view = new ImageView();
            
            Vector2d pos = positions.get(obj_idx);
            int idx = obj_idx;
        
            File file = new File(path);
                try {
                    BufferedImage bufferedImage = ImageIO.read(file);
                    Image image = SwingFXUtils.toFXImage(bufferedImage, null);
                    view.setImage(image);
                    
                    view.setOnDragDetected(e -> {
                        try {
                            dragObj(e, view);
                        } catch (IOException ex) {
                            Logger.getLogger(SelectionSportController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    });
                    
                    view.setOnMouseReleased(e -> {
                        try {
                            dropObj(e, view, ObjectType, idx);
                        } catch (IOException ex) {
                            Logger.getLogger(SelectionSportController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    });
                    
                } catch (IOException ex) {
                    Logger.getLogger(SportInformationController.class.getName()).log(Level.SEVERE, null, ex);
                }
                
            view.setId(Integer.toString(idx));
            board.getChildren().add(view);
            view.setFitHeight(40);
            view.setFitWidth(40);
            view.setTranslateX(pos.x);
            view.setTranslateY(pos.y);
        }
            
    }
    
    @FXML
    protected void dragObj(MouseEvent e, ImageView obj) throws IOException {
        // cette fonction ne fait rien pour l instant.
        // elle pourrait servir a visuellement dragger l objet
        
    }
    
    private Vector2d getFieldCenter() {
    
        Bounds myBounds = board.localToScene(board.getBoundsInLocal());
        
        float width = (float) myBounds.getWidth();
        float height = (float) myBounds.getHeight();
        float x = (float) myBounds.getMinX() + (width/2);
        float y = (float) myBounds.getMinY() + (height/2);
                
        return new Vector2d(x,y);
        
    }
    
    @FXML
    private boolean isObjectAtPos(Vector2d mousePos, PlayFrame frame) {
    
        List<Vector2d> JoueursPos = frame.getJoueursPos();
        List<Vector2d> AdversairesPos = frame.getAdversairesPos();
        List<Vector2d> ObstaclesPos = frame.getObstaclesPos();
        
        for (int i=0; i<JoueursPos.size();i++) {
            
            if (JoueursPos.get(i).tooCloseTo(mousePos)) {
                
                return true;
                
            }
            
        }
        
        for (int i=0; i<AdversairesPos.size();i++) {
            
            if (AdversairesPos.get(i).tooCloseTo(mousePos)) {
                
                return true;
                
            }
            
        }
        
        for (int i=0; i<ObstaclesPos.size();i++) {
            
            if (ObstaclesPos.get(i).tooCloseTo(mousePos)) {
                
                return true;
                
            }
            
        }
        
        return false;
        
    }
    
    @FXML
    protected void dropObj(MouseEvent e, ImageView obj, String ObjectType, int Idx) throws IOException {
        
        Vector2d mousePos = new Vector2d((float) e.getSceneX(),(float) e.getSceneY());
        
        // difference entre la souris et la position du centre car on applique une translation
        mousePos.x -= getFieldCenter().x;
        mousePos.y -= getFieldCenter().y;
        
        Jeu monJeu = visualigue.getJeu(jeuName.getText());
        PlayFrame frame = monJeu.getFrame(frameEnCours());
        
        // verification de la disponibilite de l emplacement
        if (isObjectAtPos(mousePos,frame)) {
            
            Label avertissement = new Label("Un objet existe à cet emplacement!");
            board.getChildren().add(avertissement); // disparaitra au prochain redraw grace au clear()
            
        } else {
        
            switch(ObjectType) {
                case "Joueurs":
                    frame.setJoueurPos(Idx,mousePos);
                    break;
                case "Adversaires":
                    frame.setAdversairePos(Idx,mousePos);
                    break;
                case "Obstacles":
                    frame.setObstaclePos(Idx,mousePos);
                    break;
            }

            redraw();
        
        }
        
        
    }
    
    private void redraw() {
        
        board.getChildren().clear();
        
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