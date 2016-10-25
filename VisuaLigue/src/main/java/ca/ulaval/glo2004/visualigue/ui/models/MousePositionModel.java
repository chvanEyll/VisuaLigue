package ca.ulaval.glo2004.visualigue.ui.models;

import ca.ulaval.glo2004.visualigue.utils.geometry.Vector2;
import ca.ulaval.glo2004.visualigue.domain.sport.playingsurface.PlayingSurfaceUnit;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

public class MousePositionModel extends ModelBase {

    public ObjectProperty<PlayingSurfaceUnit> playingSurfaceUnit = new SimpleObjectProperty();
    public ObjectProperty<Vector2> position = new SimpleObjectProperty();

}
