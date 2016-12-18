/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visualigue.ui;

import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
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
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.paint.Color;
import javafx.util.Pair;
import javax.imageio.ImageIO;
import visualigue.domain.Command;

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
    @FXML private Button pauseButton;
    private VisuaLigue visualigue = VisuaLigue.getInstance();
    Timer timer = new Timer();
    int regular_play_speed = 1000;
    int fast_play_speed = 500;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {}    
    
    @Override
     public void initScreen(Object[] name_of_thing) {
         //creer le jeu et mettre l image
        visualigue.createJeux((String)name_of_thing[0], (String)name_of_thing[1]);
        //String sportFieldImage = visualigue.getPlayingSurfaceImagePath(visualigue.getSportNameFromJeu((String)name_of_thing[0]));
        //File file = new File(sportFieldImage);
        try {
                Image image = new Image(getClass().getResourceAsStream("/resources/soccer-field.jpg"));
                //BufferedImage bufferedImage = ImageIO.read(file);
                //Image image = SwingFXUtils.toFXImage(bufferedImage, null);
                field.setImage(image);
            } catch (Exception ex) {
                Logger.getLogger(SportInformationController.class.getName()).log(Level.SEVERE, null, ex);
            }        
        
        jeuName.setText((String)name_of_thing[0]);
        
        // creer la frame 1
        Jeu monJeu = visualigue.getJeu(jeuName.getText());
        monJeu.newFrame(1);
        frameSelector.getItems().addAll("1");
        frameSelector.setValue("1");
        
    }
     
    @FXML
    protected void saveImage(MouseEvent e) {
    
        WritableImage strategy = getImageWithMovements(field.getImage());
        
        File outputFile = new File("maStrategie");
        RenderedImage renderedImage = SwingFXUtils.fromFXImage(strategy, null);
        
        try {
      
            ImageIO.write(renderedImage, "png", outputFile);
    
        } catch (IOException ex) {
      
            throw new RuntimeException(ex);
            
        }
        
    }
    
    @FXML protected void undo(MouseEvent e) {
        
        Jeu monJeu = visualigue.getJeu(jeuName.getText());
        Command maCommande = monJeu.getLastCommand();
        if (maCommande == null) { return;}
        PlayFrame frame = monJeu.getFrame(maCommande.frame);

        if (maCommande.commandType == "Add") {

            frame.deleteAtIndex(maCommande.objectType,maCommande.index);

        } else if (maCommande.commandType == "Move") {

            switch(maCommande.objectType) {
                case "Joueurs":
                    frame.setJoueurPos(maCommande.index, maCommande.oldPos);
                    break;
                case "Adversaires":
                    frame.setAdversairePos(maCommande.index, maCommande.oldPos);
                    break;
                case "Obstacles":
                    frame.setObstaclePos(maCommande.index, maCommande.oldPos);
                    break;
            }
            
        }
        
        redraw();

    }
    
    @FXML protected void redo(MouseEvent e) {
        
        Jeu monJeu = visualigue.getJeu(jeuName.getText());
        Command maCommande = monJeu.getLastRedoCommand();
        if (maCommande == null) { return;}
        PlayFrame frame = monJeu.getFrame(maCommande.frame);

        if (maCommande.commandType == "Add") {
            
            Vector2d pos_depart = new Vector2d(0,0);
            
            switch(maCommande.objectType) {
                    
                case "Joueurs":
                    frame.addJoueur(pos_depart);
                    break;
                case "Adversaires":
                    frame.addAdversaire(pos_depart);
                    break;
                case "Obstacles":
                    frame.addObstacle(pos_depart);
                    break;
            }

        } else if (maCommande.commandType == "Move") {

            switch(maCommande.objectType) {
                case "Joueurs":
                    frame.setJoueurPos(maCommande.index, maCommande.newPos);
                    break;
                case "Adversaires":
                    frame.setAdversairePos(maCommande.index, maCommande.newPos);
                    break;
                case "Obstacles":
                    frame.setObstaclePos(maCommande.index, maCommande.newPos);
                    break;
            }

        }

        redraw();
        
    }
    
    @FXML
    protected WritableImage getImageWithMovements(Image image) {

        WritableImage wImage = 
            new WritableImage( (int)image.getWidth(), (int)image.getHeight());
        
        PixelReader pixelReader = image.getPixelReader();
        PixelWriter pixelWriter = wImage.getPixelWriter();
        Color color;
        
        // copy initial image to wImage
        for(int readY=0;readY<image.getHeight();readY++){
            
            for(int readX=0; readX<image.getWidth();readX++){
                
                color = pixelReader.getColor(readX,readY);
                pixelWriter.setColor(readX,readY,color);
                
            }
        }
            
        // ADD JOUEURS, ADVERSAIRES ET OBSTACLES
        Jeu monJeu = visualigue.getJeu(jeuName.getText());
        PlayFrame frame;
        List<Vector2d> JoueursPos = new ArrayList();
        List<Vector2d> AdversairesPos = new ArrayList();
        List<Vector2d> ObstaclesPos = new ArrayList();
        
        for (int i = 0; i<nbOfFrames();i++) {
        
            frame = monJeu.getFrame(i);

            JoueursPos.addAll(frame.getJoueursPos());
            AdversairesPos.addAll(frame.getAdversairesPos());
            ObstaclesPos.addAll(frame.getObstaclesPos());
            
        }    
        
        // add joueurs
        color = Color.GREEN;
        
        for (int i = 0; i < JoueursPos.size(); i++) {
            
            Vector2d pos = JoueursPos.get(i);
            drawPixels(pixelWriter,(int)pos.x,(int)pos.y,color);
            
            }
        
        // add adversaires
        color = Color.RED;
        
        for (int i = 0; i < AdversairesPos.size(); i++) {
            
            Vector2d pos = AdversairesPos.get(i);
            drawPixels(pixelWriter,(int)pos.x,(int)pos.y,color);
            
            }
        
        // add obstacles
        color = Color.ORANGE;
        
        for (int i = 0; i < ObstaclesPos.size(); i++) {
            
            Vector2d pos = ObstaclesPos.get(i);
            drawPixels(pixelWriter,(int)pos.x,(int)pos.y,color);
            
            }
        
        return wImage;
    }
    
    protected void drawPixels(PixelWriter pixelWriter, int x, int y, Color color) {
        
        Vector2d imageCenter = getImageCenter();
        Vector2d uiFieldCenter = getFieldCenter();
        float x_ratio = imageCenter.x / uiFieldCenter.x;
        float y_ratio = imageCenter.y / uiFieldCenter.y;
        
        int corrected_x = (int) ((float) x*x_ratio) + (int) imageCenter.x;
        int corrected_y = (int) ((float) y*y_ratio) + (int) imageCenter.y;
        
        int diameter = (int)(field.getImage().getWidth() * 0.02);
        
        // dessine un carre autour du pixel
        for (int i=-diameter;i<diameter;i++) {
            
            for (int j=-diameter;j<diameter;j++) {
                
                pixelWriter.setColor(corrected_x+i,corrected_y+j,color);
                
            }
            
        }
        
    }
    
    protected Vector2d getImageCenter() {
        
        Image fieldImage = field.getImage();
        
        float x = (float) fieldImage.getWidth() / 2;
        float y = (float) fieldImage.getHeight() / 2;
                
        return new Vector2d(x,y);
        
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
        
        monJeu.addAjoutToHistorique("Joueurs",frameEnCours(),frame.addJoueur(joueurPos));
        redraw();
        
    }
    
    @FXML
    protected void addAdversaire(MouseEvent e) throws IOException {
        
        Jeu monJeu = visualigue.getJeu(jeuName.getText());
        PlayFrame frame = monJeu.getFrame(frameEnCours());
        Vector2d advPos = new Vector2d(0,0);
        
        monJeu.addAjoutToHistorique("Adversaires",frameEnCours(),frame.addAdversaire(advPos));
        redraw();
        
    }
    
    @FXML
    protected void addObstacle(MouseEvent e) throws IOException {
        
        Jeu monJeu = visualigue.getJeu(jeuName.getText());
        PlayFrame frame = monJeu.getFrame(frameEnCours());
        Vector2d obstaclePos = new Vector2d(0,0);
        
        monJeu.addAjoutToHistorique("Obstacles",frameEnCours(),frame.addObstacle(obstaclePos));
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
    
    protected void startSequence(int delay, boolean backwards) {
        
        timer.cancel();
        timer = new Timer();
        
        if (backwards) {
            
            timer.schedule(new playSeqBackwards(), delay, delay);
            
        } else {
            
            timer.schedule(new playSeq(), delay, delay);
            
        }
        
    }
    
    @FXML
    protected void playSequence(MouseEvent e) throws IOException {
        
        if (nbOfFrames() == 1) {
        
            return;
            
        }
        
        frameSelector.setValue("1");
        startSequence(regular_play_speed, false);
        
    }
    
    class playSeq extends TimerTask {
        
        public void run() {
            
            Platform.runLater(new Runnable() {
       
                public void run() {
                    
                    if (nbOfFrames() <= frameEnCours()+1) {

                        cancel();
                        return;

                    }
                    
                    String next_frame = Integer.toString(frameEnCours()+2);
                    frameSelector.setValue(next_frame);

                }});
        }
    }
    
    class playSeqBackwards extends TimerTask {
        
        public void run() {
            
            Platform.runLater(new Runnable() {
       
                public void run() {
                    
                    if (frameEnCours() == 0) {

                        cancel();
                        return;

                    }
                    
                    String next_frame = Integer.toString(frameEnCours());
                    frameSelector.setValue(next_frame);

                }});
        }
    }
    
    @FXML
    protected void pauseSequence(MouseEvent e) throws IOException, InterruptedException {
        
        String grayedStyle = "-fx-background-color: white";
        boolean isPaused = pauseButton.getStyle().contains(grayedStyle);
        
        if (isPaused) {
        
            pauseButton.setStyle(null);
            startSequence(regular_play_speed, false);
            
        } else {
            
            pauseButton.setStyle(grayedStyle);
            timer.cancel();
            
        }
        
    }
    
    @FXML
    protected void stopSequence(MouseEvent e) throws IOException {
        
        timer.cancel();
        frameSelector.setValue("1");
        
    }
    
    @FXML
    protected void fastRewind(MouseEvent e) throws IOException {
    
        goToLastFrame(e);
        startSequence(fast_play_speed, true);
        
    }
    
    @FXML
    protected void fastForward(MouseEvent e) throws IOException {
    
        startSequence(fast_play_speed, false);
        
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
    
    private Image getImage(String ObjectType) {
        
        Image image = null;
        try {
       
            //String path="";
            switch(ObjectType) {
                case "Joueurs":
                    //path = System.getProperty("user.dir")+"/src/image/joueur.png";
                    image = new Image(getClass().getResourceAsStream("/resources/joueur.png"));
                    break;
                case "Adversaires":
                    //path = System.getProperty("user.dir")+"/src/image/adversaire.png";
                    image = new Image(getClass().getResourceAsStream("/resources/adversaire.png"));
                    break;
                case "Obstacles":
                    //path = System.getProperty("user.dir")+"/src/image/cone-icon.png";
                    image = new Image(getClass().getResourceAsStream("/resources/cone-icon.png"));
                    break;
            }
        }
        
        catch (Exception ex) {
                    Logger.getLogger(SportInformationController.class.getName()).log(Level.SEVERE, null, ex);
                }
        
        return image;
        
    }
    
    private void drawObjects(String ObjectType, List<Vector2d> positions) {
        
        Image image = getImage(ObjectType);
        for (int obj_idx=0;obj_idx<positions.size();obj_idx++) {
            
            ImageView view = new ImageView();
            
            Vector2d pos = positions.get(obj_idx);
            int idx = obj_idx;
            
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
            
            if (JoueursPos.get(i).isCloseTo(mousePos)) {
                
                return true;
                
            }
            
        }
        
        for (int i=0; i<AdversairesPos.size();i++) {
            
            if (AdversairesPos.get(i).isCloseTo(mousePos)) {
                
                return true;
                
            }
            
        }
        
        for (int i=0; i<ObstaclesPos.size();i++) {
            
            if (ObstaclesPos.get(i).isCloseTo(mousePos)) {
                
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
                    monJeu.addDeplacementToHistorique("Joueurs",frameEnCours(),Idx,frame.setJoueurPos(Idx,mousePos),mousePos);
                    break;
                case "Adversaires":
                    monJeu.addDeplacementToHistorique("Adversaires",frameEnCours(),Idx,frame.setAdversairePos(Idx,mousePos),mousePos);
                    break;
                case "Obstacles":
                    monJeu.addDeplacementToHistorique("Obstacles",frameEnCours(),Idx,frame.setObstaclePos(Idx,mousePos),mousePos);
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