package ca.ulaval.glo2004.visualigue.ui.controllers.sportlist;

import ca.ulaval.glo2004.visualigue.GuiceFXMLLoader;
import ca.ulaval.glo2004.visualigue.services.SportService;
import ca.ulaval.glo2004.visualigue.ui.converters.SportCreationModelConverter;
import ca.ulaval.glo2004.visualigue.ui.models.SportCreationModel;
import ca.ulaval.glo2004.visualigue.utils.EventHandler;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.TilePane;
import javax.inject.Inject;

public class SportSelectorController implements Initializable {

    @Inject private SportService sportService;
    @Inject private SportCreationModelConverter sportModelConverter;
    @FXML private TilePane sportTilePane;
    public EventHandler<SportCreationModel> onSportSelected = new EventHandler<>();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        sportService.getSports().stream().sorted().forEach(sport -> {
            initSportItem(sportModelConverter.Convert(sport));
        });
    }

    private void initSportItem(SportCreationModel model) {
        FXMLLoader fxmlLoader = GuiceFXMLLoader.load(SportSelectorItemController.VIEW_NAME);
        SportSelectorItemController controller = (SportSelectorItemController) fxmlLoader.getController();
        controller.init(model);
        controller.onClick.addHandler(this::onSportItemClicked);
        sportTilePane.getChildren().add(fxmlLoader.getRoot());
    }

    public void onSportItemClicked(Object sender, SportCreationModel model) {
        onSportSelected.fire(this, model);
    }
}
