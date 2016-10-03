package ca.ulaval.glo2004.visualigue.ui.controllers;

import ca.ulaval.glo2004.visualigue.domain.Sport;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import ca.ulaval.glo2004.visualigue.services.SportService;
import javax.inject.Inject;

public class SportListViewController implements Initializable {
    
    @Inject
    private SportService sportService;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        sportService.createSport(new Sport("Sport 3"));
    }    
}
