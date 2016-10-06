package ca.ulaval.glo2004.visualigue.ui.models;

import ca.ulaval.glo2004.visualigue.domain.playingsurface.PlayingSurfaceUnit;
import ca.ulaval.glo2004.visualigue.domain.sport.Sport;
import javafx.beans.property.*;

public class SportCreationModel {

    public Sport associatedSport;
    public StringProperty name = new SimpleStringProperty("Nouveau sport");
    public StringProperty builtInIconFileName = new SimpleStringProperty("/images/generic-sport-icon");
    public DoubleProperty playingSurfaceWidth = new SimpleDoubleProperty(100.0);
    public DoubleProperty playingSurfaceLength = new SimpleDoubleProperty(200.0);
    public StringProperty playingSurfaceImageFileName = new SimpleStringProperty();
    public ObjectProperty<PlayingSurfaceUnit> playingSurfaceWidthUnits = new SimpleObjectProperty<>(PlayingSurfaceUnit.METER);
    public ObjectProperty<PlayingSurfaceUnit> playingSurfaceLengthUnits = new SimpleObjectProperty<>(PlayingSurfaceUnit.METER);

    public SportCreationModel(String name) {
        this.name.set(name);
    }

    public Boolean hasAssociatedSport() {
        return associatedSport != null;
    }
}
