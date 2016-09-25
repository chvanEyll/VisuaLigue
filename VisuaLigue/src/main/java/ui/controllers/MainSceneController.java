/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.java.ui.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.Pane;
import javafx.scene.input.ScrollEvent;
import javafx.scene.input.SwipeEvent;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;

/**
 *
 * @author OlivierR
 */
public class MainSceneController implements Initializable {
    
    @FXML private Pane menuPane;
    @FXML private Pane logoPane;
    private boolean isMenuPaneCollapsed = false;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    public void onHamburgerIconClick() {
        if (!isMenuPaneCollapsed) {
            menuPane.setPrefWidth(58);
            logoPane.setPrefWidth(58);
            menuPane.setClip(new Rectangle(58, Integer.MAX_VALUE));
            logoPane.setClip(new Rectangle(58, Integer.MAX_VALUE));
        } else {
            menuPane.setPrefWidth(170);
            logoPane.setPrefWidth(170);
            menuPane.setClip(null);
            logoPane.setClip(null);
        }
        isMenuPaneCollapsed = !isMenuPaneCollapsed;
    }
    
}
