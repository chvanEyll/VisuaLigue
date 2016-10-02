package main.java.ui.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import main.java.services.SportService;

public class SportListViewController implements Initializable {
    
    private SportService sportService;
    
    public SportListViewController(SportService sportService) {
        this.sportService = sportService;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        int i = 8;
    }    
}
