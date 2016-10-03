package ca.ulaval.glo2004.visualigue.ui.controllers;

import ca.ulaval.glo2004.visualigue.domain.Sport;
import ca.ulaval.glo2004.visualigue.utils.EventHandler;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class SportSelectorItemController {
    public static final String VIEW_NAME = "sport-selector-item.fxml";
    
    @FXML
    private VBox rootNode;
    
    private Sport sport;
    
    public EventHandler<Sport> onClick = new EventHandler();
    
    public SportSelectorItemController(Sport sport) {
        this.sport = sport;
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/" + VIEW_NAME));
            fxmlLoader.setController(this);
            fxmlLoader.load();
        } catch (IOException ex) {
            Logger.getLogger(SportSelectorItemController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public VBox getNode() {
        return rootNode;
    }
    
    public Sport getSport() {
        return sport;
    }
    
    @FXML
    private void onClick() {
        onClick.fire(this, sport);
    }
    
}