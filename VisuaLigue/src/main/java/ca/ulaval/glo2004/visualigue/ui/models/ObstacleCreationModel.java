package ca.ulaval.glo2004.visualigue.ui.models;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class ObstacleCreationModel extends Model {

    public StringProperty name = new SimpleStringProperty("Nouvel obstacle");
    public StringProperty builtInImagePathName = new SimpleStringProperty("/images/built-in-sport-icons/generic-icon.fxml");
    public StringProperty newImagePathName = new SimpleStringProperty();
    public StringProperty currentImagePathName = new SimpleStringProperty();

}
