package ca.ulaval.glo2004.visualigue.ui.controllers;

import ca.ulaval.glo2004.visualigue.ui.models.SportModel;

public abstract class SportCreationStepController extends Controller {

    protected SportModel sportModel;

    public abstract void setSportModel(SportModel sportModel);
}
