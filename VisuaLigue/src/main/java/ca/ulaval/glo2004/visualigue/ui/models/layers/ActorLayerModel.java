package ca.ulaval.glo2004.visualigue.ui.models.layers;

import ca.ulaval.glo2004.visualigue.ui.models.ModelBase;
import ca.ulaval.glo2004.visualigue.utils.geometry.Vector2;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class ActorLayerModel extends ModelBase {

    public StringProperty viewName = new SimpleStringProperty();
    public ObjectProperty<Vector2> position = new SimpleObjectProperty();
    public StringProperty hoverText = new SimpleStringProperty();

}
