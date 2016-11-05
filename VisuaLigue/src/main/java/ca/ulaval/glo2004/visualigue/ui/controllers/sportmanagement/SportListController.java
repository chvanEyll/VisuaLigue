package ca.ulaval.glo2004.visualigue.ui.controllers.sportmanagement;

import ca.ulaval.glo2004.visualigue.domain.sport.Sport;
import ca.ulaval.glo2004.visualigue.services.sport.SportService;
import ca.ulaval.glo2004.visualigue.ui.InjectableFXMLLoader;
import ca.ulaval.glo2004.visualigue.ui.View;
import ca.ulaval.glo2004.visualigue.ui.controllers.ControllerBase;
import ca.ulaval.glo2004.visualigue.ui.controllers.ViewFlowRequestEventArgs;
import ca.ulaval.glo2004.visualigue.ui.controllers.sportcreation.SportCreationController;
import ca.ulaval.glo2004.visualigue.ui.converters.SportListItemModelConverter;
import ca.ulaval.glo2004.visualigue.ui.models.SportListItemModel;
import ca.ulaval.glo2004.visualigue.utils.EventHandler;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.function.BiConsumer;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.TilePane;
import javax.inject.Inject;
import javax.swing.SortOrder;

public class SportListController extends ControllerBase {

    public EventHandler<SportListItemModel> onSportSelected = new EventHandler();
    @Inject private SportService sportService;
    @Inject private SportListItemModelConverter sportListItemModelConverter;
    @FXML private TilePane tilePane;
    private Map<SportListItemModel, View> sportListItemViews = new HashMap();
    private Map<String, SportListItemModel> sportListItemModels = new HashMap();
    private BiConsumer<Object, Sport> onSportCreated = this::onSportCreated;
    private BiConsumer<Object, Sport> onSportUpdated = this::onSportUpdated;
    private BiConsumer<Object, Sport> onSportDeleted = this::onSportDeleted;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        sportService.onSportCreated.addHandler(onSportCreated);
        sportService.onSportUpdated.addHandler(onSportUpdated);
        sportService.onSportDeleted.addHandler(onSportDeleted);
        fillSportList();
    }

    @Override
    public void clean() {
        sportService.onSportCreated.removeHandler(onSportCreated);
        sportService.onSportUpdated.removeHandler(onSportUpdated);
        sportService.onSportDeleted.removeHandler(onSportDeleted);
        super.clean();
    }

    private void onSportCreated(Object sender, Sport sport) {
        SportListItemModel model = sportListItemModelConverter.convert(sport);
        addSportListItem(model);
    }

    private void onSportUpdated(Object sender, Sport sport) {
        SportListItemModel model = sportListItemModels.get(sport.getUUID());
        sportListItemModelConverter.update(model, sport);
    }

    private void onSportDeleted(Object sender, Sport sport) {
        SportListItemModel model = sportListItemModels.get(sport.getUUID());
        removeSportListItem(model);
    }

    private void fillSportList() {
        List<Sport> sports = sportService.getSports(Sport::getName, SortOrder.ASCENDING);
        sports.forEach(sport -> {
            addSportListItem(sportListItemModelConverter.convert(sport));
        });
    }

    private void addSportListItem(SportListItemModel model) {
        View view = InjectableFXMLLoader.loadView(SportListItemController.VIEW_NAME);
        SportListItemController controller = (SportListItemController) view.getController();
        controller.init(model);
        controller.onClick.setHandler(this::onItemClicked);
        sportListItemViews.put(model, view);
        sportListItemModels.put(model.getUUID(), model);
        tilePane.getChildren().add(view.getRoot());
    }

    private void removeSportListItem(SportListItemModel model) {
        tilePane.getChildren().remove(sportListItemViews.get(model).getRoot());
        sportListItemViews.remove(model);
        sportListItemModels.remove(model.getUUID());
    }

    private void onItemClicked(Object sender, SportListItemModel model) {
        onSportSelected.fire(this, model);
    }

    @FXML
    protected void onNewSportButtonClicked(MouseEvent e) {
        View view = InjectableFXMLLoader.loadView(SportCreationController.VIEW_NAME);
        SportCreationController controller = (SportCreationController) view.getController();
        controller.init();
        onViewAppendRequested.fire(this, new ViewFlowRequestEventArgs(view));
    }
}
