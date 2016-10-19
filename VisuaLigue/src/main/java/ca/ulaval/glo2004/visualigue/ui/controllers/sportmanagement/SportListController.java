package ca.ulaval.glo2004.visualigue.ui.controllers.sportmanagement;

import ca.ulaval.glo2004.visualigue.domain.sport.Sport;
import ca.ulaval.glo2004.visualigue.services.SportService;
import ca.ulaval.glo2004.visualigue.ui.InjectableFXMLLoader;
import ca.ulaval.glo2004.visualigue.ui.View;
import ca.ulaval.glo2004.visualigue.ui.controllers.Controller;
import ca.ulaval.glo2004.visualigue.ui.controllers.sportcreation.SportCreationController;
import ca.ulaval.glo2004.visualigue.ui.converters.SportListItemModelConverter;
import ca.ulaval.glo2004.visualigue.ui.models.SportListItemModel;
import ca.ulaval.glo2004.visualigue.utils.EventHandler;
import ca.ulaval.glo2004.visualigue.utils.FXUtils;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.TilePane;
import javax.inject.Inject;
import javax.swing.SortOrder;

public class SportListController extends Controller {

    @Inject private SportService sportService;
    @Inject private SportListItemModelConverter sportListItemModelConverter;
    @FXML private TilePane tilePane;
    @FXML private Label emptyNoticeLabel;
    public EventHandler<SportListItemModel> onSportSelected = new EventHandler<>();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        sportService.onSportCreated.setHandler(this::onSportChanged);
        sportService.onSportUpdated.setHandler(this::onSportChanged);
        sportService.onSportDeleted.setHandler(this::onSportChanged);
        fillSportList();
    }

    private void onSportChanged(Object sender, Sport sport) {
        Platform.runLater(() -> fillSportList());
    }

    private void fillSportList() {
        tilePane.getChildren().clear();
        List<Sport> sports = sportService.getSports(Sport::getName, SortOrder.ASCENDING);
        FXUtils.setDisplay(emptyNoticeLabel, sports.isEmpty());
        sports.forEach(sport -> {
            initSportItem(sportListItemModelConverter.convert(sport));
        });
    }

    private void initSportItem(SportListItemModel model) {
        View view = InjectableFXMLLoader.loadView(SportListItemController.VIEW_NAME);
        SportListItemController controller = (SportListItemController) view.getController();
        controller.init(model);
        controller.onClick.setHandler(this::onItemClicked);
        tilePane.getChildren().add(view.getRoot());
    }

    private void onItemClicked(Object sender, SportListItemModel model) {
        onSportSelected.fire(this, model);
    }

    @FXML
    public void onNewSportButtonClicked(MouseEvent e) {
        View view = InjectableFXMLLoader.loadView(SportCreationController.VIEW_NAME);
        SportCreationController controller = (SportCreationController) view.getController();
        controller.init();
        onViewChangeRequested.fire(this, view);
    }
}
