package ca.ulaval.glo2004.visualigue.ui.controllers.sportmanagement;

import ca.ulaval.glo2004.visualigue.domain.sport.SportNotFoundException;
import ca.ulaval.glo2004.visualigue.ui.InjectableFXMLLoader;
import ca.ulaval.glo2004.visualigue.ui.controllers.Controller;
import ca.ulaval.glo2004.visualigue.ui.controllers.sportcreation.SportCreationController;
import ca.ulaval.glo2004.visualigue.ui.models.SportListItemModel;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

public class SportManagementController extends Controller {

    public static final String VIEW_TITLE = "Liste des sports";
    public static final String VIEW_NAME = "/views/sport-management/sport-management.fxml";
    @FXML private SportListController sportListController;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        sportListController.onSportSelected.setHandler(this::onSportSelectedEvent);
    }

    @Override
    public StringProperty getTitle() {
        return new SimpleStringProperty(VIEW_TITLE);
    }

    private void onSportSelectedEvent(Object sender, SportListItemModel sportListItemModel) {
        FXMLLoader fxmlLoader = InjectableFXMLLoader.load(SportCreationController.VIEW_NAME);
        SportCreationController controller = (SportCreationController) fxmlLoader.getController();
        try {
            controller.init(sportListItemModel.getUUID());
        } catch (SportNotFoundException ex) {
            Logger.getLogger(SportManagementController.class.getName()).log(Level.SEVERE, null, ex);
        }
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
