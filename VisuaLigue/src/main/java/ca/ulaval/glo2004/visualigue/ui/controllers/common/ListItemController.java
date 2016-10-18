package ca.ulaval.glo2004.visualigue.ui.controllers.common;

import ca.ulaval.glo2004.visualigue.ui.controllers.Controller;
import ca.ulaval.glo2004.visualigue.ui.models.Model;
import ca.ulaval.glo2004.visualigue.utils.EventHandler;
import ca.ulaval.glo2004.visualigue.utils.FXUtils;
import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;

public abstract class ListItemController extends Controller {

    @FXML protected GridPane rootNode;
    protected EventHandler<Model> onEditRequested = new EventHandler<>();
    protected EventHandler<Model> onDeleteRequested = new EventHandler<>();

    public void init(Model model) {
        if (model.isDeleted()) {
            hide();
        }
    }

    public void hide() {
        rootNode.getChildren().forEach(child -> {
            FXUtils.setDisplay(child, false);
        });
        rootNode.setPrefHeight(0);
        rootNode.setMinHeight(0);
    }
}
