package ca.ulaval.glo2004.visualigue.ui.controllers;

import ca.ulaval.glo2004.visualigue.GuiceFXMLLoader;
import ca.ulaval.glo2004.visualigue.domain.Sport;
import ca.ulaval.glo2004.visualigue.services.SportService;
import ca.ulaval.glo2004.visualigue.utils.EventHandler;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.TilePane;
import javax.inject.Inject;

public class SportSelectorController implements Initializable {

    public static final String ITEM_VIEW_NAME = "/views/sport-selector-item.fxml";

    @Inject
    private SportService sportService;
    @FXML
    private TilePane sportTilePane;
    public EventHandler<Sport> onSportSelected = new EventHandler<>();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        sportService.getSports().stream().sorted().forEach(sport -> {
            initSportItem(sport);
        });
    }

    private void initSportItem(Sport sport) {
        FXMLLoader fxmlLoader = GuiceFXMLLoader.createLoader(getClass().getResource(ITEM_VIEW_NAME));
        try {
            fxmlLoader.load();
        } catch (IOException ex) {
            Logger.getLogger(SportSelectorController.class.getName()).log(Level.SEVERE, null, ex);
        }
        SportSelectorItemController controller = (SportSelectorItemController) fxmlLoader.getController();
        controller.setSport(sport);
        controller.onClick.addHandler(this::onSportItemClicked);
        sportTilePane.getChildren().add(controller.getRootNode());
    }

    public void onSportItemClicked(Object sender, Sport sport) {
        onSportSelected.fire(this, sport);
    }
}
