package main.java.ui.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Rectangle;

public class MainViewController implements Initializable {
    
    private static final int MENU_PANE_COLLAPSED_WIDTH = 58;
    private static final int MENU_PANE_EXTENDED_WIDTH = 170;
    private static final String sportListViewName = "sport-list.fxml";
    private static final String playListViewName = "play-list.fxml";
    
    @FXML private VBox menuPane;
    @FXML private Pane logoPane;
    @FXML private HBox sportsMenuItem;
    @FXML private HBox playsMenuItem;
    @FXML private Pane contentPane;
    private boolean isMenuPaneCollapsed = false;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        collapseMenuPane();
        loadView(playListViewName, playsMenuItem);
    }    
    
    @FXML
    private void onHamburgerIconClick() {
        if (!isMenuPaneCollapsed) {
            collapseMenuPane();
        } else {
            extendMenuPane();
        }
    }
    
    private void collapseMenuPane() {
        menuPane.setPrefWidth(MENU_PANE_COLLAPSED_WIDTH);
        menuPane.setMinWidth(MENU_PANE_COLLAPSED_WIDTH);
        logoPane.setPrefWidth(MENU_PANE_COLLAPSED_WIDTH);
        logoPane.setMinWidth(MENU_PANE_COLLAPSED_WIDTH);
        menuPane.setClip(new Rectangle(MENU_PANE_COLLAPSED_WIDTH, Integer.MAX_VALUE));
        logoPane.setClip(new Rectangle(MENU_PANE_COLLAPSED_WIDTH, Integer.MAX_VALUE));
        isMenuPaneCollapsed = true;
    }
    
    private void extendMenuPane() {
        menuPane.setPrefWidth(MENU_PANE_EXTENDED_WIDTH);
        menuPane.setMinWidth(MENU_PANE_EXTENDED_WIDTH);
        logoPane.setPrefWidth(MENU_PANE_EXTENDED_WIDTH);
        logoPane.setMinWidth(MENU_PANE_EXTENDED_WIDTH);
        menuPane.setClip(null);
        logoPane.setClip(null);
        isMenuPaneCollapsed = false;
    }
    
    @FXML
    private void onSportsMenuItemClick() {
        loadView(sportListViewName, sportsMenuItem);
    }
    
    @FXML
    private void onPlaysMenuItemClick() {
        loadView(playListViewName, playsMenuItem);
    }   
    
    private void loadView(String viewName, HBox menuItem) {
        try {
            contentPane.getChildren().clear();
            contentPane.getChildren().add(FXMLLoader.load(getClass().getResource("/resources/views/" + viewName)));
        } catch (IOException ex) {
            Logger.getLogger(MainViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
        unselectAllMenus();
        menuItem.getStyleClass().add("active");
    }
    
    private void unselectAllMenus() {
        menuPane.getChildren().stream().forEach(n -> {
            n.getStyleClass().remove("active");
        });
    }
}
