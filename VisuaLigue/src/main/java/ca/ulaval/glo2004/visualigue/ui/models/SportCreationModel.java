package ca.ulaval.glo2004.visualigue.ui.models;

import ca.ulaval.glo2004.visualigue.domain.playingsurface.PlayingSurfaceUnit;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class SportCreationModel extends Model {

    public StringProperty name = new SimpleStringProperty("Nouveau sport");
    public StringProperty builtInIconPathName = new SimpleStringProperty("/images/built-in-sport-icons/generic-icon.fxml");
    public DoubleProperty playingSurfaceWidth = new SimpleDoubleProperty(100.0);
    public DoubleProperty playingSurfaceLength = new SimpleDoubleProperty(200.0);
    public StringProperty newPlayingSurfaceImagePathName = new SimpleStringProperty();
    public StringProperty currentPlayingSurfacePathName = new SimpleStringProperty();
    public StringProperty builtInPlayingSurfaceImage = new SimpleStringProperty();
    public ObjectProperty<PlayingSurfaceUnit> playingSurfaceWidthUnits = new SimpleObjectProperty<>(PlayingSurfaceUnit.METER);
    public ObjectProperty<PlayingSurfaceUnit> playingSurfaceLengthUnits = new SimpleObjectProperty<>(PlayingSurfaceUnit.METER);
    public ObservableList<PlayerCategoryModel> playerCategoryModels = FXCollections.observableArrayList();

}
