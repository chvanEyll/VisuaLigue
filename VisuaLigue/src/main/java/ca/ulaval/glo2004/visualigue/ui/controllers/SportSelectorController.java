package ca.ulaval.glo2004.visualigue.ui.controllers;

import ca.ulaval.glo2004.visualigue.domain.Sport;
import java.net.URL;
import java.util.ResourceBundle;
import ca.ulaval.glo2004.visualigue.utils.EventHandler;
import javafx.fxml.Initializable;
import ca.ulaval.glo2004.visualigue.services.SportService;
import javafx.fxml.FXML;
import javafx.scene.layout.TilePane;
import javax.inject.Inject;

public class SportSelectorController implements Initializable {
    
    @Inject
    private SportService sportService;
    
    @FXML
    private TilePane sportTilePane;

    public EventHandler<Sport> onSportSelected = new EventHandler<>();    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        sportService.getSports().stream().sorted().forEach(sport -> {
            SportSelectorItemController item = new SportSelectorItemController(sport);
            item.onClick.addHandler(this::onSportItemClicked);
            sportTilePane.getChildren().add(item.getNode());
        });
    }    
    
    public void onSportItemClicked(Object sender, Sport sport) {
        onSportSelected.fire(this, sport);
    }
}
