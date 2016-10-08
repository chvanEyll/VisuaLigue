package ca.ulaval.glo2004.visualigue.ui.controllers.sportlist;

import ca.ulaval.glo2004.visualigue.domain.sport.Sport;
import ca.ulaval.glo2004.visualigue.services.SportService;
import ca.ulaval.glo2004.visualigue.ui.InjectableFXMLLoader;
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
import javax.swing.SortOrder;

public class SportSelectorController implements Initializable {

    @Inject private SportService sportService;
    @Inject private SportListItemModelConverter sportListItemModelConverter;
    @FXML private TilePane rootNode;
    public EventHandler<SportListItemModel> onSportSelected = new EventHandler<>();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        sportService.onSportCreated.setHandler(this::onSportChangedHandler);
        sportService.onSportUpdated.setHandler(this::onSportChangedHandler);
        fillSportList();
    }

    private void onSportChangedHandler(Object sender, Sport sport) {
        Platform.runLater(() -> fillSportList());
    }

    private void fillSportList() {
        rootNode.getChildren().clear();
        sportService.getSports(Sport::getName, SortOrder.ASCENDING).forEach(sport -> {
            initSportItem(sportListItemModelConverter.convert(sport));
        });
    }

    private void initSportItem(SportListItemModel model) {
        FXMLLoader fxmlLoader = InjectableFXMLLoader.load(SportSelectorItemController.VIEW_NAME);
        SportSelectorItemController controller = (SportSelectorItemController) fxmlLoader.getController();
        controller.init(model);
        controller.onClick.setHandler(this::onItemClickedHandler);
        rootNode.getChildren().add(fxmlLoader.getRoot());
    }

    public void onItemClickedHandler(Object sender, SportListItemModel model) {
        onSportSelected.fire(this, model);
    }
}
