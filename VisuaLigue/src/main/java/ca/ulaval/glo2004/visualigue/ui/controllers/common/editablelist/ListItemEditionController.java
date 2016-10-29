package ca.ulaval.glo2004.visualigue.ui.controllers.common.editablelist;

import ca.ulaval.glo2004.visualigue.ui.controllers.ControllerBase;
import ca.ulaval.glo2004.visualigue.ui.models.ModelBase;
import ca.ulaval.glo2004.visualigue.utils.EventHandler;

public abstract class ListItemEditionController extends ControllerBase {

    protected EventHandler<ModelBase> onCloseRequested = new EventHandler();

    public abstract void init(ModelBase model);

    public abstract ModelBase getModel();

}
