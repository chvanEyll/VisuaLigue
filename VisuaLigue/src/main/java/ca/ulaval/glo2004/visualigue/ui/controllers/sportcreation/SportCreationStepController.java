package ca.ulaval.glo2004.visualigue.ui.controllers.sportcreation;

import ca.ulaval.glo2004.visualigue.ui.controllers.ControllerBase;
import ca.ulaval.glo2004.visualigue.ui.models.SportCreationModel;

public abstract class SportCreationStepController extends ControllerBase {

    protected SportCreationModel model;

    public abstract void init(SportCreationModel model);

    public void showError(Exception ex) {
        //Intentionally left blank. Sub-controllers may or may not implement this method.
    }

    public void clearErrors() {
        //Intentionally left blank. Sub-controllers may or may not implement this method.
    }

    public Boolean validate() {
        return true;
    }
}
