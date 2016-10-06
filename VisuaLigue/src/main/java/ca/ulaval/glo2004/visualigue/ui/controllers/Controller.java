package ca.ulaval.glo2004.visualigue.ui.controllers;

import ca.ulaval.glo2004.visualigue.utils.EventHandler;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

public abstract class Controller implements Initializable {

    public EventHandler<Object> onViewCloseRequested = new EventHandler<>();
    public EventHandler<FXMLLoader> onViewChangeRequested = new EventHandler<>();
    public EventHandler<FileSelectionEventArgs> onFileSelectionRequested = new EventHandler<>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void clearHandlers() {
        onViewCloseRequested.clear();
        onViewChangeRequested.clear();
        onFileSelectionRequested.clear();
    }

    public abstract StringProperty getTitle();

}
