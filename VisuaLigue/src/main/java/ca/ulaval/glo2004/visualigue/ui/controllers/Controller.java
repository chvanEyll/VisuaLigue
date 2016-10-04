package ca.ulaval.glo2004.visualigue.ui.controllers;

import ca.ulaval.glo2004.visualigue.utils.EventHandler;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

public class Controller implements Initializable {

    public EventHandler<Object> onViewCloseRequest = new EventHandler();
    public EventHandler<FXMLLoader> onViewChangeRequest = new EventHandler<>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
