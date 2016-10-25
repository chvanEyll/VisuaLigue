package ca.ulaval.glo2004.visualigue.ui.controllers.common;

import ca.ulaval.glo2004.visualigue.ui.controllers.ViewController;
import ca.ulaval.glo2004.visualigue.ui.models.ModelBase;
import ca.ulaval.glo2004.visualigue.utils.EventHandler;

public abstract class ListItemEditionController extends ViewController {

    protected EventHandler<ModelBase> onCloseRequested = new EventHandler();

    public abstract void init(ModelBase model);

    public abstract ModelBase getModel();

}
