package ca.ulaval.glo2004.visualigue.ui.controllers.sportlist;

import ca.ulaval.glo2004.visualigue.InjectableFXMLLoader;
import ca.ulaval.glo2004.visualigue.domain.sport.Sport;
import ca.ulaval.glo2004.visualigue.ui.controllers.Controller;
import ca.ulaval.glo2004.visualigue.ui.controllers.sportcreation.SportCreationController;
import ca.ulaval.glo2004.visualigue.ui.models.SportListItemModel;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

public class SportListController extends Controller {

    public static final String VIEW_TITLE = "Liste des sports";
    public static final String VIEW_NAME = "/views/sport-list.fxml";
    @FXML private SportSelectorController sportSelectorController;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        sportSelectorController.onSportSelected.setHandler(this::onSportSelectedEventHandler);
    }

    @Override
    public StringProperty getTitle() {
        return new SimpleStringProperty(VIEW_TITLE);
    }

    public void onSportSelectedEventHandler(Object sender, SportListItemModel sportListItemModel) {
        FXMLLoader fxmlLoader = InjectableFXMLLoader.load(SportCreationController.VIEW_NAME);
        SportCreationController controller = (SportCreationController) fxmlLoader.getController();
        controller.init((Sport) sportListItemModel.getAssociatedEntity());
        onViewChangeRequested.fire(this, fxmlLoader);
    }

    @FXML
    public void onNewSportButtonClicked() {
        FXMLLoader fxmlLoader = InjectableFXMLLoader.load(SportCreationController.VIEW_NAME);
        SportCreationController controller = (SportCreationController) fxmlLoader.getController();
        controller.init();
        onViewChangeRequested.fire(this, fxmlLoader);
    }
}
