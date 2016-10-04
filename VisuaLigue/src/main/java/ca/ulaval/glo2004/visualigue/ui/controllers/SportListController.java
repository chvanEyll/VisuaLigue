package ca.ulaval.glo2004.visualigue.ui.controllers;

import ca.ulaval.glo2004.visualigue.GuiceFXMLLoader;
import ca.ulaval.glo2004.visualigue.ui.models.SportModel;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

public class SportListController extends Controller {

    public static final String VIEW_NAME = "/views/sport-list.fxml";
    @FXML private SportSelectorController sportSelectorController;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        sportSelectorController.onSportSelected.addHandler(this::onSportSelectedEventHandler);
    }

    public void onSportSelectedEventHandler(Object sender, SportModel sportModel) {
        FXMLLoader fxmlLoader = GuiceFXMLLoader.load(SportCreationController.VIEW_NAME);
        SportCreationController controller = (SportCreationController) fxmlLoader.getController();
        controller.setSportModel(sportModel);
        onViewChangeRequest.fire(this, fxmlLoader);
    }
}
