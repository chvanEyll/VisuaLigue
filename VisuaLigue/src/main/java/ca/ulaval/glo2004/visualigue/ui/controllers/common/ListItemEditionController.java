package ca.ulaval.glo2004.visualigue.ui.controllers.common;

import ca.ulaval.glo2004.visualigue.ui.controllers.Controller;
import ca.ulaval.glo2004.visualigue.ui.models.Model;
import ca.ulaval.glo2004.visualigue.utils.EventHandler;

public abstract class ListItemEditionController extends Controller {

    protected EventHandler<Model> onCloseRequested = new EventHandler<>();

    public abstract void init(Model model);

    public abstract Model getModel();

}
