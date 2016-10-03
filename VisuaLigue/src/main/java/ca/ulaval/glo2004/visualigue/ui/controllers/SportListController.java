package ca.ulaval.glo2004.visualigue.ui.controllers;

import ca.ulaval.glo2004.visualigue.ui.models.SportModel;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

public class SportListController implements Initializable {

    @FXML SportSelectorController sportSelectorController;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        sportSelectorController.onSportSelected.addHandler(this::onSportSelectedEventHandler);
    }

    public void onSportSelectedEventHandler(Object sender, SportModel sportModel) {

    }
}
