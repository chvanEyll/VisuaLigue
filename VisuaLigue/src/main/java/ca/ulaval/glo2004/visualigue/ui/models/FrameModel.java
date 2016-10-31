package ca.ulaval.glo2004.visualigue.ui.models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;

public class FrameModel extends ModelBase {

    public ObservableMap<String, ActorModel> actorStates = FXCollections.observableHashMap();

}
