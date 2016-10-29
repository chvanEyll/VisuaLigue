package ca.ulaval.glo2004.visualigue.ui.controllers.sportmanagement;

import ca.ulaval.glo2004.visualigue.ui.InjectableFXMLLoader;
import ca.ulaval.glo2004.visualigue.ui.View;
import ca.ulaval.glo2004.visualigue.ui.controllers.ControllerBase;
import ca.ulaval.glo2004.visualigue.ui.controllers.ViewFlowRequestEventArgs;
import ca.ulaval.glo2004.visualigue.ui.controllers.sportcreation.SportCreationController;
import ca.ulaval.glo2004.visualigue.ui.models.SportListItemModel;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;

public class SportManagementController extends ControllerBase {

    public static final String VIEW_TITLE = "Sports";
    public static final String VIEW_NAME = "/views/sportmanagement/sport-management.fxml";
    @FXML private SportListController sportListController;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        sportListController.onSportSelected.setHandler(this::onSportSelectedEvent);
        sportListController.onViewAppendRequested.forward(this.onViewAppendRequested);
    }

    @Override
    public StringProperty getTitle() {
        return new SimpleStringProperty(VIEW_TITLE);
    }

    private void onSportSelectedEvent(Object sender, SportListItemModel sportListItemModel) {
        View view = InjectableFXMLLoader.loadView(SportCreationController.VIEW_NAME);
        SportCreationController controller = (SportCreationController) view.getController();
        controller.init(sportListItemModel.getUUID());
        onViewAppendRequested.fire(this, new ViewFlowRequestEventArgs(view));
    }
}
