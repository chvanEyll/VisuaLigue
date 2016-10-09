package ca.ulaval.glo2004.visualigue.ui.controllers.sportcreation;

import ca.ulaval.glo2004.visualigue.ui.controllers.FileSelectionEventArgs;
import ca.ulaval.glo2004.visualigue.ui.models.SportCreationModel;
import ca.ulaval.glo2004.visualigue.utils.EventHandler;

public abstract class SportCreationStepController {

    protected SportCreationModel model;
    public EventHandler<FileSelectionEventArgs> onFileSelectionRequested = new EventHandler<>();

    public abstract void init(SportCreationModel model);

    public void init() {
        clearErrors();
    }

    public void showError(Exception ex) {
        //Intentionally left blank. Sub-controllers may or may not implement this function.
    }

    public void clearErrors() {
        //Intentionally left blank. Sub-controllers may or may not implement this function.
    }
}
