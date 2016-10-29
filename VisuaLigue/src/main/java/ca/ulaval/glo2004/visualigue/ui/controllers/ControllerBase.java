package ca.ulaval.glo2004.visualigue.ui.controllers;

import ca.ulaval.glo2004.visualigue.utils.EventHandler;
import java.net.URL;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.Initializable;

public abstract class ControllerBase implements Initializable {

    public EventHandler<ViewFlowRequestEventArgs> onViewCloseRequested = new EventHandler();
    public EventHandler<ViewFlowRequestEventArgs> onViewChangeRequested = new EventHandler();
    public EventHandler<ViewFlowRequestEventArgs> onViewAppendRequested = new EventHandler();
    public EventHandler<ViewFlowRequestEventArgs> onViewFlowResetRequested = new EventHandler();
    private Set<ControllerBase> children = new HashSet();

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

    public void clean() {
        children.stream().forEach(childController -> childController.clean());
    }

    protected void addChild(ControllerBase childController) {
        children.add(childController);
    }
}
