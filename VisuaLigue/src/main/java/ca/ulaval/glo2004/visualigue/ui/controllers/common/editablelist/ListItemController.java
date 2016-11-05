package ca.ulaval.glo2004.visualigue.ui.controllers.common.editablelist;

import ca.ulaval.glo2004.visualigue.ui.controllers.ControllerBase;
import ca.ulaval.glo2004.visualigue.ui.models.ModelBase;
import ca.ulaval.glo2004.visualigue.utils.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;

public abstract class ListItemController extends ControllerBase {

    @FXML private GridPane rootNode;
    protected EventHandler<ModelBase> onEditRequested = new EventHandler();
    protected EventHandler<ModelBase> onDeleteRequested = new EventHandler();

    public abstract void init(ModelBase model);

}
