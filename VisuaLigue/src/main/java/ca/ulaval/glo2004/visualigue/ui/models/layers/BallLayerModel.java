package ca.ulaval.glo2004.visualigue.ui.models.layers;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class BallLayerModel extends ActorLayerModel {

    public ObjectProperty<BallLayerModel> owner = new SimpleObjectProperty();
    public StringProperty builtInImagePathName = new SimpleStringProperty();
    public StringProperty imagePathName = new SimpleStringProperty();
}
