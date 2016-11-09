package ca.ulaval.glo2004.visualigue.ui.models.layers;

import ca.ulaval.glo2004.visualigue.utils.geometry.Vector2;
import javafx.beans.property.*;
import javafx.scene.paint.Color;

public class PlayerLayerModel extends ActorLayerModel {

    public ObjectProperty<Vector2> nextPosition = new SimpleObjectProperty();
    public DoubleProperty orientation = new SimpleDoubleProperty();
    public ObjectProperty<Color> color = new SimpleObjectProperty();
    public StringProperty svgImagePathName = new SimpleStringProperty();
    public StringProperty label = new SimpleStringProperty();
    public BooleanProperty showRotationArrow = new SimpleBooleanProperty(false);

}
