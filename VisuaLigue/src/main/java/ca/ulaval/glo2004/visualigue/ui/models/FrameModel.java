package ca.ulaval.glo2004.visualigue.ui.models;

import java.util.UUID;
import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;

public class FrameModel extends ModelBase {

    public ObservableMap<UUID, ActorModel> actorStates = FXCollections.observableHashMap();

}
