package ca.ulaval.glo2004.visualigue.ui.models.layers;

import ca.ulaval.glo2004.visualigue.ui.models.ModelBase;
import ca.ulaval.glo2004.visualigue.utils.geometry.Vector2;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class ActorLayerModel extends ModelBase {

    public IntegerProperty zOrder = new SimpleIntegerProperty();
    public BooleanProperty isLocked = new SimpleBooleanProperty();
    public DoubleProperty opacity = new SimpleDoubleProperty(1.0);
    public BooleanProperty visible = new SimpleBooleanProperty();
    public BooleanProperty showLabel = new SimpleBooleanProperty();
    public ObjectProperty<Vector2> position = new SimpleObjectProperty();
    public StringProperty hoverText = new SimpleStringProperty();

}
