/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visualigue.ui;

import java.time.Duration;
import java.util.HashMap;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.DoubleProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

/**
 *
 * @author Guillaume
 */
public class ScreensController extends VBox {
    
   private HashMap<String, Node> screens = new HashMap<>();
    
   public void addScreen(String name, Node screen) { 
       screens.put(name, screen); 
   } 
   
   public boolean loadScreen(String name, String resource) {
     try { 
         
        if(screens.get(name) == null){
            FXMLLoader myLoader = new 
                    FXMLLoader(getClass().getResource(resource));
            Parent loadScreen = (Parent) myLoader.load(); 
            MainController myScreenControler = 
                   ((MainController) myLoader.getController());
            //myScreenControler.setScreenParent(this); 
            addScreen(name, loadScreen);            
        }
       
        getChildren().add(screens.get(name));
        return true; 
        
     }catch(Exception e) { 
       System.out.println(e.getMessage()); 
       return false; 
     } 
   }   
   
   public boolean unloadScreen(String name) { 
     if(screens.remove(name) == null) { 
       System.out.println("Screen didn't exist"); 
       return false; 
     } else { 
       return true; 
     } 
   }
     
}
