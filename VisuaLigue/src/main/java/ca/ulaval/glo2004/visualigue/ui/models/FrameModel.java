package ca.ulaval.glo2004.visualigue.ui.models;

import ca.ulaval.glo2004.visualigue.ui.models.actors.ActorModel;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;

public class FrameModel extends ModelBase {

    public LongProperty time = new SimpleLongProperty();
    public ObservableMap<Integer, ActorModel> actorModels = FXCollections.observableHashMap();

}
