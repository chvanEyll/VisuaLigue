package ca.ulaval.glo2004.visualigue.ui.models;

import ca.ulaval.glo2004.visualigue.domain.sport.playingsurface.PlayingSurfaceUnit;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class SportCreationModel extends ModelBase {

    public StringProperty name = new SimpleStringProperty("Nouveau sport");
    public StringProperty ballName = new SimpleStringProperty("Ballon");
    public StringProperty newIconPathName = new SimpleStringProperty();
    public StringProperty currentIconPathName = new SimpleStringProperty();
    public StringProperty builtInIconPathName = new SimpleStringProperty("/images/built-in-sport-icons/generic-icon.png");
    public StringProperty newBallImagePathName = new SimpleStringProperty();
    public StringProperty currentBallImagePathName = new SimpleStringProperty();
    public StringProperty builtInBallImagePathName = new SimpleStringProperty("/images/built-in-ball-icons/generic-ball-icon.png");
    public DoubleProperty playingSurfaceWidth = new SimpleDoubleProperty(100.0);
    public DoubleProperty playingSurfaceLength = new SimpleDoubleProperty(200.0);
    public StringProperty newPlayingSurfaceImagePathName = new SimpleStringProperty();
    public StringProperty currentPlayingSurfaceImagePathName = new SimpleStringProperty();
    public StringProperty builtInPlayingSurfaceImagePathName = new SimpleStringProperty();
    public ObjectProperty<PlayingSurfaceUnit> playingSurfaceWidthUnits = new SimpleObjectProperty<>(PlayingSurfaceUnit.METER);
    public ObjectProperty<PlayingSurfaceUnit> playingSurfaceLengthUnits = new SimpleObjectProperty<>(PlayingSurfaceUnit.METER);
    public ObservableList<PlayerCategoryModel> playerCategoryModels = FXCollections.observableArrayList();

}
