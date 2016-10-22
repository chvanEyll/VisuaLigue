package ca.ulaval.glo2004.visualigue.ui.models;

import ca.ulaval.glo2004.visualigue.domain.play.position.Position;
import ca.ulaval.glo2004.visualigue.domain.sport.playingsurface.PlayingSurfaceUnit;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

public class MousePositionModel extends Model {

    public ObjectProperty<PlayingSurfaceUnit> playingSurfaceUnit = new SimpleObjectProperty();
    public ObjectProperty<Position> position = new SimpleObjectProperty();

}
