package ca.ulaval.glo2004.visualigue.ui.models;

import ca.ulaval.glo2004.visualigue.ui.models.layers.ActorLayerModel;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;

public class FrameModel extends ModelBase {

    public LongProperty time = new SimpleLongProperty();
    public ObservableMap<Integer, ActorLayerModel> layerModels = FXCollections.observableHashMap();

}
