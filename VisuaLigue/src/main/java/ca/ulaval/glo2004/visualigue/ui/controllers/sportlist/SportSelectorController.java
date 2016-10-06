package ca.ulaval.glo2004.visualigue.ui.controllers.sportlist;

import ca.ulaval.glo2004.visualigue.GuiceFXMLLoader;
import ca.ulaval.glo2004.visualigue.domain.sport.Sport;
import ca.ulaval.glo2004.visualigue.services.SportService;
import ca.ulaval.glo2004.visualigue.ui.converters.SportListItemModelConverter;
import ca.ulaval.glo2004.visualigue.ui.models.SportListItemModel;
import ca.ulaval.glo2004.visualigue.utils.EventHandler;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.TilePane;
import javax.inject.Inject;

public class SportSelectorController implements Initializable {

    @Inject private SportService sportService;
    @Inject private SportListItemModelConverter sportListItemModelConverter;
    @FXML private TilePane sportTilePane;
    public EventHandler<Sport> onSportSelected = new EventHandler<>();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        sportService.onSportCreated.addHandler(this::onSportChangedHandler);
        sportService.onSportUpdated.addHandler(this::onSportChangedHandler);
        fillSportList();
    }

    private void onSportChangedHandler(Object sender, Sport sport) {
        Platform.runLater(() -> fillSportList());
    }

    private void fillSportList() {
        sportTilePane.getChildren().clear();
        sportService.getSports().stream().sorted().forEach(sport -> {
            initSportItem(sportListItemModelConverter.convert(sport));
        });
    }

    private void initSportItem(SportListItemModel model) {
        FXMLLoader fxmlLoader = GuiceFXMLLoader.load(SportSelectorItemController.VIEW_NAME);
        SportSelectorItemController controller = (SportSelectorItemController) fxmlLoader.getController();
        controller.init(model);
        controller.onClick.addHandler(this::onItemClickedHandler);
        sportTilePane.getChildren().add(fxmlLoader.getRoot());
    }

    public void onItemClickedHandler(Object sender, SportListItemModel model) {
        onSportSelected.fire(this, model.associatedSport);
    }
}