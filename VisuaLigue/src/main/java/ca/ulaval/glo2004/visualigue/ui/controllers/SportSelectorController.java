package ca.ulaval.glo2004.visualigue.ui.controllers;

import ca.ulaval.glo2004.visualigue.GuiceFXMLLoader;
import ca.ulaval.glo2004.visualigue.services.SportService;
import ca.ulaval.glo2004.visualigue.ui.models.SportModel;
import ca.ulaval.glo2004.visualigue.utils.EventHandler;
import ca.ulaval.visualigue.ui.converters.SportModelConverter;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.TilePane;
import javax.inject.Inject;

public class SportSelectorController extends Controller {

    @Inject private SportService sportService;
    @Inject private SportModelConverter sportModelConverter;
    @FXML private TilePane sportTilePane;
    public EventHandler<SportModel> onSportSelected = new EventHandler<>();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        sportService.getSports().stream().sorted().forEach(sport -> {
            initSportItem(sportModelConverter.Convert(sport));
        });
    }

    private void initSportItem(SportModel sportModel) {
        FXMLLoader fxmlLoader = GuiceFXMLLoader.load(SportSelectorItemController.VIEW_NAME);
        SportSelectorItemController controller = (SportSelectorItemController) fxmlLoader.getController();
        controller.setSportModel(sportModel);
        controller.onClick.addHandler(this::onSportItemClicked);
        sportTilePane.getChildren().add(fxmlLoader.getRoot());
    }

    public void onSportItemClicked(Object sender, SportModel sportModel) {
        onSportSelected.fire(this, sportModel);
    }
}
