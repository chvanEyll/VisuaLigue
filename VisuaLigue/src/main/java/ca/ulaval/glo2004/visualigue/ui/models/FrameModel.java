package ca.ulaval.glo2004.visualigue.ui.models;

import ca.ulaval.glo2004.visualigue.ui.models.layers.ActorLayerModel;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;

public class FrameModel extends ModelBase {

    public LongProperty time = new SimpleLongProperty();
    public BooleanProperty isLocked = new SimpleBooleanProperty();
    public DoubleProperty opacity = new SimpleDoubleProperty(1.0);
    public ObservableMap<String, ActorLayerModel> layerModels = FXCollections.observableHashMap();

}
