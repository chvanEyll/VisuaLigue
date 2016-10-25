package ca.ulaval.glo2004.visualigue.ui.controllers.common;

import ca.ulaval.glo2004.visualigue.ui.controllers.ViewController;
import ca.ulaval.glo2004.visualigue.ui.models.ModelBase;
import ca.ulaval.glo2004.visualigue.utils.EventHandler;
import ca.ulaval.glo2004.visualigue.utils.FXUtils;
import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;

public abstract class ListItemController extends ViewController {

    @FXML private GridPane rootNode;
    protected EventHandler<ModelBase> onEditRequested = new EventHandler();
    protected EventHandler<ModelBase> onDeleteRequested = new EventHandler();

    public void init(ModelBase model) {
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
