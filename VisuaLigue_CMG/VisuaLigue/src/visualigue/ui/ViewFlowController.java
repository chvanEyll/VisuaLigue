/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visualigue.ui;

import java.net.URL;
import java.util.HashMap;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.StackPane;

/**
 * FXML Controller class
 *
 * @author Guillaume
 */
public class ViewFlowController extends MainController {

    static private HashMap<String, Node> screens = new HashMap<>(); 
    
    @FXML private StackPane rootNode;
    
    private Node nextScreen;
    private String myName;
        
   public void init() {
        loadScreen("sportManagement", "sport-management.fxml");
        loadScreen("sportInformation", "sport-information.fxml");
        loadScreen("jeuxManagement", "jeux-management.fxml");
        loadScreen("selectionPourJeu", "selection_sport_pour_jeu.fxml");
        loadScreen("strategyEditor", "strategyEditor.fxml");
        //setScreen("jeuxManagement");
        setScreen("sportManagement");
    }      
    
    public void addScreen(String name, Node screen) { 
       screens.put(name, screen); 
    } 
   
    public boolean loadScreen(String name, String resource) {
     try { 
        URL location = getClass().getResource(resource);
        FXMLLoader fxmlLoader = new FXMLLoader(location);
        
        fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
        
        Node node = fxmlLoader.load(location.openStream());
        addScreen(name, node);
       return true; 
    }catch(Exception e) { 
       System.out.println(e.getMessage()); 
       return false; 
    }        
   }
    
    public boolean loadScreenWithInfo(String name, String resource, Object object) {
     try { 
        URL location = getClass().getResource(resource);
        FXMLLoader fxmlLoader = new FXMLLoader(location);
        
        fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
        
        Node node = fxmlLoader.load(location.openStream());
        
        MainController controller = (MainController)fxmlLoader.getController();
        
        controller.initScreen(object);
        addScreen(name, node);
        rootNode.getChildren().clear();
        rootNode.getChildren().setAll(screens.get(name));
         
       return true; 
    }catch(Exception e) { 
       System.out.println(e.getMessage()); 
       return false; 
    }        
   }
            
    public void setScreen(String name) {
        rootNode.getChildren().remove(screens.get(myName));
        rootNode.getChildren().setAll(screens.get(name));
   }
    
}
