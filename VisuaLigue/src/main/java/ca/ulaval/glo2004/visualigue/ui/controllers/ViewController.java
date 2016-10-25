package ca.ulaval.glo2004.visualigue.ui.controllers;

import ca.ulaval.glo2004.visualigue.ui.View;
import ca.ulaval.glo2004.visualigue.utils.EventHandler;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.Initializable;

public abstract class ViewController implements Initializable {

    public EventHandler<Boolean> onViewCloseRequested = new EventHandler();
    public EventHandler<View> onViewChangeRequested = new EventHandler();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public StringProperty getTitle() {
        return new SimpleStringProperty("");
    }

    public Boolean isTitleEditable() {
        return false;
    }

    public Boolean onViewClosing() {
        return true;
    }
}
