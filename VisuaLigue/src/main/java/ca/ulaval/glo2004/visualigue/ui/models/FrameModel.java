package ca.ulaval.glo2004.visualigue.ui.models;

import ca.ulaval.glo2004.visualigue.ui.models.actors.ActorModel;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;

public class FrameModel extends ModelBase {

    public IntegerProperty time = new SimpleIntegerProperty();
    public ObservableMap<String, ActorModel> actorModels = FXCollections.observableHashMap();

}
