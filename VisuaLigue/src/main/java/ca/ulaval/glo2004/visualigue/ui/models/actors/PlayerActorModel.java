package ca.ulaval.glo2004.visualigue.ui.models.actors;

import ca.ulaval.glo2004.visualigue.utils.geometry.Vector2;
import javafx.beans.property.*;
import javafx.scene.paint.Color;

public class PlayerActorModel extends ActorModel {

    public ObjectProperty<Vector2> nextPosition = new SimpleObjectProperty();
    public DoubleProperty orientation = new SimpleDoubleProperty();
    public ObjectProperty<Color> color = new SimpleObjectProperty();
    public StringProperty svgImagePathName = new SimpleStringProperty();
    public StringProperty label = new SimpleStringProperty();
    public BooleanProperty showRotationArrow = new SimpleBooleanProperty(false);

}
